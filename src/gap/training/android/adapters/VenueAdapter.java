package gap.training.android.adapters;

import gap.training.android.R;
import gap.training.android.common.DeviceLocationManager;
import gap.training.android.model.Venue;

import java.sql.NClob;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class VenueAdapter extends ArrayAdapter<Venue> {
	
	Context mContext;
	
	int layoutResourceId;
	
	List<Venue >data = null;
	
	SharedPreferences preferenceManager;
	
	DeviceLocationManager deviceLocationManager;

	public VenueAdapter(Context context, int layoutResourceId, List<Venue> data){
		super(context, layoutResourceId, data);
		this.mContext = context;
		this.layoutResourceId = layoutResourceId;
		this.data = data;
		preferenceManager = PreferenceManager.getDefaultSharedPreferences(context);
		// Set Device Location Manager
		deviceLocationManager = DeviceLocationManager.getInstance();
	}
	
	static class VenueItemViewHolder{
		public TextView textViewName;
		public TextView textViewDistance;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		VenueItemViewHolder viewHolder = new VenueItemViewHolder();
		
		if (convertView == null){
			// inflate layout
			convertView = LayoutInflater.from(mContext).inflate(layoutResourceId, parent, false);
			// Set up the ViewHolder
			viewHolder.textViewName = ((TextView)convertView.findViewById(R.id.text_view_name));			
			viewHolder.textViewDistance = ((TextView) convertView.findViewById(R.id.text_view_distance));
			// Store the holder with the view
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (VenueItemViewHolder) convertView.getTag();
		}
		
		Venue venue = data.get(position);
		
		TextView textViewName = viewHolder.textViewName;	
		textViewName.setText(venue.getName());	
		TextView textViewDistance = viewHolder.textViewDistance;
		String distanceText;		
		if (preferenceManager.getBoolean(mContext.getText(R.string.preference_enable_gps_key).toString(), true) 
			&& deviceLocationManager.getCurrentLocation() != null){
			Location venueLocation = new Location("FoursquareApi");
			venueLocation.setLatitude(venue.getLocation().getLat());
			venueLocation.setLongitude(venue.getLocation().getLng());			
			float distance = deviceLocationManager.getCurrentLocation().distanceTo(venueLocation) / 1000;
			distanceText = String.format("%.2f", distance) + " km";		
		}else{
			distanceText = "";
		}				
		textViewDistance.setText(distanceText);		
		return convertView;
	}
}
