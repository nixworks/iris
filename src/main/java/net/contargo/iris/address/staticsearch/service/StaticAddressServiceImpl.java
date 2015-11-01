package net.contargo.iris.address.staticsearch.service;

import net.contargo.iris.BoundingBox;
import net.contargo.iris.GeoLocation;
import net.contargo.iris.address.Address;
import net.contargo.iris.address.AddressList;
import net.contargo.iris.address.staticsearch.StaticAddress;
import net.contargo.iris.address.staticsearch.persistence.StaticAddressRepository;
import net.contargo.iris.normalizer.NormalizerServiceImpl;
import net.contargo.iris.sequence.service.SequenceService;

import org.slf4j.Logger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.invoke.MethodHandles;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;

import static org.slf4j.LoggerFactory.getLogger;


/**
 * Implementation of {@link StaticAddressService}.
 *
 * @author  Aljona Murygina - murygina@synyx.de
 */
@Transactional
public class StaticAddressServiceImpl implements StaticAddressService {

    private static final Logger LOG = getLogger(MethodHandles.lookup().lookupClass());
    private static final String WILDCARD = "%";
    private static final int PAGE_SIZE = 50;

    private final StaticAddressRepository repository;

    private final SequenceService uniqueIdSequenceService;

    private final NormalizerServiceImpl normalizerService;

    public StaticAddressServiceImpl(StaticAddressRepository repository, SequenceService uniqueIdSequenceService,
        NormalizerServiceImpl normalizerService) {

        this.repository = repository;
        this.uniqueIdSequenceService = uniqueIdSequenceService;
        this.normalizerService = normalizerService;
    }

    /**
     * Finds all {@link Address}es by the given parameters postalCode, city and country. The procedure of this method is
     * following (step to next procedure if result list is empty): 1. Execute an AND search, i.e. find addresses by
     * postal code AND city 2. Execute an OR search, i.e. find addresses by postal code OR city 3. Execute a split
     * search, i.e. if the city string is "Neustadt an der Weinstrasse" and nothing is found for that then execute
     * search for "Neustadt"
     *
     * @param  postalCode  String
     * @param  city  String
     * @param  country  String
     *
     * @return  {@link AddressList}
     */
    @Override
    public AddressList findAddresses(String postalCode, String city, String country) {

        List<StaticAddress> staticAddresses = getAddressesByDetailsWithFallbacks(postalCode, city, country);

        List<Address> addresses = staticAddresses.stream().map(StaticAddress::toAddress).collect(Collectors.toList());

        return new AddressList("City and Suburb Results", addresses);
    }


    @Override
    @Transactional(readOnly = true)
    public List<StaticAddress> getAddressesByDetailsWithFallbacks(String postalCode, String city, String country) {

        String normalizedCity = normalizerService.normalize(city);

        List<StaticAddress> staticAddresses = executeANDSearch(postalCode, normalizedCity, country);

        if (staticAddresses.isEmpty()) {
            // fallback to OR search
            staticAddresses = executeORSearch(postalCode, normalizedCity, country);
        }

        if (staticAddresses.isEmpty() && city != null) {
            // fallback to split search
            staticAddresses = executeSplitSearch(postalCode, city, country);
        }

        return staticAddresses;
    }


    @Override
    public List<StaticAddress> getAddressesByDetails(String postalCode, String city, String country) {

        String normalizedCity = normalizerService.normalize(city);

        return repository.findByCountryAndPostalCodeAndCity(postalCode, normalizedCity, country);
    }


    @Override
    @Transactional(readOnly = true)
    public StaticAddress getForLocation(GeoLocation loc) {

        return repository.findByLatitudeAndLongitude(loc.getLatitude(), loc.getLongitude());
    }


    @Override
    @Transactional(readOnly = true)
    public StaticAddress findbyId(Long staticAddressId) {

        return repository.findOne(staticAddressId);
    }


    @Override
    public synchronized StaticAddress saveStaticAddress(StaticAddress staticAddress) {

        setEmptyValues(staticAddress);

        normalizeFields(staticAddress);

        if (staticAddress.getId() == null) {
            return saveNewStaticAddress(staticAddress);
        } else {
            return updateStaticAddress(staticAddress);
        }
    }


    /**
     * @param  staticAddressId
     *
     * @return  The _one_ Address for the given static address ID. For consistent processing on client side it is
     *          wrapped in a list of AddressLists.
     */
    @Override
    public List<AddressList> getAddressListListForStaticAddressId(Long staticAddressId) {

        StaticAddress staticAddress = findbyId(staticAddressId);

        if (staticAddress == null) {
            return Collections.emptyList();
        }

        AddressList staticAddressList = new AddressList("Result ", Arrays.asList(staticAddress.toAddress()));

        return Arrays.asList(staticAddressList);
    }


