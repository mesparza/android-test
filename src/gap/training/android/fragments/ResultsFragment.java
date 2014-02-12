package gap.training.android.fragments;

import gap.training.android.AppConstants;
import gap.training.android.R;
import gap.training.android.adapters.VenueAdapter;
import gap.training.android.api.FoursquareApi;
import gap.training.android.api.FoursquareResponse;
import gap.training.android.common.DeviceLocationManager;
import gap.training.android.dao.DatabaseManager;
import gap.training.android.model.Venue;
import gap.training.android.model.VenuesSearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public class ResultsFragment extends ListFragment{
	
	private final static String TAG = ResultsFragment.class.getCanonicalName();	
	
	// Location Manager for GPS location
	DeviceLocationManager deviceLocationManager;
	// Process Dialog
	ProgressDialog progressDialog;
	// Venues Array
	List<Venue> venues = new ArrayList<Venue>();
	// Venue Custom Adapter
	VenueAdapter adapter;
	// Shared Preferences
	SharedPreferences preferences;
	// Flag to track if onCreate has run
	boolean onCreatedRunned = false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.result_fragment, container, false);				
		// Set onCreate flag
		onCreatedRunned = true;
		// Show the progress bar
		progressDialog = new ProgressDialog(getActivity());
		progressDialog.setMessage("Loading");
		progressDialog.show();		
		// Initialize Preferences Manager
		preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		// read the search text
		Bundle args = getArguments();
		String searchText = args.getString(AppConstants.SEARCH_TEXT_KEY);		

		// Set Device Data Base Manager
		DatabaseManager dataBaseManager = DatabaseManager.getInstance();
		// Initialize Adapter
		adapter = new VenueAdapter(getActivity(), R.layout.list_view_venue_row, venues);
		setListAdapter(adapter);		
		
		VenuesSearch venuesSearch = dataBaseManager.getVenuesSearchBySearchText(searchText);
		if (venuesSearch != null){
			// search already exist in DB
			List<Venue> records = dataBaseManager.getVenuesByVenuesSeach(venuesSearch.getId());
			venues.addAll(records);	
			adapter.notifyDataSetChanged();
			progressDialog.dismiss();
		}else{
			// new search							
			getVenuesFromApi(searchText, view);
		}		
		Log.i(TAG, "on create ...");
		return view;
	}		
	
	@Override
	public void onResume() {		
		if (!onCreatedRunned){
			adapter.notifyDataSetChanged();
		}else{
			onCreatedRunned = false;
		}
		Log.i(TAG, "on resume ...");		
		super.onResume();
	}
	
	/*
	 * Get Venues information from Foursquare API and display them in list view
	 * @param {place} String place to search
	 */
	private void getVenuesFromApi(final String place, final View view){	
		FoursquareApi api = new FoursquareApi();
		// build Response Listener
		Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				// TODO Auto-generated method stub											
				progressDialog.dismiss();				
				List<Venue> records = parseApiInfo(response);								
				venues.addAll(records);	
				adapter.notifyDataSetChanged();				
				// Store search in DB
				saveVenuesSearch(place, records);
			}
		};
		// build Error Listener
		Response.ErrorListener errorListener = new Response.ErrorListener() {			 			
			@Override			
			public void onErrorResponse(VolleyError error) {
				progressDialog.dismiss();					
				TextView textView = (TextView) getListView().getEmptyView();				
				textView.setText(R.string.empty_result);		
			}
		};	
		api.getVenues(getActivity(), place, responseListener, errorListener);								
	}	
	
	/*
	 * Parse Foursquare API json response into Java Object
	 * @param {response} JSONObject of the api response
	 */
	private List<Venue> parseApiInfo(JSONObject response){		
		try {
			// Get venues info from api response
			String jsonResponse = response.getJSONObject("response").toString();
			// Parse Venues json with Jackson library
			ObjectMapper mapper = new ObjectMapper();
			FoursquareResponse  foursquareResponse = mapper.readValue(jsonResponse, FoursquareResponse.class);
			// Calculate Distance
			return Arrays.asList(foursquareResponse.getVenues());						
		} catch (Exception e) {			
			e.printStackTrace();
			return null;
		}				
	}	
	
	/*
	 * Persist Venue Search in Data Base
	 */
	private void saveVenuesSearch(String searchText, List<Venue> venues){
		DatabaseManager dataBaseManager = DatabaseManager.getInstance();
		VenuesSearch venuesSearch = new VenuesSearch();
		venuesSearch.setSearchText(searchText);
		dataBaseManager.addObject(venuesSearch);
		for (int i = 0; i < venues.size(); i++){
			venues.get(i).setVenuesSearch(venuesSearch);
			dataBaseManager.addObject(venues.get(i));		
		}
			
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {		
		Venue venue = (Venue) getListAdapter().getItem(position);				
		// Open Venue Details Dialog
		FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
		transaction.addToBackStack(null);					
		VenueDialogFragment dialogFragment =  VenueDialogFragment.newInstance(venue);
		dialogFragment.show(transaction, "dialog");				
	}	
}
