package datasource;

import java.util.ArrayList;

/**
 * Define the things a gateway to a data source about maps needs to do
 */
interface MapTableDataGateway
{
	/**
	 * @return all of the map names in the data source
	 */
	ArrayList<String> getMapNames();
}
