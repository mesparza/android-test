package gap.training.android.model;

import java.util.Collection;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class VenuesSearch {
	
	public static final String SEARCH_TEXT_FIELD = "searchText";
	
	@DatabaseField(generatedId=true)
    private int id;	

	@DatabaseField
	private String searchText;
	
	@ForeignCollectionField
	private Collection<Venue> venues;

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public Collection<Venue> getVenues() {
		return venues;
	}

	public void setVenues(Collection<Venue> venues) {
		this.venues = venues;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
