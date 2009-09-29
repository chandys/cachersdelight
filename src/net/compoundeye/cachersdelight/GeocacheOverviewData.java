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

	public static final double COORD_UNDEFINED = -1;
	public static final int RATING_UNDEFINED = -1;
	
	public String id = null,
		name = null,
		owner = null;
	public double longitude = COORD_UNDEFINED,
		latitude = COORD_UNDEFINED;
	public int terrain = RATING_UNDEFINED,
		difficulty = RATING_UNDEFINED,
		rating = RATING_UNDEFINED;
}
