package gap.training.android.api;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import gap.training.android.model.Venue;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FoursquareResponse {
	
	private Venue[] venues;

	public Venue[] getVenues() {
		return venues;
	}

	public void setVenues(Venue[] venues) {
		this.venues = venues;
	}

}
