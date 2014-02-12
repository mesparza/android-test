package gap.training.android.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/*
 * Class to represent Foursquare location object
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@DatabaseTable
public class Location {
	
	@DatabaseField(generatedId=true)
    private int id;
	
	@DatabaseField
	private double lat;
	
	@DatabaseField
	private double lng;
	
	@DatabaseField
	private String address;
	
	@DatabaseField
	private String city;
	
	@DatabaseField
	private String state;
	
	public Location(){
		// default construct
	}		

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}
	
	

}
