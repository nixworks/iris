package net.contargo.iris.seaport.api;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import net.contargo.iris.api.AbstractController;
import net.contargo.iris.api.NotFoundException;
import net.contargo.iris.seaport.dto.SeaportDto;
import net.contargo.iris.seaport.dto.SeaportDtoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


/**
 * Public API controller that responds to seaport or terminal requests.
 *
 * @author  Sandra Thieme - thieme@synyx.de
 * @author  Oliver Messner - messner@synyx.de
 * @author  David Schilling - schilling@synyx.de
 */
@Api(value = "/seaports", description = "API endpoint to manage seaports.")
@Controller
@RequestMapping(value = "/seaports")
public class SeaportApiController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(SeaportApiController.class);

    private final SeaportDtoService seaportDtoService;

    @Autowired
    public SeaportApiController(SeaportDtoService seaportDtoService) {

        this.seaportDtoService = seaportDtoService;
    }

    @ApiOperation(value = "Returns all active seaports.", notes = "Returns all active seaports.")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public SeaportsResponse getSeaports() {

        SeaportsResponse response = new SeaportsResponse();

        response.add(linkTo(methodOn(getClass()).getSeaports()).withSelfRel());

        Set<SeaportDto> ports = new HashSet<>();
        ports.addAll(seaportDtoService.getAllActive());

        response.setSeaports(ports);

        LOG.info("API: Returning {} active seaports ", ports.size());

        return response;
    }


    @ApiOperation(value = "Returns the seaport for the given UID.", notes = "Returns the seaport for the given UID.")
    @RequestMapping(value = SLASH + "{seaportuid}", method = RequestMethod.GET)
    @ModelAttribute(RESPONSE)
    public SeaportResponse getSeaportById(@PathVariable("seaportuid") BigInteger seaportUID) {

        SeaportResponse response = new SeaportResponse();

        response.add(linkTo(methodOn(getClass()).getSeaportById(seaportUID)).withSelfRel());
        response.add(linkTo(methodOn(getClass()).getSeaports()).withRel("seaports"));

        SeaportDto port = seaportDtoService.getByUid(seaportUID);

        if (port == null) {
            throw new NotFoundException("Cannot find Seaport for UID " + seaportUID);
        }

        response.setSeaport(port);

        LOG.info("API: Returning seaport {}", port.getName());

        return response;
    }


    @ApiOperation(
        value = "Saves the given seaport with the specified UID.",
        notes = "Saves the given seaport with the specified UID.", response = Void.class
    )
    @RequestMapping(value = SLASH + "{seaportUid}", method = RequestMethod.PUT)
    public ResponseEntity syncSeaport(@PathVariable("seaportUid") BigInteger seaportUID,
        @Valid @RequestBody SeaportDto seaport) {

        boolean update = seaportDtoService.existsByUniqueId(seaportUID);

        if (update) {
            seaportDtoService.updateSeaport(seaportUID, seaport);

            LOG.info("API: Updating seaport with unique id {}", seaportUID);

            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            seaport.setUniqueId(seaportUID.toString());

            seaportDtoService.save(seaport);

            LOG.info("API: Creating seaport with unique id {}", seaportUID);

            return new ResponseEntity(HttpStatus.CREATED);
        }
    }
}
