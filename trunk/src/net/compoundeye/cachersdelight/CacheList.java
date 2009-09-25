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
			Log.w(TAG, dir.getName() + " is no directory!");
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
			xpp.setInput(new BufferedInputStream(new FileInputStream(file)), null);

			if (file.getName().toLowerCase().endsWith(".loc")) {
				syncLocFileWithDatabase(dbName, xpp, stats);
			} else if (file.getName().toLowerCase().endsWith(".gpx")) {
				syncGpxFileWithDatabase(dbName, xpp, stats);
			} else {
				// TODO: Alert user
				Log.w(TAG, "WARNING: " + file.getName()
						+ " is of unknown file type!");
			}
		} catch (FileNotFoundException e) {
			// TODO: Alert user
			Log.w(TAG, "File not found: " + file.getName());
		} catch (IOException e) {
			// TODO: Alert user
			Log.w(TAG, "IEOxception: " + e);
		} catch (XmlPullParserException e) {
			// TODO: Alert user
			Log.w(TAG, "XML error: " + e);
		}
	}

	
	
	/**
	 * Parse a LOC XML documet and sync it with a given database.
	 * Relies upon the fact that every tag name is unique inside a
	 * waypoint entry. Ugly, but works. :-)
	 * 
	 * @param dbName Name of the database to sync into
	 * @param xpp A XmlPullParser set to START_DOCUMENT
	 * @param stats A DBSyncStatistics to keep statistics
	 * 
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private void syncLocFileWithDatabase(String dbName, XmlPullParser xpp, DBSyncStatistics stats)
			throws XmlPullParserException, IOException {
		String tagName,
			currentText = null,
			attributeName;
		int eventType,
			numAttributes,
			byIndex,
			i;
		GeocacheOverviewData cacheData = null;
		
		eventType = xpp.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch(eventType) {
			case XmlPullParser.START_DOCUMENT:
				Log.d(TAG, "Start");
				break;
			case XmlPullParser.START_TAG:
				tagName = xpp.getName().toLowerCase();
				Log.d(TAG, "TagName: " + tagName);
				if (tagName.equals("loc")) {
					// ignore loc tags
				} else if (tagName.equals("waypoint")) {
					// sync last cache, if there is one then start new one
					if (cacheData != null) {
						// store data in DB
					} else {
						cacheData = new GeocacheOverviewData();
					}
				} else if (tagName.equals("name")) {
					// get cache ID
					numAttributes = xpp.getAttributeCount();
					for (i = 0; i < numAttributes; i++) {
						attributeName = xpp.getAttributeName(i).toLowerCase();
						if (attributeName.equals("id")) {
							cacheData.id = xpp.getAttributeValue(i);
							Log.d(TAG, "id = " + cacheData.id);
						} else {
							// unknown attribute! Log and ignore.
							Log.w(TAG, "WARNING: Unknown attribute for name: " + attributeName);							
						}
					}
				} else if (tagName.equals("coord")) {
					// get coords
					numAttributes = xpp.getAttributeCount();
					for (i = 0; i < numAttributes; i++) {
						attributeName = xpp.getAttributeName(i).toLowerCase();
						if (attributeName.equals("lat")) {
							cacheData.latitude = Double.parseDouble(xpp.getAttributeValue(i));
							Log.d(TAG, "lat = " + cacheData.latitude);
						} else if (attributeName.equals("lon")) {
							cacheData.longitude = Double.parseDouble(xpp.getAttributeValue(i));
							Log.d(TAG, "long = " + cacheData.longitude);
						} else {
							// unknown attribute! Log and ignore.
							Log.w(TAG, "WARNING: Unknown attribute for coord: " + attributeName);							
						}
					}
				} else if (tagName.equals("type")) {
					// ignore tag "type" - should be "geocache", but don't check for performance reasons
				} else if (tagName.equals("link")) {
					// ignore link tags for now.
					// Might change if support for other geocaching sites will be added
				} else {
					// unknown tag! Log and ignore.
					Log.w(TAG, "WARNING: Unknown tag: " + tagName);
				}
				break;
			case XmlPullParser.TEXT:
				// Since getName() won't return the last opened tag unfortunately, store
				// text for later use in case we're inside a <name> tag
				// TODO: If performance is a problem, check if xpp.getTextCharacters() is faster (should avoid generating a new String object)
				currentText = xpp.getText();
				break;
			case XmlPullParser.END_TAG:
				tagName = xpp.getName().toLowerCase();
				if (tagName.equals("name")) {
					// Extract name and owner
					if (currentText == null) {
						Log.w(TAG, "WARNING: name tag has no text!");
					} else {
						byIndex = currentText.lastIndexOf(" by ");
						if (byIndex == -1) {
							Log.w(TAG, "WARNING: name tag has no ' by ' substring!");
						} else {
							cacheData.name = currentText.substring(0, byIndex);
							cacheData.owner = currentText.substring(byIndex + 4);
							currentText = null;
							Log.d(TAG, "Name: " + cacheData.name + ", owner: " + cacheData.owner);
						}
					}
				}
				break;
			default:
				// This should not happen!
				Log.e(TAG, "WARNING: This should not have happened: eventType = " + eventType);
			}
			
			eventType = xpp.next();
		}
	}


	
	private void syncGpxFileWithDatabase(String dbName, XmlPullParser xpp, DBSyncStatistics stats)
			throws XmlPullParserException {
		Log.w(TAG, "WARNING: GPX parser not yet implemented");
	}

}
