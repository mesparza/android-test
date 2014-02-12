package gap.training.android.fragments;

import gap.training.android.AppConstants;
import gap.training.android.R;
import gap.training.android.model.Venue;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class VenueDialogFragment extends DialogFragment{
	
	/*
	 * Create a new instance of VenueDialogFragment
	 */
	
	static VenueDialogFragment newInstance(Venue venue){
		VenueDialogFragment fragment = new VenueDialogFragment();
		Bundle args = new Bundle();
		args.putString(AppConstants.VENUE_NAME_KEY, venue.getName());
		if (venue.getContact() != null){
			args.putString(AppConstants.VENUE_CONTACT_PHONE_KEY, venue.getContact().getPhone());	
		}		
		args.putString(AppConstants.VENUE_ADDRESS_KEY, venue.getLocation().getAddress());
		args.putString(AppConstants.VENUE_CITY_KEY, venue.getLocation().getCity());
		args.putString(AppConstants.VENUE_STATE_KEY, venue.getLocation().getState());
		fragment.setArguments(args);
		return fragment;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.dialog_fragment, container, false);
		getDialog().setTitle("Venue Details");
		Bundle bundle = getArguments();
		// Set Venue Name 
		TextView tvName = (TextView) view.findViewById(R.id.name_value);
		tvName.setText(bundle.getString(AppConstants.VENUE_NAME_KEY));
		// Set Contact Phone Number		
		TextView tvContactPHone = (TextView) view.findViewById(R.id.contact_phone_number_value);
		tvContactPHone.setText(bundle.getString(AppConstants.VENUE_CONTACT_PHONE_KEY));
		// Set Address 		
		TextView tvAddress = (TextView) view.findViewById(R.id.address_value);
		tvAddress.setText(bundle.getString(AppConstants.VENUE_ADDRESS_KEY));
		// Set City		
		TextView tvCity = (TextView) view.findViewById(R.id.city_value);
		tvCity.setText(bundle.getString(AppConstants.VENUE_CITY_KEY));
		// Set State		
		TextView tvState = (TextView) view.findViewById(R.id.state_value);
		tvState.setText(bundle.getString(AppConstants.VENUE_STATE_KEY));
		
		// Add Close Button Listener
		Button button = (Button) view.findViewById(R.id.close_button);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();				
			}
		});
		return view;
	}

}
