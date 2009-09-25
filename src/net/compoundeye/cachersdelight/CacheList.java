package net.compoundeye.cachersdelight;

import java.io.*;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import org.xmlpull.v1.*;

public class CacheList extends Activity {

	// Log tag
	private static final String TAG = "CacheList";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Log.d(TAG, "Calling sync");
		DBSyncStatistics stats = new DBSyncStatistics();
		syncPathWithDatabase("standard", "/sdcard/download", stats);
		Log.d(TAG, "Added: " + stats.added + ", updated: " + stats.updated + ", failed: " + stats.failed);
	}

	/**
	 * Syncs a database with all files LOC or GPX in a given directory.
	 * 
	 * @param dbName
	 *            Name of the database to sync into
	 * @param path
	 *            Path of the folder where the cache files reside
	 * 
	 * @return true if sync was successful
	 */

	private void syncPathWithDatabase(String dbName, String path, DBSyncStatistics stats) {
		File dir;
		File[] fileList;

		/**
		 * Helper class to filter out non-cache-files from file list
		 */
		final class CacheFileFilter implements FilenameFilter {
			public final boolean accept(File dir, String name) {
				return (name.endsWith(".loc") || name.endsWith(".LOC")
						|| name.endsWith(".gpx") || name.endsWith(".GPX"));
			}
		}

		dir = new File(path);
		if (dir.isDirectory()) {
			fileList = dir.listFiles(new CacheFileFilter());
			for (int i = 0; i < fileList.length; i++) {
				syncFileWithDatabase(dbName, fileList[i], stats);
			}
		} else {
			// TODO: Call syncFileWithDatabase?
			Log.d(TAG, dir.getName() + " is no directory!");
		}
	}

	/**
	 * Syncs a LOC or GPX File with a given database.
	 * 
	 * @param dbName
	 *            Name of the database to sync into
	 * @param file
	 *            the File to sync from
	 * @throws XmlPullParserException
	 */
	private void syncFileWithDatabase(String dbName, File file, DBSyncStatistics stats) {
		XmlPullParserFactory factory;
		XmlPullParser xpp;

		Log.d(TAG, "Syncing file " + file.getName());
		try {
			// Prepare XML parser
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			xpp = factory.newPullParser();
			xpp.setInput(new BufferedReader(new FileReader(file)));

			if (file.getName().toLowerCase().endsWith(".loc")) {
				syncLocFileWithDatabase(dbName, xpp, stats);
			} else if (file.getName().toLowerCase().endsWith(".gpx")) {
				syncGpxFileWithDatabase(dbName, xpp, stats);
			} else {
				// TODO: Alert user
				Log.d(TAG, "WARNING: " + file.getName()
						+ " is of unknown file type!");
			}
		} catch (FileNotFoundException e) {
			// TODO: Alert user
			Log.d(TAG, "File not found: " + file.getName());
		} catch (IOException e) {
			// TODO: Alert user
			Log.d(TAG, "IEOxception: " + e);
		} catch (XmlPullParserException e) {
			// TODO: Alert user
			Log.d(TAG, "XML error: " + e);
		}
	}

	
	
	private void syncLocFileWithDatabase(String dbName, XmlPullParser xpp, DBSyncStatistics stats)
			throws XmlPullParserException, IOException {
		int eventType = xpp.getEventType();

		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch(eventType) {
			case XmlPullParser.START_DOCUMENT:
				Log.d(TAG, "Start");
				break;
			case XmlPullParser.START_TAG:
				break;
			case XmlPullParser.TEXT:
				break;
			case XmlPullParser.END_TAG:
				break;
			case XmlPullParser.END_DOCUMENT:
				Log.d(TAG, "End");
				break;
			default:
				// This should not happen!
				Log.d(TAG, "WARNING: This should not have happened: eventType = " + eventType);
			}
			
			eventType = xpp.next();
		}
	}


	
	private void syncGpxFileWithDatabase(String dbName, XmlPullParser xpp, DBSyncStatistics stats)
			throws XmlPullParserException {
		Log.d(TAG, "WARNING: GPX parse not yet implemented");
	}

}