    /**
     * @param  location
     *
     * @return  The _one_ Address for the given geoLocation. For consistent processing on client side it is wrapped in a
     *          list of AddressLists.
     */
    @Override
    public List<AddressList> getAddressListListForGeolocation(GeoLocation location) {

        StaticAddress staticAddress = getForLocation(location);

        if (staticAddress == null) {
            return Collections.emptyList();
        }

        AddressList staticAddressList = new AddressList("Result ", Arrays.asList(staticAddress.toAddress()));

        return Arrays.asList(staticAddressList);
    }


    private void setEmptyValues(StaticAddress staticAddress) {

        if (staticAddress.getCity() == null) {
            staticAddress.setCity("");
        }

        if (staticAddress.getPostalcode() == null) {
            staticAddress.setPostalcode("");
        }

        if (staticAddress.getSuburb() == null) {
            staticAddress.setSuburb("");
        }
    }


    @Override
    public void normalizeFields(StaticAddress staticAddress) {

        if (staticAddress.getCity() != null) {
            staticAddress.setCityNormalized(normalizerService.normalize(staticAddress.getCity()));
        }

        if (staticAddress.getSuburb() != null) {
            staticAddress.setSuburbNormalized(normalizerService.normalize(staticAddress.getSuburb()));
        }
    }


    @Override
    public void fillMissingHashKeys() {

        LOG.info("Starting to fill the static address hashkeys with " + PAGE_SIZE + " items per page");

        int currentPage = 0;
        int totalPages = getTotalPages(currentPage, PAGE_SIZE);

        boolean hasElements = true;

        while (hasElements) {
            List<StaticAddress> addressesOfCurrentPage = getAddresses(0, PAGE_SIZE);

            // change state
            hasElements = !addressesOfCurrentPage.isEmpty();

            if (hasElements) {
                LOG.info(String.format("Processing page %d of %d", currentPage + 1, totalPages));

                for (StaticAddress staticAddress : addressesOfCurrentPage) {
                    staticAddress.setUniqueId(staticAddress.getUniqueId());
                    repository.save(staticAddress);
                }

                currentPage = currentPage + 1;
            }
        }

        LOG.info("Finished filling the hashkeys");
    }


    @Override
    public List<StaticAddress> getAddressesInBoundingBox(GeoLocation geoLocation, Double km) {

        Assert.notNull(geoLocation);
        Assert.notNull(km);

        BoundingBox box = geoLocation.getBoundingBox(km);
        GeoLocation lowerLeft = box.getLowerLeft();
        GeoLocation upperRight = box.getUpperRight();

        return repository.findByBoundingBox(lowerLeft.getLatitude(), upperRight.getLatitude(), lowerLeft.getLongitude(),
                upperRight.getLongitude());
    }


    private StaticAddress updateStaticAddress(StaticAddress staticAddress) {

        StaticAddress staticAddressFromDb = repository.findOne(staticAddress.getId());

        boolean addressParametersDifferent = staticAddressFromDb.areAddressParametersDifferent(staticAddress);

        boolean coordinatesDifferent = staticAddressFromDb.areLatitudeAndLongitudeDifferent(staticAddress);

        if (addressParametersDifferent && checkDuplicateAddressParameters(staticAddress)) {
            throw new StaticAddressDuplicationException();
        }

        if (coordinatesDifferent && checkOnDuplicateCoordinates(staticAddressFromDb, staticAddress)) {
            throw new StaticAddressCoordinatesDuplicationException();
        }

        return repository.save(staticAddress);
    }


    private StaticAddress saveNewStaticAddress(StaticAddress staticAddress) {

        if (staticAddress.getUniqueId() == null) {
            staticAddress.setUniqueId(determineUniqueId());
        }

        if (checkDuplicateAddressParameters(staticAddress)) {
            throw new StaticAddressDuplicationException();
        }

        if (checkOnDuplicateCoordinates(staticAddress)) {
            throw new StaticAddressCoordinatesDuplicationException();
        }

        return repository.save(staticAddress);
    }


    BigInteger determineUniqueId() {

        String entityName = StaticAddress.class.getAnnotation(Entity.class).name();
        BigInteger nextUniqueId = uniqueIdSequenceService.getNextId(entityName);
        boolean isUniqueIdAlreadyAssigned = isUniqueIdAlreadyAssigned(nextUniqueId);

        while (isUniqueIdAlreadyAssigned) {
            // In this loop we increment the ID by ourselves to avoid write-accesses to the DB for performance
            LOG.warn("StaticAddress uniqueId {} already assigned - trying next uniqueId", nextUniqueId);
            nextUniqueId = nextUniqueId.add(BigInteger.ONE);

            if (!isUniqueIdAlreadyAssigned(nextUniqueId)) {
                isUniqueIdAlreadyAssigned = false;
                uniqueIdSequenceService.setNextId(entityName, nextUniqueId);
            }
        }

        return nextUniqueId;
    }


