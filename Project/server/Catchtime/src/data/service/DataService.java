/**
 * 
 */
package data.service;

import java.util.ArrayList;
import java.util.List;

import data.dao.DataDao;
import entity.All_data;

/**
 * @author ZSX
 *
 */
public class DataService {
	public List<All_data>  activityRecently (String table_name,String activity_name){
		List<All_data>  list =new ArrayList<>();
		DataDao  dao = new DataDao();
		list = dao.activityRecently(table_name, activity_name);
		return list;
	}
	public List<All_data>  activityMonthly (String table_name,String activity_name){
		List<All_data>  list =new ArrayList<>();
		DataDao  dao = new DataDao();
		list = dao.activityMonthly(table_name, activity_name);
		return list;
	}

}
