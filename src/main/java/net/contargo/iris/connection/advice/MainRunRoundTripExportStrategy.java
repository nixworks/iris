package net.contargo.iris.connection.advice;

import net.contargo.iris.GeoLocation;
import net.contargo.iris.connection.MainRunConnection;
import net.contargo.iris.container.ContainerState;
import net.contargo.iris.container.ContainerType;
import net.contargo.iris.route.Route;
import net.contargo.iris.route.RouteType;
import net.contargo.iris.route.builder.RouteBuilder;


/**
 * @author  Jörg Alberto Hoffmann - hoffmann@synyx.de
 * @see  MainRunStrategy
 * @see  MainRunAdvisor
 */
class MainRunRoundTripExportStrategy implements MainRunStrategy {

    @Override
    public Route getRoute(MainRunConnection connection, GeoLocation destination, ContainerType containerType) {

        RouteBuilder routeBuilder = new RouteBuilder(connection.getSeaport(), containerType, ContainerState.EMPTY);

        if (connection.getSubConnections().isEmpty()) {
            routeBuilder.goTo(connection.getTerminal(), connection.getRouteType());
        } else {
            routeBuilder.goToTerminalViaSubConnections(connection.getTerminal(), connection.getSubConnections());
        }

        routeBuilder.goTo(destination, RouteType.TRUCK);
        routeBuilder.loadContainer();
        routeBuilder.goTo(connection.getTerminal(), RouteType.TRUCK);

        if (connection.getSubConnections().isEmpty()) {
            routeBuilder.goTo(connection.getSeaport(), connection.getRouteType());
        } else {
            routeBuilder.goToSeaportViaSubConnections(connection.getSeaport(), connection.getSubConnections());
        }

        routeBuilder.responsibleTerminal(connection.getTerminal());

        return routeBuilder.getRoute();
    }
}