    private boolean isUniqueIdAlreadyAssigned(BigInteger uniqueId) {

        return repository.findByUniqueId(uniqueId) != null;
    }


    private boolean checkOnDuplicateCoordinates(StaticAddress staticAddressFromDb, StaticAddress staticAddress) {

        // compare coordinates
        if (null != staticAddressFromDb && staticAddressFromDb.equals(staticAddress)) {
            return false;
        }

        StaticAddress addressFromDb = repository.findByLatitudeAndLongitude(staticAddress.getLatitude(),
                staticAddress.getLongitude());

        return null != addressFromDb;
    }


    private boolean checkOnDuplicateCoordinates(StaticAddress staticAddress) {

        return this.checkOnDuplicateCoordinates(null, staticAddress);
    }


    private boolean checkDuplicateAddressParameters(StaticAddress staticAddress) {

        List<StaticAddress> staticAddresses = repository.findByCityAndSuburbAndPostalcode(
                staticAddress.getCityNormalized(), staticAddress.getSuburbNormalized(), staticAddress.getPostalcode());

        return !staticAddresses.isEmpty();
    }


    /**
     * Similar to method executeORSearch, only using other repository methods.
     */
    private List<StaticAddress> executeANDSearch(String postalCode, String city, String country) {

        List<StaticAddress> addresses = new ArrayList<>();

        if (StringUtils.hasText(country)) {
            addresses = repository.findByCountryAndPostalCodeAndCity(postalCode, getParameterWithWildcard(city),
                    country);
        } else if (StringUtils.hasText(city)) {
            addresses = repository.findByPostalCodeAndCity(postalCode, getParameterWithWildcard(city));
        } else {
            repository.findByPostalcode(postalCode);
        }

        return addresses;
    }


    /**
     * Similar to method executeANDSearch, only using other repository methods.
     */
    private List<StaticAddress> executeORSearch(String postalCode, String city, String country) {

        List<StaticAddress> addresses;

        if (StringUtils.hasText(country)) {
            addresses = repository.findByCountryAndPostalCodeOrCity(postalCode, getParameterWithWildcard(city),
                    country);
        } else if (StringUtils.hasText(city)) {
            addresses = repository.findByPostalCodeOrCity(postalCode, getParameterWithWildcard(city));
        } else {
            addresses = repository.findByPostalcode(postalCode);
        }

        return addresses;
    }


    /**
     * Adds wildcard to String if it is not empty and returns the new String.
     *
     * @param  param
     *
     * @return  new String with wildcard
     */
    private String getParameterWithWildcard(String param) {

        // rule for wildcard city: "city%"
        if (StringUtils.hasText(param)) {
            return param + WILDCARD;
        }

        return param;
    }


    /**
     * This is a fallback method if neither executeANDSearch nor executeORSearch have a result. The city string is split
     * on whitespaces. The current implementation executes the search only for first element of the split string.
     *
     * @param  postalCode
     * @param  city
     * @param  country
     *
     * @return  {@link java.util.List} of {@link StaticAddress} matching the given search parameters
     */
    private List<StaticAddress> executeSplitSearch(String postalCode, String city, String country) {

        Assert.notNull(city);

        String[] singleSearchParameters = city.split(" ");

        String firstString = normalizerService.normalize(singleSearchParameters[0]);
        List<StaticAddress> addresses = executeANDSearch(postalCode, firstString, country);

        if (addresses.isEmpty()) {
            addresses = executeORSearch(postalCode, firstString, country);
        }

        return addresses;
    }


    /**
     * Retrieves total amount of pages, according to pagesize.
     *
     * @param  currentPage
     * @param  pageSize
     *
     * @return
     */
    private int getTotalPages(int currentPage, int pageSize) {

        Pageable pageable = new PageRequest(currentPage, pageSize);

        return getAllPagesForEmptyItems(pageable).getTotalPages();
    }


    /**
     * Retreive only empty pages from repository..
     *
     * @param  pageable
     *
     * @return
     */
    private Page<StaticAddress> getAllPagesForEmptyItems(Pageable pageable) {

        return repository.findMissingHashKeys(pageable);
    }


    /**
     * Get pageable addresses according to the given page size.
     *
     * @param  startPage
     * @param  pageSize
     *
     * @return
     */
    private List<StaticAddress> getAddresses(int startPage, int pageSize) {

        Pageable pageable = new PageRequest(startPage, pageSize);

        return getAllPagesForEmptyItems(pageable).getContent();
    }
}
