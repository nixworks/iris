package net.contargo.iris.routing.osrm;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

import java.util.List;


/**
 * Represents the response of an osrm request to /route in api version 5. Only relevant fields for iris are depicted
 * here. See https://github.com/Project-OSRM/osrm-backend/blob/master/docs/http.md#service-route
 *
 * @author  David Schilling - schilling@synyx.de
 */
public class Osrm5Response {

    private final BigDecimal distance;
    private final BigDecimal duration;
    private final BigDecimal toll;

    @JsonCreator
    public Osrm5Response(@JsonProperty("routes") List<Osrm5Route> routes) {

        Osrm5Route route = routes.get(0);
        this.distance = route.getDistance();
        this.duration = route.getDuration();
        this.toll = route.getToll();
    }

    public BigDecimal getDistance() {

        return distance;
    }


    public BigDecimal getDuration() {

        return duration;
    }


    public BigDecimal getToll() {

        return toll;
    }


    @Override
    public String toString() {

        return "Osrm5Response{"
            + "distance=" + distance
            + ", duration=" + duration
            + ", toll=" + toll + '}';
    }
}