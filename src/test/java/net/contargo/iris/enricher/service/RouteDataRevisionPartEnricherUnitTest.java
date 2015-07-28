package net.contargo.iris.enricher.service;

import net.contargo.iris.address.Address;
import net.contargo.iris.route.RoutePart;
import net.contargo.iris.routedatarevision.RouteDataRevision;
import net.contargo.iris.routedatarevision.service.RouteDataRevisionService;
import net.contargo.iris.seaport.Seaport;
import net.contargo.iris.terminal.Terminal;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static net.contargo.iris.route.RouteType.BARGE;
import static net.contargo.iris.route.RouteType.TRUCK;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.mockito.Mockito.when;


/**
 * Unit test of {@link net.contargo.iris.enricher.service.RouteDataRevisionPartEnricher RouteDataRevisionPartEnricher}.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class RouteDataRevisionPartEnricherUnitTest {

    protected static final BigDecimal AIRLINE_DISTANCE = BigDecimal.ZERO;
    protected static final BigDecimal TOLL_DISTANCE_ONE_WAY = BigDecimal.ONE;
    protected static final BigDecimal TRUCK_DISTANCE_ONE_WAY = BigDecimal.TEN;

    private RoutePartEnricher sut;

    @Mock
    private RouteDataRevisionService routeDataRevisionServiceMock;

    private EnricherContext enricherContext;
    private Terminal terminal;
    private Address address;

    @Before
    public void before() {

        terminal = new Terminal();
        address = new Address();

        RouteDataRevision routeDataRevision = new RouteDataRevision();
        routeDataRevision.setAirlineDistanceInMeter(AIRLINE_DISTANCE);
        routeDataRevision.setTollDistanceOneWayInMeter(TOLL_DISTANCE_ONE_WAY);
        routeDataRevision.setTruckDistanceOneWayInMeter(TRUCK_DISTANCE_ONE_WAY);

        when(routeDataRevisionServiceMock.getRouteDataRevision(terminal, address)).thenReturn(routeDataRevision);

        enricherContext = new EnricherContext.Builder().build();

        sut = new RouteDataRevisionPartEnricher(routeDataRevisionServiceMock);
    }


    @Test
    public void enrichTerminal2Address() throws CriticalEnricherException {

        RoutePart routePart = new RoutePart(terminal, address, TRUCK);

        sut.enrich(routePart, enricherContext);

        assertRoutePartEnriched(routePart);
    }


    @Test
    public void enrichAddress2Terminal() throws CriticalEnricherException {

        RoutePart routePart = new RoutePart(address, terminal, TRUCK);

        sut.enrich(routePart, enricherContext);

        assertRoutePartEnriched(routePart);
    }


    @Test
    public void wrongRouteType() throws CriticalEnricherException {

        RoutePart routePart = new RoutePart(terminal, address, BARGE);

        sut.enrich(routePart, enricherContext);

        assertRoutePartNotEnriched(routePart);
    }


    @Test
    public void throwInternExceptionWrongOriginType() throws CriticalEnricherException {

        RoutePart routePart = new RoutePart(new Seaport(), address, TRUCK);

        sut.enrich(routePart, enricherContext);

        assertRoutePartNotEnriched(routePart);
    }


    @Test
    public void throwInternExceptionWrongDestinationType() throws CriticalEnricherException {

        RoutePart routePart = new RoutePart(terminal, new Seaport(), TRUCK);

        sut.enrich(routePart, enricherContext);

        assertRoutePartNotEnriched(routePart);
    }


    private void assertRoutePartEnriched(RoutePart routePart) {

        assertThat(routePart.getData().getAirLineDistance(), is(AIRLINE_DISTANCE));
        assertThat(routePart.getData().getTollDistance(), is(TOLL_DISTANCE_ONE_WAY));
        assertThat(routePart.getData().getDistance(), is(TRUCK_DISTANCE_ONE_WAY));
    }


    private void assertRoutePartNotEnriched(RoutePart routePart) {

        assertThat(routePart.getData().getAirLineDistance(), is(nullValue()));
        assertThat(routePart.getData().getTollDistance(), is(nullValue()));
        assertThat(routePart.getData().getDistance(), is(nullValue()));
    }
}
