/**
 * 
 */
package activity.service;

import java.sql.SQLException;

import activity.dao.ActivityDao;

/**
 * @author ZSX
 *
 */
public class ActivityService {

	public int insertTo(int userId, String activityName, int iconId) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		ActivityDao ad = new ActivityDao();
		int i=ad.insertData(userId, activityName, iconId);
		return i;
	}

}
