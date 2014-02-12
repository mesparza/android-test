package gap.training.android.fragments;

import gap.training.android.AppConstants;
import gap.training.android.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SearchFragment extends Fragment {
	
	private final static String TAG = SearchFragment.class.getCanonicalName();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.search_fragment, container, false);
		// add button listener
		Button button = (Button) view.findViewById(R.id.search_button);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Get text address
				EditText editText = (EditText) getView().findViewById(R.id.search_text);
				String searchText = editText.getText().toString();
						
				Log.i(TAG, "Searching from Main Activity");		
				// Open search results fragment
				ResultsFragment resutsFragment = new ResultsFragment();
				// Set Fragment arguments
				Bundle args = new Bundle();
				args.putString(AppConstants.SEARCH_TEXT_KEY, searchText);
				resutsFragment.setArguments(args);
				FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();		
				// Replace fragment
				transaction.replace(R.id.fragment_container, resutsFragment);
				transaction.addToBackStack(null);		
				// Commit the transaction
				transaction.commit();
				
			}
		});
		return view;
	}
}
