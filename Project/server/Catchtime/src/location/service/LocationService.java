/**
 * 
 */
package location.service;

import java.sql.SQLException;

import location.dao.LocationDao;

/**
 * @author ZSX
 *
 */
public class LocationService {

	public int insertTo(int userId,String locationName,String detailName,double lat,double lng) {
		LocationDao ld=new LocationDao();
		try {
			return ld.insertData(userId, locationName, lat, lng, 50, detailName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0; 
	}

}
