package net.compoundeye.cachersdelight;

public class GeocacheData {
	
	// Cache types
	public static final int CACHETYPE_UNKNOWN = -1;
	public static final int CACHETYPE_TRADITIONAL = 0;
	public static final int CACHETYPE_MULTI = 1;
	public static final int CACHETYPE_EARTH = 2;
	public static final int CACHETYPE_EVENT = 3;
	public static final int CACHETYPE_LETTERBOX = 4;
	public static final int CACHETYPE_VIRTUAL = 5;
	public static final int CACHETYPE_MYSTERY = 6;
	public static final int CACHETYPE_WEBCAM = 7;
	public static final int CACHETYPE_CITO = 8;
	public static final int CACHETYPE_MEGAEVENT = 9;
	public static final int CACHETYPE_REVERSE = 10;
	public static final int CACHETYPE_WHERIGO = 11;

	// Cache sizes
	public static final int CACHESIZE_UNKNOWN = -1;
	public static final int CACHESIZE_MICRO = 0;
	public static final int CACHESIZE_SMALL = 1;
	public static final int CACHESIZE_REGULAR = 2;
	public static final int CACHESIZE_LARGE = 3;
	public static final int CACHESIZE_NOTCHOSEN = 4;

	public GeocacheOverviewData overviewData = null;
	public GeocacheDetailData detailData = null;
	
	public GeocacheData(GeocacheOverviewData overviewData, GeocacheDetailData detailData) {
		this.overviewData = overviewData;
		this.detailData = detailData;
	}
}
