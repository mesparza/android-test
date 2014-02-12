package gap.training.android;

import gap.training.android.common.DeviceLocationManager;
import gap.training.android.dao.DatabaseManager;
import gap.training.android.fragments.SearchFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {
		
	private static final int RESULT_SETTINGS = 1;
	private String[] mMenuOptions;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;    
    private ActionBarDrawerToggle mDrawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_container);
		//setContentView(R.layout.activity_main);		
		
		// Initialize Navigation Drawer 
		mMenuOptions = getResources().getStringArray(R.array.drawer_options);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);		
		// Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_listview_item, mMenuOptions));        
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ); 
        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);               
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);
		
		// Initialize DB
		DatabaseManager.init(this);	
		// Initialize Manage Location
		DeviceLocationManager.init(this);
		
        // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if (findViewById(R.id.fragment_container) != null) {
        	
        	// Check we're being restored from a previous state, 
        	// to avoid end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
		
			// Open search results fragment
			SearchFragment searchFragment = new SearchFragment();
			// Set Fragment arguments
			Bundle args = new Bundle();
			searchFragment.setArguments(args);
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();		
			// Replace fragment
			transaction.replace(R.id.fragment_container, searchFragment);		
			// Commit the transaction
			transaction.commit();
        }
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
	    super.onPostCreate(savedInstanceState);
	    // Sync the toggle state after onRestoreInstanceState has occurred.
	    mDrawerToggle.syncState();
	}	
	
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 if (mDrawerToggle.onOptionsItemSelected(item)) {
	            return true;
	        }
		switch (item.getItemId()){
			case R.id.action_settings:
				Intent intent = new Intent(this, PreferencesActivity.class);
				startActivityForResult(intent, RESULT_SETTINGS);
				break;
		}
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {		
		switch (requestCode){
			case RESULT_SETTINGS:
				 applyUserSettings();
				 break;
		}
	}
	
	/*
	 * Dummy Function to display user preferences
	 */
	private void applyUserSettings(){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		Log.i("marcio main activity", String.valueOf(preferences.getBoolean("enableGps", true)));
	}
	

}
