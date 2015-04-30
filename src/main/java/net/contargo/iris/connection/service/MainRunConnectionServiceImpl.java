package net.contargo.iris.connection.service;

import net.contargo.iris.connection.MainRunConnection;
import net.contargo.iris.connection.persistence.MainRunConnectionRepository;
import net.contargo.iris.route.RouteType;
import net.contargo.iris.seaport.Seaport;
import net.contargo.iris.terminal.Terminal;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.util.Assert;

import java.math.BigInteger;

import java.util.List;


/**
 * Service for operations with the {@link net.contargo.iris.connection.MainRunConnection}.
 *
 * @author  Sandra Thieme - thieme@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 * @author  Oliver Messner - messner@synyx.de
 */
@Transactional
public class MainRunConnectionServiceImpl implements MainRunConnectionService {

    private final MainRunConnectionRepository mainRunConnectionRepository;

    public MainRunConnectionServiceImpl(MainRunConnectionRepository mainRunConnectionRepository) {

        this.mainRunConnectionRepository = mainRunConnectionRepository;
    }

    /**
     * @see  MainRunConnectionService#getAll()
     */
    @Override
    public List<MainRunConnection> getAll() {

        return mainRunConnectionRepository.findAll();
    }


    /**
     * @see  MainRunConnectionService#getAllActive()
     */
    @Override
    public List<MainRunConnection> getAllActive() {

        return mainRunConnectionRepository.findByEnabled(true);
    }


    /**
     * @see  MainRunConnectionService#getById(Long)
     */
    @Override
    public MainRunConnection getById(Long id) {

        return mainRunConnectionRepository.findOne(id);
    }


    /**
     * @see  MainRunConnectionService#save(net.contargo.iris.connection.MainRunConnection)
     */
    @Override
    public MainRunConnection save(MainRunConnection mainrunConnection) {

        return mainRunConnectionRepository.save(mainrunConnection);
    }


    /**
     * @see  MainRunConnectionService#findRoutingConnectionBetweenTerminalAndSeaportByType(
     *       net.contargo.iris.terminal.Terminal, net.contargo.iris.seaport.Seaport, net.contargo.iris.route.RouteType)
     */
    @Override
    public MainRunConnection findRoutingConnectionBetweenTerminalAndSeaportByType(Terminal terminal, Seaport seaport,
        RouteType routeType) {

        Assert.notNull(terminal);
        Assert.notNull(seaport);
        Assert.notNull(routeType);

        return mainRunConnectionRepository.findByTerminalAndSeaportAndRouteTypeAndEnabled(terminal, seaport, routeType,
                true);
    }


    /**
     * @see  MainRunConnectionService#isAlreadyApplied(net.contargo.iris.connection.MainRunConnection)
     */
    @Override
    public Boolean isAlreadyApplied(MainRunConnection mainrunConnection) {

        MainRunConnection dbMainrunConnection = mainRunConnectionRepository.findBySeaportAndTerminalAndRouteType(
                mainrunConnection.getSeaport(), mainrunConnection.getTerminal(), mainrunConnection.getRouteType());

        return null != dbMainrunConnection;
    }


    /**
     * @see  MainRunConnectionService#isAlreadyAppliedAndNotThis(net.contargo.iris.connection.MainRunConnection)
     */
    @Override
    public Boolean isAlreadyAppliedAndNotThis(MainRunConnection mainrunConnection) {

        MainRunConnection dbMainrunConnection =
            mainRunConnectionRepository.findBySeaportAndTerminalAndRouteTypeAndIdNot(mainrunConnection.getSeaport(),
                mainrunConnection.getTerminal(), mainrunConnection.getRouteType(), mainrunConnection.getId());

        return null != dbMainrunConnection;
    }


    @Override
    @Transactional(readOnly = true)
    public List<MainRunConnection> getConnectionsForTerminal(BigInteger terminalUID) {

        return mainRunConnectionRepository.findConnectionsByTerminalUniqueId(terminalUID);
    }
}
