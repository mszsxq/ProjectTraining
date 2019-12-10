/**
 * 
 */
package location.service;

import java.sql.SQLException;

import entity.Location;
import location.dao.LocationDao;

/**
 * @author ZSX
 *
 */
public class LocationService {
	public int InsertDefaultLocation(int id,Location loc) {
		int n = 0;
		LocationDao locationDao = new LocationDao();
		try {
			n = locationDao.insertData(id, loc.getLocationName(), loc.getLocationLat(), loc.getLocationLng(), loc.getLocationRange(), loc.getLocationDetail());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return n;
	}
}
