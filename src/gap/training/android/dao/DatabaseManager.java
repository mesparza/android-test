package gap.training.android.dao;

import gap.training.android.model.Venue;
import gap.training.android.model.VenuesSearch;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

/*
 * Class to manage data base persistence
 */
public class DatabaseManager {
	
	// singleton instance
	static private DatabaseManager instance;	

	/*
	 * Init Database Manager instance
	 */
    static public void init(Context ctx) {
        if (instance == null) {
            instance = new DatabaseManager(ctx);
        }
    }
    
    /*
     * Get Singleton instance of DatabaseManager
     */
    static public DatabaseManager getInstance() {
        return instance;
    }
    
    private DatabaseHelper helper;
    
    private DatabaseManager(Context ctx) {
        helper = new DatabaseHelper(ctx);
    }  
    
    /*
     * Generic method to insert record to DB
     */
	public void addObject(Object object) {
		try {
			if (object instanceof Venue){ 
				helper.getVenueDao().create((Venue)object);
			}
			if (object instanceof VenuesSearch){ 
				helper.getVenuesSearchDao().create((VenuesSearch)object);
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Get venuesSearch record by search text
	 */
	public VenuesSearch getVenuesSearchBySearchText(String searchText){
		try {
			Dao<VenuesSearch, Integer> venuesSearchDao = helper.getVenuesSearchDao();
			// get our query builder from the DAO
			QueryBuilder<VenuesSearch, Integer> queryBuilder = venuesSearchDao.queryBuilder();			
			queryBuilder.where().eq(VenuesSearch.SEARCH_TEXT_FIELD, searchText);		
			// prepare our sql statement
			PreparedQuery<VenuesSearch> preparedQuery = queryBuilder.prepare();			
			return (VenuesSearch) venuesSearchDao.queryForFirst(preparedQuery);		
		} catch (SQLException e) {			
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * Get venuesSearch record by search text
	 */
	public List<Venue> getVenuesByVenuesSeach(int venuesSearchId){
		try {
			Dao<Venue, Integer> venueDao = helper.getVenueDao();
			// get our query builder from the DAO
			QueryBuilder<Venue, Integer> queryBuilder = venueDao.queryBuilder();			
			queryBuilder.where().eq(Venue.VENUES_SEARCH_ID, venuesSearchId);		
			// prepare our sql statement
			PreparedQuery<Venue> preparedQuery = queryBuilder.prepare();			
			return venueDao.query(preparedQuery);		
		} catch (SQLException e) {			
			e.printStackTrace();
			return null;
		}
	} 	


    
}
