package datasource;

/**
 * This class specifies what is needed for table data gateways in order for the
 * TableDataGatewayManager to be able to take care of them.
 *
 * 1) They must be named myNameTableDataGatewayMock and myNameTableDataGatewayRDS in
 * order to be managed under the name "myName"
 * 2) They must have a public no-arg constructor (or use the default one)
 * 3) They can't be singletons.  The factory will keep a hashmap of them and will
 * give everyone the same one, so singletons are not necessary
 */
public interface TableDataGateway
{

    void resetTableGateway();
}
