package net.compoundeye.cachersdelight;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.util.Log;


/**
 * Wrapper for access to geocache databases. Hides internal DB structure and
 * exposes standard access methods.
 * 
 * @author Andre Wichmann
 *
 */
public class DBManager {

	private static final String TAG = "DBManager";

	// result values
	public static final int SYNC_ERROR = -1;
	public static final int SYNC_ADDED = 0;
	public static final int SYNC_UPDATED = 1;
		
	// SQL strings
	private static final String DATABASE_CREATE =
		"CREATE TABLE geocache_overview (" +
			"_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"cacheid TEXT UNIQUE NON NULL," +
			"name TEXT NON NULL" +
			"owner TEXT NON NULL" +
			"longitude REAL NON NULL" +
			"latitude REAL NON NULL" +
			"terrain REAL NON NULL" +
			"difficulty REAL NON NULL" +
			"rating REAL NON NULL" +
			");";
	// database strings
	private static final String DATABASE_NAME = "standard";
	private static final String DATABASE_TABLE = "geocache_overview";
	private static final int DATABASE_VERSION = 1;


	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private final Context mCtx;

	/**
	 * Inner helper class.
	 * @see SQLiteOpenHelper
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper {

		private static final String TAG = "DBManager.DatabaseHelper";
		
		// Name of the currently used DB
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.e(TAG, "ERROR: onUpgrade not implemented!");
		}
	}
    
	
	
	/**
	 * Constructor. Needs the context to work.
	 * 
	 * @param ctx
	 *            the Context within which to work
	 */
	public DBManager(Context ctx) {
		this.mCtx = ctx;
	}
    

	
	/**
	 * Opens the database. If none exists for the given name, a new
	 * one is created. If that fails, an SQLException is thrown.
	 * 
	 * @return this (self reference, allowing this to be chained in an
	 *         initialization call)
	 * @throws SQLException
	 *             if the database could be neither opened or created
	 */
	public DBManager open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	
	
	/**
	 * Closes the database.
	 */
	public void close() {
		mDbHelper.close();
	}
	
	
	
	/**
	 * Syncs a geocache with the database.
	 * If no cache with the same ID exists in the DB, a new entry is created.
	 * If there is already a cache with the same ID, its stats are updated
	 * using the given values.
	 * 
	 * @param cache The GeocacheData to sync into the database
	 * @return a SYNC_ result value: ADDED, UPDATED or ERROR
	 */
	public int syncGeocache(GeocacheData cache) {
		// XXX
		return SYNC_ERROR;
	}
}
