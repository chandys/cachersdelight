package net.compoundeye.cachersdelight;

/**
 * Data storage class for Geocache overview data.
 * Does not provide any getter/setter for performance reasons,
 * as this data is needed to populate a potentially large list
 * of caches.
 * 
 * For cache details, see corresponding GeocacheDetailData class.
 * 
 * @author Andre Wichmann
 *
 */
public class GeocacheOverviewData {

	public String id = null,
		name = null,
		owner = null;
	public double longitude = -1,
		latitude = -1;
	public int terrain = -1,
		difficulty = -1,
		rating = -1;
}
