package net.contargo.iris.co2.advice;

import net.contargo.iris.route.RouteType;


/**
 * @author  Oliver Messner - messner@synyx.de
 */
public class Co2PartStrategyAdvisor {

    private Co2PartStrategy bargeStrategy;
    private Co2PartStrategy railStrategy;
    private Co2PartStrategy truckStrategy;
    private Co2PartStrategy bargeRailStrategy;

    Co2PartStrategyAdvisor() {

        // no-arg constructor used for unit testing
    }


    public Co2PartStrategyAdvisor(Co2PartStrategy bargeStrategy, Co2PartStrategy railStrategy,
        Co2PartStrategy truckStrategy, Co2PartStrategy bargeRailStrategy) {

        this.bargeStrategy = bargeStrategy;
        this.railStrategy = railStrategy;
        this.truckStrategy = truckStrategy;
        this.bargeRailStrategy = bargeRailStrategy;
    }

    void setBargeStrategy(Co2PartStrategy bargeStrategy) {

        this.bargeStrategy = bargeStrategy;
    }


    void setRailStrategy(Co2PartStrategy railStrategy) {

        this.railStrategy = railStrategy;
    }


    void setTruckStrategy(Co2PartStrategy truckStrategy) {

        this.truckStrategy = truckStrategy;
    }


    void setBargeRailStrategy(Co2PartStrategy bargeRailStrategy) {

        this.bargeRailStrategy = bargeRailStrategy;
    }


    public Co2PartStrategy advice(RouteType routeType) {

        if (routeType == RouteType.BARGE) {
            return bargeStrategy;
        }

        if (routeType == RouteType.RAIL) {
            return railStrategy;
        }

        if (routeType == RouteType.TRUCK) {
            return truckStrategy;
        }

        if (routeType == RouteType.BARGE_RAIL) {
            return bargeRailStrategy;
        }

        throw new IllegalStateException("Cannot determine co2 for route part of type " + routeType);
    }
}
