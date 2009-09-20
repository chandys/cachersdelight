package net.compoundeye.cachersdelight;

import junit.framework.Assert;
import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.util.*;


public class DBManager {

	private static final String TAG = "DBManager";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	private static final String DATABASE_CREATE =
		"CREATE TABLE geocache_overview (" +
			"_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"id TEXT NON NULL," +
			"name TEXT NON NULL" +
			"owner TEXT NON NULL" +
			"longitude REAL NON NULL" +
			"latitude REAL NON NULL" +
			"terrain INTEGER NON NULL" +
			"difficulty INTEGER NON NULL" +
			"rating REAL NON NULL" +
			");";

	private static final String DATABASE_NAME = "standard";
	private static final String DATABASE_TABLE = "geocache_overview";
	private static final int DATABASE_VERSION = 1;

    private static class DatabaseHelper extends SQLiteOpenHelper {

    	DatabaseHelper(Context context) {
    		super(context, DATABASE_NAME, null, DATABASE_VERSION);
    	}

    	@Override
    	public void onCreate(SQLiteDatabase db) {
    		db.execSQL(DATABASE_CREATE);
    	}

    	@Override
    	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	}
    }
}
