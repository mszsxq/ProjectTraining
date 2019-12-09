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
	public List<All_data> InsertActivityTime(int id){
		List<All_data> datas = new ArrayList<All_data>();
		List<String> name = new ArrayList<>();
		DataDao dataDao = new DataDao();
		int n = dataDao.countData(id);
		ActivityService activityService = new ActivityService();
		name = activityService.insertData(id);
		SimpleDateFormat format11= new SimpleDateFormat("yyyy-MM-dd");
		String data = format11.format(new Date());
		for(int i = 0;i<name.size();i++) {
			All_data all_data = new All_data();
			n = n+1;
			DetailService detailService = new DetailService();
			int j = i+1;
			String time = detailService.detailActivity(id,j);
			System.out.println(name.get(i));
			all_data.setActivity_data(time);
			all_data.setActivity_name(name.get(i));
			all_data.setData(data);
			all_data.setData_id(n);
			datas.add(all_data);
		}
		return datas;
	}

}
