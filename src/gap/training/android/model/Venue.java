package gap.training.android.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/*
 * Class to represent Foursquare venue object
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@DatabaseTable
public class Venue {
	
	public static final String VENUES_SEARCH_ID = "venues_search_id";
	
	@DatabaseField(generatedId=true)
    private int id;

	@DatabaseField
	private String name;
	
	@DatabaseField(foreign=true, foreignAutoRefresh=true, foreignAutoCreate=true, columnName="location_id")
	private Location location;
	
	@DatabaseField(foreign=true,foreignAutoRefresh=true, foreignAutoCreate=true, columnName="contact_id", canBeNull=true)
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	private Contact contact;
	
	@JsonIgnoreProperties(ignoreUnknown = true)	
	@DatabaseTable
	public static class Contact{
		
		@DatabaseField(generatedId=true)
	    private int id;
		
		@DatabaseField
		private String phone;

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}				
	}
	
	// Foreign Collection for SearhVenue Object, ORM needs it
	@DatabaseField(foreign=true, columnName=VENUES_SEARCH_ID)
	private VenuesSearch venuesSearch;

	public Venue(){
		// default construct
	}		

	
	public Venue(String name){
		this.name = name;		
	}		

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Contact getContact() {		
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
	public VenuesSearch getVenuesSearch() {
		return venuesSearch;
	}

	public void setVenuesSearch(VenuesSearch venuesSearch) {
		this.venuesSearch = venuesSearch;
	}
	
}
