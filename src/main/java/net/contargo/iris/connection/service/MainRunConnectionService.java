package net.contargo.iris.connection.service;

import net.contargo.iris.connection.MainRunConnection;
import net.contargo.iris.route.RouteType;
import net.contargo.iris.seaport.Seaport;
import net.contargo.iris.terminal.Terminal;

import java.math.BigInteger;

import java.util.List;


/**
 * Services that provides handling of {@link net.contargo.iris.connection.MainRunConnection}s.
 *
 * @author  Sandra Thieme - thieme@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 * @author  Oliver Messner - messner@synyx.de
 */
public interface MainRunConnectionService {

    /**
     * Finds all {@link net.contargo.iris.connection.MainRunConnection}s.
     *
     * @return  a list of {@link net.contargo.iris.connection.MainRunConnection}s
     */
    List<MainRunConnection> getAll();


    /**
     * Finds all active, i.e. enabled {@link net.contargo.iris.connection.MainRunConnection}s
     *
     * @return  a list of {@link net.contargo.iris.connection.MainRunConnection}s
     */
    List<MainRunConnection> getAllActive();


    /**
     * Finds a {@link net.contargo.iris.connection.MainRunConnection}.
     *
     * @param  id  the {@link net.contargo.iris.connection.MainRunConnection}'s id
     *
     * @return  the {@link net.contargo.iris.connection.MainRunConnection} with the specified {@code id}
     */
    MainRunConnection getById(Long id);


    /**
     * Saves a {@link net.contargo.iris.connection.MainRunConnection}.
     *
     * @param  mainrunConnection  the {@link net.contargo.iris.connection.MainRunConnection} to be saved
     *
     * @return  the saved {MainRunConnection} that does not have a null id property
     */
    MainRunConnection save(MainRunConnection mainrunConnection);


    /**
     * Finds a {@link net.contargo.iris.connection.MainRunConnection} with the specified attributes.
     *
     * @param  terminal  the {@link net.contargo.iris.connection.MainRunConnection}'s {@link Terminal}
     * @param  seaport  the {@link net.contargo.iris.connection.MainRunConnection}'s {@link Seaport}
     * @param  routeType  the {@link net.contargo.iris.connection.MainRunConnection}'s {@link RouteType}
     *
     * @return  a {@link net.contargo.iris.connection.MainRunConnection}
     */
    MainRunConnection findRoutingConnectionBetweenTerminalAndSeaportByType(Terminal terminal, Seaport seaport,
        RouteType routeType);


    List<MainRunConnection> getConnectionsForTerminal(BigInteger terminalUID);
}
