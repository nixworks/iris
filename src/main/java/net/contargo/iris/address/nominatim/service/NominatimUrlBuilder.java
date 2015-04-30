package net.contargo.iris.address.nominatim.service;

import net.contargo.iris.GeoLocation;
import net.contargo.iris.countries.service.CountryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;

import java.lang.invoke.MethodHandles;

import java.net.URLEncoder;

import java.util.Map;


/**
 * To geocode an address {@link NominatimAddressService} needs a URL containing the base URL, different parameters and
 * the search query. In the application context the variable parameter are defined (like email and base url), the
 * requested format is always json since the {@link NominatimJsonResponseParser} creates Java objects by json.
 *
 * @author  Aljona Murygina - murygina@synyx.de
 * @author  Vincent Potucek - potucek@synyx.de
 */
class NominatimUrlBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String SEARCH_URL = "search.php?q=";
    private static final String SUBURB_URL = "suburb.php?place_id=";
    private static final String SUBURB_TYPE = "&suburb_type=";
    private static final String ACCEPT_LANGUAGE = "&accept-language=";
    private static final String FORMAT = "&format=json";
    private static final String ADDRESS_DETAILS = "&addressdetails=1";
    private static final String COUNTRY = "&countrycodes=";
    private static final String WHITESPACE = " ";
    private static final String OSMTYPE_W = "&osm_type=W";
    private static final String OSMID = "osm_id=";
    private static final String REVERSE_URL = "reverse?";
    private static final String LOG_TWO_PLACEHOLDERS = "{}{}";
    private static final String LOG_BUILT_URL = "Built URL: ";

    private final String baseUrl;
    private final String language;
    private final CountryService countryService;

    NominatimUrlBuilder(String baseUrl, String language, CountryService countryService) {

        this.countryService = countryService;

        Assert.hasText(baseUrl);
        Assert.hasText(language);

        this.baseUrl = baseUrl;
        this.language = language;
    }

    /**
     * Builds Nominatim's search url appending the query to the base URL.
     *
     * @param  street  + streetNumber
     * @param  postalCode
     * @param  city
     * @param  country
     * @param  name
     *
     * @return  ready URL to send a GET request to Nominatim
     */
    String buildUrl(String street, String postalCode, String city, String country, String name) {

        String url = baseUrl + SEARCH_URL + buildQuery(street, postalCode, city, name) + COUNTRY
            + encodeUrl(buildCountryCodeList(country)) + FORMAT + ADDRESS_DETAILS + ACCEPT_LANGUAGE + language;

        LOG.debug(LOG_TWO_PLACEHOLDERS, LOG_BUILT_URL, url);

        return url;
    }


    private String buildCountryCodeList(String country) {

        if (StringUtils.hasText(country)) {
            return country;
        } else {
            Map<String, String> cc = countryService.getCountries();

            return String.join(",", cc.values());
        }
    }


    /**
     * Builds Nominatim's suburb url (modified details page) using the given place ID and suburb type.
     *
     * @param  placeId  long
     * @param  suburbType  String
     *
     * @return  ready URL to send a GET request to Nominatim
     */
    String buildSuburbUrl(long placeId, String suburbType) {

        String suburbUrl = baseUrl + SUBURB_URL + placeId + FORMAT + ADDRESS_DETAILS + SUBURB_TYPE + suburbType;
        LOG.debug(LOG_TWO_PLACEHOLDERS, LOG_BUILT_URL, suburbUrl);

        return suburbUrl;
    }


    /**
     * Appends the query to the base URL using Nominatim's special phrases like 'suburb' for specific searching.
     *
     * @param  city  the city
     * @param  specialPhrase  'suburb' or 'village' or any other specific place type
     *
     * @return  ready URL to send a GET request to Nominatim
     */
    String buildSpecialPhrasesUrl(String city, String specialPhrase) {

        String specialPhrasesUrl = baseUrl + SEARCH_URL + buildSpecialPhraseQuery(city, specialPhrase) + "&limit=100"
            + FORMAT + ACCEPT_LANGUAGE + language + ADDRESS_DETAILS;
        LOG.debug(LOG_TWO_PLACEHOLDERS, LOG_BUILT_URL, specialPhrasesUrl);

        return specialPhrasesUrl;
    }


    /**
     * Build the osm url within the osm id.
     *
     * @param  osmId  to build osm url
     *
     * @return  The URL for requesting ONE Address by osmId. The osm Type used is "N" for Node by default.
     */
    String buildOsmUrl(long osmId) {

        String osmUrl = baseUrl + REVERSE_URL + OSMID + osmId + OSMTYPE_W + FORMAT;
        LOG.debug(LOG_TWO_PLACEHOLDERS, LOG_BUILT_URL, osmUrl);

        return osmUrl;
    }


    /**
     * Builds the query and converts it to URL encoded format with {@link java.net.URLEncoder} (e.g. replaces
     * whitespaces by plus and commas by %2C).
     *
     * @param  street:  street name + house number
     * @param  postalCode
     * @param  city
     * @param  name
     *
     * @return  search query String
     */
    String buildQuery(String street, String postalCode, String city, String name) {

        StringBuilder queryBuilder = new StringBuilder();
        appendStringWithSeparatorIfHasText(queryBuilder, street, ",");
        appendStringWithSeparatorIfHasText(queryBuilder, postalCode, WHITESPACE);
        appendStringWithSeparatorIfHasText(queryBuilder, city, WHITESPACE);
        appendStringWithSeparatorIfHasText(queryBuilder, name, WHITESPACE);

        String query = queryBuilder.toString();
        String encodedUrl = encodeUrl(query);

        return encodedUrl == null ? query : encodedUrl;
    }


    /**
     * Builds the query with special phrase and city and converts it to URL encoded format with
     * {@link java.net.URLEncoder}.
     *
     * @param  city
     * @param  specialPhrase
     *
     * @return  special phrases query
     */
    String buildSpecialPhraseQuery(String city, String specialPhrase) {

        StringBuilder queryBuilder = new StringBuilder();

        appendStringWithSeparatorIfHasText(queryBuilder, specialPhrase, WHITESPACE);
        appendStringWithSeparatorIfHasText(queryBuilder, city, "");

        String query = queryBuilder.toString();
        String encodedUrl = encodeUrl(query);

        return encodedUrl == null ? query : encodedUrl;
    }


    /**
     * Encodes an URL with {@link java.net.URLEncoder}.
     *
     * @param  url  String
     *
     * @return  encoded URL String
     */
    private String encodeUrl(String url) {

        String result = null;

        try {
            result = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            LOG.warn("Could not encode this String to URL: {} ", url);
        }

        return result;
    }


    /**
     * If the given String has text, append it with the given separator to the StringBuilder.
     *
     * @param  builder
     * @param  string
     * @param  separator
     */
    void appendStringWithSeparatorIfHasText(StringBuilder builder, String string, String separator) {

        if (StringUtils.hasText(string)) {
            builder.append(string).append(separator);
        }
    }


    String buildUrl(GeoLocation geoLocation) {

        if (geoLocation.getLatitude() == null || geoLocation.getLongitude() == null) {
            throw new IllegalArgumentException("Invalid Geo location: " + geoLocation);
        }

        String url = baseUrl + "reverse/" + "?format=json" + "&lat=" + geoLocation.getLatitude().toString() + "&lon="
            + geoLocation.getLongitude().toString();

        LOG.debug("Built request URL: {}", url);

        return url;
    }
}
