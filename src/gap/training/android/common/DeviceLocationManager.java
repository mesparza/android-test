package gap.training.android.common;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class DeviceLocationManager {
	
	static private DeviceLocationManager instance;
	
	/*
	 * Init Database Manager instance
	 */
    static public void init(Context ctx) {
        if (instance == null) {
            instance = new DeviceLocationManager(ctx);
        }
    }
	
	/*
	 * Get Singleton instance of Device Location Manager
	 */
	static public DeviceLocationManager getInstance(){	
		return instance;
	}
	
	private Location currentLocation;

	/*
	 * Initialize 
	 */
	private DeviceLocationManager(Context context){
		// Get current device location
		LocationManager locationManager = (LocationManager) 
				context.getSystemService(Context.LOCATION_SERVICE);
		DeviceLocationListener locationListener = new DeviceLocationListener();
		
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				500, 0, locationListener);			
	}
	
	/*
	 * Location Listener to get GPS device location
	 */
	private class DeviceLocationListener implements LocationListener{

		@Override
		public void onLocationChanged(Location location) {			
			currentLocation = location; 				
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}		
	}
	
	public Location getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}	

}
