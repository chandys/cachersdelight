package net.compoundeye.cachersdelight;

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
        
        Log.d(TAG, "Hu-Ho!");
    }
}