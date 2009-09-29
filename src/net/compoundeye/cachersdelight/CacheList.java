package net.compoundeye.cachersdelight;

import java.io.*;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import net.compoundeye.cachersdelight.util.*;

import org.xmlpull.v1.*;


public class CacheList extends Activity {

	// Log tag
	private static final String TAG = "CacheList";

	private DBManager dbManager;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Log.d(TAG, "Calling sync");
		dbManager = new DBManager(this);
		dbManager.open();
		GeocacheImporter imp = new GeocacheImporter();
		DBSyncStatistics stats = new DBSyncStatistics();
		imp.syncPathWithDatabase(dbManager, "/sdcard/download", stats);
		Log.d(TAG, "Added: " + stats.added + ", updated: " + stats.updated + ", failed: " + stats.failed);
		dbManager.close();
	}


}
