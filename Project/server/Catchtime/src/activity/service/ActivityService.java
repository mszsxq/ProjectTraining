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
	public List<String> insertData(){
		List<String> datas = new ArrayList<>();
		ActivityDao activityDao = new ActivityDao();
		int n = activityDao.countActivity(1);
		for(int i =1;i<=n;i++) {
			int j = i-1;
			String name = activityDao.findActivity(i);
			datas.add(name);
		}
		return datas;
	}

}
