package gap.training.android.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONObject;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class FoursquareApi {	
	
	public void getVenues(final Context context, String place, 
			Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener){
		try {			
			RequestQueue queue = Volley.newRequestQueue(context);
			String query;
			query = URLEncoder.encode(place, "UTF-8");								 
			String url = "https://api.foursquare.com/v2/venues/search?near=" +
					query + "&oauth_token=HDQVML4JWDJNHHKCSRPN0MPYUFEMJJEXH3C34FTCPYUOR5OP&v=20130417";			
			JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, 
					responseListener, errorListener);
				
			queue.add(jsObjRequest);				
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
