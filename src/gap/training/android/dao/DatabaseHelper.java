package gap.training.android.dao;

import gap.training.android.model.Location;
import gap.training.android.model.Venue;
import gap.training.android.model.VenuesSearch;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper{
	
	private static final String DATABASE_NAME = "AndroidTestDB.db";
	private static final int DATABASE_VERSION = 1;
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}	
	
	// the DAO object we use to access the SimpleData table
	private Dao<Venue, Integer> venueDao = null;
	// the DAO object we use to access the VenuesSearch table
	private Dao<VenuesSearch, Integer> venuesSearchDao = null;

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {		
		try {			
			TableUtils.createTable(connectionSource, Venue.class);
			TableUtils.createTable(connectionSource, Location.class);
			TableUtils.createTable(connectionSource, Venue.Contact.class);
			TableUtils.createTable(connectionSource, VenuesSearch.class);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {				
			
	}
	
	/*
	 * Gets Venue Dao
	 */
	public Dao<Venue, Integer> getVenueDao() {
		if (venueDao == null){
			try {
				venueDao = getDao(Venue.class);
			}catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return venueDao;
	}
	
	/*
	 * Gets Venues Search Dao
	 */
	public Dao<VenuesSearch, Integer> getVenuesSearchDao() {
		if (venuesSearchDao == null){
			try {
				venuesSearchDao = getDao(VenuesSearch.class);
			}catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return venuesSearchDao;
	}	

}

