package net.compoundeye.cachersdelight;

/**
 * Simple container for database sync statistics.
 * Uses no getter/setter for performance reasons.
 * 
 * @author Andre Wichmann
 *
 */
public class DBSyncStatistics {

	public int added = 0,
		updated = 0,
		failed = 0;
}
