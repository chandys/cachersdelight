package net.compoundeye.cachersdelight;

public class GeocacheOverviewData {

	public String id, name, owner;
	// Coordinates saved as int for performance
	public int longDegree, longFrac, latDegree, latFrac;
	public int terrain, difficulty, rating;
}