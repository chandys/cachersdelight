package net.compoundeye.cachersdelight;

import java.io.*;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class CacheList extends Activity {

	// Log tag
	private static final String TAG = "CacheList";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Log.d(TAG, "Calling sync");
        syncPathWithDatabase("standard", "/sdcard/download");
    }
    
    
    
    /**
     * Syncs a database with all files LOC or GPX in a given directory.
     *
     * @param dbName Name of the database to sync into
     * @param path Path of the folder where the cache files reside
     * 
     * @return true if sync was successful
     */
    
    private void syncPathWithDatabase(String dbName, String path) {
    	File dir;
    	File[] fileList;
    	
    	/**
    	 * Helper class to filter out non-cache-files from file list 
    	 */
    	final class CacheFileFilter implements FilenameFilter {
    	    public final boolean accept(File dir, String name) {
    	        return (name.endsWith(".loc") || name.endsWith(".LOC") ||
    	        		name.endsWith(".gpx") || name.endsWith(".GPX"));
    	    }
    	}

   		dir = new File(path);
   		fileList = dir.listFiles(new CacheFileFilter());
   		for (int i = 0; i < fileList.length; i++) {
   			syncFileWithDatabase(dbName, fileList[i]);
   		}
    }
    
    
    
    /**
     * Syncs a LOC or GPX File with a given database.
     * 
     * @param dbName Name of the database to sync into
     * @param file the File to sync from
     */
    private void syncFileWithDatabase(String dbName, File file) {
    	Log.d(TAG, "Syncing file " + file.getName());
    	
    }
}