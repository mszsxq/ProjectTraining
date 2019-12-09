/**
 * 
 */
package activity.dao;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import entity.Activity;
import entity.activity_location;
import entity.chartData;
import util.DBManager;

/**
 * @author ZSX
 *
 */
public class ActivityDao {
	public void createTable(int id) throws SQLException {
//		String add = "alter table ol_activity add constraint lll foreign key(icon_id) REFERENCES icon(icon_id);";
		Connection conn= null;
		String table = id+"_activity";
		PreparedStatement pst = null;
		String sql = "CREATE TABLE "+table+" (activity_id int not null, "
				+ "activity_name varchar(50), icon_id smallint,"
				+ "primary key(activity_id),foreign key(icon_id) references icon(icon_id));";
		try {
			 conn= DBManager.getInstance().getConnection();
			 pst = conn.prepareStatement(sql);
			 int i=pst.executeUpdate();
//			 pst1 = conn.prepareStatement(add);
//			 pst1.execute();
			 if(i>0) {
				 System.out.println("鍒涘缓鎴愬姛");
			 }
			 
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			conn.close();
			pst.close();
//			pst1.close();
		}
	}
	public int insertData(int userId,String activity_name,int icon_id) throws ClassNotFoundException, SQLException {
		Connection conn= null;
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		ResultSet rs=null;
		int location_id=0;
		String table=userId+"_activity";
		String allNum="select * from "+table;
		String s = "insert into "+table+" values(?,?,?);";
		try {
			conn = DBManager.getInstance().getConnection();
			pst1 = conn.prepareStatement(allNum);
			rs = pst1.executeQuery();
			while(rs.next()) {
				location_id = rs.getInt(1);
			}
			pst = conn.prepareStatement(s);
			pst.setInt(1,++location_id);
			pst.setString(2, activity_name);
			pst.setInt(3, icon_id);
			int i = pst.executeUpdate();
			if(i>0) {
				return location_id;
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			conn.close();
			pst1.close();
		}
		return 0;
	}
	public void delete(int userId,int i) throws SQLException {
		Connection conn= null;
		PreparedStatement pst1 = null;
		String table=userId+"_activity";
		String sql1 = "delete from "+table+" where icon_id = ?;";
		try {
			conn = DBManager.getInstance().getConnection();
			pst1 = conn.prepareStatement(sql1);
			pst1.setInt(1, i);
			int m =pst1.executeUpdate();	
			if(m>0) {
				System.out.println("鍒犻櫎鎴愬姛");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				DBManager.getInstance().closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public void updateName(int userId,int id,int icon_id) throws SQLException{
		Connection conn= null;
		PreparedStatement pst = null;
		String table=userId+"_activity";
		String sql ="update "+table+" set icon_id=? where activity_id = ?;";
		try {
			conn = DBManager.getInstance().getConnection();
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			pst.setInt(2, icon_id);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	finally {
			try {
				DBManager.getInstance().closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public List<Activity> findAll(int userId) {
		Connection conn= null;
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		ResultSet rs=null;
		ResultSet rs1=null;
		String table=userId+"_activity";
		List<Activity> list=new ArrayList<Activity>();
		try {
			conn = DBManager.getInstance().getConnection();
			pst = conn.prepareStatement("select * from "+table);
			pst1 = conn.prepareStatement("select * from icon where icon_id = ?;");
			rs = pst.executeQuery();
			while(rs.next()) {
				Activity activity = new Activity();
				activity.setActivity_name(rs.getString(2));
				activity.setActivity_id(rs.getInt(1));
				int icon_id = rs.getInt(3);
				pst1.setInt(1,icon_id);
				rs1 = pst1.executeQuery();
				while(rs1.next()) {
					activity.setIcon_id(rs1.getInt(1));
					activity.setIcon_name(rs1.getString(2));
					activity.setIcon_color(rs1.getString(3));
				}
				list.add(activity);		
			}
			
			System.out.println(list.get(0).toString());
			
			} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				DBManager.getInstance().closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	public Activity findSingle(int userId,int id) {
		Connection conn= null;
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		ResultSet rs=null;
		ResultSet rs1=null;
		String table=userId+"_activity";
		Activity activity = null;
		try {
			conn = DBManager.getInstance().getConnection();
			pst = conn.prepareStatement("select * from "+table+" where activity_id=?;");
			pst.setInt(1, id);
			rs = pst.executeQuery();
			activity = new Activity();
			activity.setActivity_id(id);
			while(rs.next()) {
				activity.setActivity_name(rs.getString(2));
				activity.setIcon_id(rs.getInt(3));
			}
			pst1 = conn.prepareStatement("select * from icon where icon_id = ?;");
			pst1.setInt(1,activity.getIcon_id());
			rs1 = pst1.executeQuery();
			while(rs1.next()) {
				activity.setIcon_name(rs1.getString(2));
				activity.setIcon_color(rs1.getString(3));
			}
			System.out.println(activity.toString());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				DBManager.getInstance().closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return activity;
	}
<<<<<<< HEAD
	public Activity findSingleforname(int userId,String name) {
=======
	
	public int findLocationId(int userId,String name) {
		int id=0;
>>>>>>> 303e91a9e2955412334c345d4e453b12cd85385a
		Connection conn= null;
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		ResultSet rs=null;
		ResultSet rs1=null;
<<<<<<< HEAD
		String table=userId+"_activity";
		Activity activity = null;
		try {
			conn = DBManager.getInstance().getConnection();
			pst = conn.prepareStatement("select * from "+table+" where activity_name=?;");
			pst.setString(1, name);
			rs = pst.executeQuery();
			activity = new Activity();
			activity.setActivity_name(name);
			while(rs.next()) {
				activity.setActivity_id(rs.getInt(1));
				activity.setIcon_id(rs.getInt(3));
			}
			pst1 = conn.prepareStatement("select * from icon where icon_id = ?;");
			pst1.setInt(1,activity.getIcon_id());
			rs1 = pst1.executeQuery();
			while(rs1.next()) {
				activity.setIcon_name(rs1.getString(2));
				activity.setIcon_color(rs1.getString(3));
			}
			return activity;
		} catch (ClassNotFoundException | SQLException e) {
=======
		String table=userId+"_location";
		Activity activity = null;
		try {
			conn = DBManager.getInstance().getConnection();
			pst = conn.prepareStatement("select * from "+table+" where location_name = ?;");
			pst.setString(1, name);
			rs = pst.executeQuery();
			while(rs.next()) {
				id=rs.getInt("location_id");
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				DBManager.getInstance().closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return id;
	}
	public HashMap<Integer,Long> findDetailData(int user_id,String date) throws ParseException{
		Connection conn= null;
		PreparedStatement pst1 = null;
		PreparedStatement pst2=null;
		PreparedStatement pst3=null;
		ResultSet rs1 = null;
		ResultSet rs2=null;
		ResultSet rs3=null;
		String table1=user_id+"_detaildata";
		String table2 =user_id+"_activity";
		String sql1 = "select * from "+table1+" where begin_time like ?";
		String sql2 = "select activity_name from "+table2+" where activity_id = ?"; 
		String sql3 = "select * from "+table1+" where activity_id = 404";
		HashMap<Integer,Long> hashList = new  HashMap<Integer,Long>();
		String lastEndTime = date+" 00:00:00";
		String lastTime=date+" 23:59:59";
		long allTime=0;
		String name=null;
		try {
			try {
				conn = DBManager.getInstance().getConnection();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				pst1 = conn.prepareStatement(sql1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				pst3 = conn.prepareStatement(sql3);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				pst1.setString(1,date+"%");
				rs1=pst1.executeQuery();
				String sdf = "yyyy-MM-dd HH:mm:ss";
				while(rs1.next()) {
					int id = rs1.getInt("activity_id");
					String start = rs1.getString("begin_time");
					String end = rs1.getString("finish_time");
					//long wenhao = dateDiff(lastEndTime,start,sdf);
					//if(wenhao>0) {
						//int n=updateDetailData(user_id,lastEndTime,start);
					//}
					long ans = dateDiff(start,end,sdf);
					if(hashList.containsKey(id)) {
						allTime=hashList.get(id);
						allTime=allTime+ans;
						hashList.put(id, allTime);
					}
					hashList.put(id, ans);
					lastEndTime = end;
				}
				
				rs3=pst3.executeQuery();
				while(rs3.next()) {
					String sdf1 = "yyyy-MM-dd HH:mm:ss";
					String start = rs3.getString("begin_time");
					String end = rs3.getString("finish_time");
					long ans = dateDiff(start,end,sdf1);
					if(hashList.containsKey(404)) {
						allTime=hashList.get(404);
						allTime=allTime+ans;
						hashList.put(404, allTime);
					}
					hashList.put(404, ans);
				}
				//long wenhao = dateDiff(lastEndTime,lastTime,sdf);
				//if(wenhao>0) {
					//int n=updateDetailData(user_id,lastEndTime,lastTime);
				//}
				System.out.println(hashList.toString());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		
		}finally {
			try {
				DBManager.getInstance().closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return hashList;
		
		
	}
	public int updateDetailData(int user_id,String begin_time,String finish_time) {
		Connection conn= null;
		PreparedStatement pst1 = null;
		PreparedStatement pst2 = null;
		String table=user_id+"_detaildata";
		String sql1 = "select count(*) from "+table+"";
		String sql2 ="insert into "+table+" values(?,?,?,?,?)";
		int detail_id=0;
		int n = 0;
		try {
			conn = DBManager.getInstance().getConnection();
			pst1 = conn.prepareStatement(sql1);
			ResultSet rs1 = pst1.executeQuery();
			while(rs1.next()) {
				detail_id = rs1.getInt(1)+1;
			}
			pst2 = conn.prepareStatement(sql2);
			pst2.setInt(1, detail_id);
			pst2.setInt(2, 404);
			pst2.setInt(3, 404);
			pst2.setString(4,begin_time);
			pst2.setString(5, finish_time);
			n = pst2.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	finally {
			try {
				DBManager.getInstance().closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(n>0) {
			return 1;
		}
		return 0;
	}
	public int findActivityId(int user_id,String activity_name){
		Connection conn= null;
		PreparedStatement pst2=null;
		ResultSet rs2=null;
		String table2 =user_id+"_activity";
		String sql2 = "select activity_id from "+table2+" where activity_name = ?;"; 
		int id=0;
		try {
			conn = DBManager.getInstance().getConnection();
			pst2 = conn.prepareStatement(sql2);
			pst2.setString(1, activity_name);
			rs2=pst2.executeQuery();
			while(rs2.next()) {
				id = rs2.getInt(1);
				System.out.println("name"+id);
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				DBManager.getInstance().closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return id;
		
	}
	
	public String findActivityName(int user_id,int activity_id){
		Connection conn= null;
		PreparedStatement pst2=null;
		ResultSet rs2=null;
		String table2 =user_id+"_activity";
		String sql2 = "select activity_name from "+table2+" where activity_id = ?;"; 
		String name=null;
		try {
			conn = DBManager.getInstance().getConnection();
			pst2 = conn.prepareStatement(sql2);
			pst2.setInt(1, activity_id);
			rs2=pst2.executeQuery();
			while(rs2.next()) {
				name= rs2.getString(1);
				System.out.println("name"+name);
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				DBManager.getInstance().closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return name;
		
	}
	
	public HashMap<String,String> findDayRecord(int user_id,String date){
		Connection conn= null;
		PreparedStatement pst1 = null;
		ResultSet rs1 = null;
		String table1=user_id+"_dayrecord";
		String sql1 = "select * from "+table1+" where data = ?";
		HashMap<String,String> hashList = new  HashMap<String,String>();
		String allTime=null;
		String name = null;
		try {
			conn = DBManager.getInstance().getConnection();
			pst1 = conn.prepareStatement(sql1);
			pst1.setString(1,date);
			rs1=pst1.executeQuery();
			while(rs1.next()) {
				allTime = rs1.getString("activity_data");
				name = rs1.getString("activity_name");
				if(hashList.containsKey(name)) {
					String ans = hashList.get(name);
					allTime =(Integer.parseInt(allTime)+Integer.parseInt(ans))+"";
					hashList.put(name,allTime);
				}else {
					hashList.put(name,allTime);
				}
				
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				DBManager.getInstance().closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return hashList;
		
	}
	
	public HashMap<String,String> findWeekRecord(int user_id,String date){
		List<String> days = new ArrayList<String>();
		Connection conn= null;
		PreparedStatement pst1 = null;
		ResultSet rs1 = null;
		String table1=user_id+"_dayrecord";
		String sql1 = "select * from "+table1+" where data = ?";
		HashMap<String,String> hashList = new  HashMap<String,String>();
		int allTime=0;
		String name = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date weekDays;
		try {
			weekDays = (Date) sdf.parse(date);
	        for(int i=0;i<7;i++) {
	        	Calendar calendar = new GregorianCalendar();
		        calendar.setTime(weekDays);
	        	calendar.add(calendar.DATE,-i);
		        days.add(sdf.format(calendar.getTime()));
	        }
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conn = DBManager.getInstance().getConnection();
			pst1 = conn.prepareStatement(sql1);
			for(int k=0;k<7;k++) {
				pst1.setString(1,days.get(k));
				rs1=pst1.executeQuery();
				while(rs1.next()) {
					try {
						allTime = addData(rs1.getString("activity_data"));
						name = rs1.getString("activity_name");
						if(hashList.containsKey(name)) {
							int ans = addData(hashList.get(name));
							allTime = allTime+ans;
							hashList.put(name,allTime+"");
						}else {
							hashList.put(name,""+allTime);
						}
						
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				DBManager.getInstance().closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return hashList;
		
	}
	
	public HashMap<String,String> findMonthRecord(int user_id,String date){
		List<String> days = new ArrayList<String>();
		Connection conn= null;
		PreparedStatement pst1 = null;
		ResultSet rs1 = null;
		String table1=user_id+"_dayrecord";
		String sql1 = "select * from "+table1+" where data = ?";
		HashMap<String,String> hashList = new  HashMap<String,String>();
		int allTime=0;
		String name = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date weekDays;
		try {
			weekDays = (Date) sdf.parse(date);
			for(int i=0;i<30;i++) {
	        	Calendar calendar = new GregorianCalendar();
		        calendar.setTime(weekDays);
	        	calendar.add(calendar.DATE,-i);
		        days.add(sdf.format(calendar.getTime()));
	        }
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conn = DBManager.getInstance().getConnection();
			pst1 = conn.prepareStatement(sql1);
			for(int k=0;k<30;k++) {
				pst1.setString(1,days.get(k));
				rs1=pst1.executeQuery();
				while(rs1.next()) {
					try {
						allTime = addData(rs1.getString("activity_data"));
						name = rs1.getString("activity_name");
						if(hashList.containsKey(name)) {
							int ans = addData(hashList.get(name));
							allTime = allTime+ans;
							hashList.put(name,allTime+"");
						}else {
							hashList.put(name,""+allTime);
						}
						
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				DBManager.getInstance().closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return hashList;
		
	}
	public HashMap<String,String> findYearRecord(int user_id,String date){
		List<String> days = new ArrayList<String>();
		Connection conn= null;
		PreparedStatement pst1 = null;
		ResultSet rs1 = null;
		String table1=user_id+"_dayrecord";
		String sql1 = "select * from "+table1+" where data = ?";
		HashMap<String,String> hashList = new  HashMap<String,String>();
		int allTime=0;
		String name = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date weekDays;
		try {
			weekDays = (Date) sdf.parse(date);
			for(int i=0;i<365;i++) {
	        	Calendar calendar = new GregorianCalendar();
		        calendar.setTime(weekDays);
	        	calendar.add(calendar.DATE,-i);
		        days.add(sdf.format(calendar.getTime()));
	        }
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conn = DBManager.getInstance().getConnection();
			pst1 = conn.prepareStatement(sql1);
			for(int k=0;k<365;k++) {
				pst1.setString(1,days.get(k));
				rs1=pst1.executeQuery();
				while(rs1.next()) {
					try {
						allTime = addData(rs1.getString("activity_data"));
						name = rs1.getString("activity_name");
						if(hashList.containsKey(name)) {
							int ans = addData(hashList.get(name));
							allTime = allTime+ans;
							hashList.put(name,allTime+"");
						}else {
							hashList.put(name,""+allTime);
						}
						
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				DBManager.getInstance().closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return hashList;
		
	}
	
	public long dateDiff(String startTime, String endTime, String format) throws ParseException {
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
            // 获得两个时间的毫秒时间差异
        diff = sd.parse(endTime).getTime()
                    - sd.parse(startTime).getTime();
        day = diff / nd;// 计算差多少天
        long hour = diff % nd / nh;// 计算差多少小时
        long min = diff/ nm;// 计算差多少分钟
        long sec = diff/ ns;// 计算差多少秒
            // 输出结果
        System.out.println("时间相差：" + day + "天" + hour + "小时" + min
                    + "分钟" + sec + "秒。");
        String ans=null;
        return min;
        
        
    }
	public String fomat(long diff) {
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数

        long hour = diff / 60;// 计算差多少小时
        long min = diff % 60;// 计算差多少分钟
            // 输出结果
        System.out.println("时间相差：" + hour + "小时" + min
                    + "分钟" );
        String ans=null;
        if(hour>1) {
           	if(min==0) {
           		ans=(hour>=10?hour+"":"0"+hour)+"-00";
           		return ans;
           	}else {
           		ans=(hour>=10?hour+"":"0"+hour)+"-"+(min>=10?min+"":"0"+min);
           		return ans;
           	}
           	
           }else {
        	   ans="00-"+(min>=10?min+"":"0"+min);
           	return ans;
           }
   
	}
	
	public int addData(String time) throws ParseException {  
        long nm = 1000 * 60;// 一分钟的毫秒数
        long diff;
       int min = Integer.parseInt(time);
     
        return min;
        
    }
	
	public activity_location findActivityLocation(int user_id,String activity_name,String date) {
		System.out.println("测试");
		Connection conn= null;
		PreparedStatement pst1 = null;
		PreparedStatement pst2 = null;
		PreparedStatement pst3 = null;
		PreparedStatement pst4 = null;
		PreparedStatement pst5 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		ResultSet rs5 = null;
		String table1=user_id+"_activity";
		String table3=user_id+"_location";
		String table4=user_id+"_connection";
		String table5=user_id+"_detaildata";
		String sql1 = "select * from "+table1+" where activity_name = ?;";
		String sql2 = "select * from icon where icon_id = ?;";
		String sql3 = "select * from "+table3+" where location_id = ?;";
		String sql4 = "select * from "+table4+" where activity_id = ?;";
		String sql5 = "select * from "+table5+" where activity_id =? and location_id = ? and begin_time like ?";
		int activity_id=0;
		int location_id=0;
		activity_location al = new activity_location();
		try {
			al.setActivity_name(activity_name);
			conn = DBManager.getInstance().getConnection();
			pst1 = conn.prepareStatement(sql1);
			pst1.setString(1,activity_name);
			rs1=pst1.executeQuery();
			while(rs1.next()) {
				activity_id=rs1.getInt("activity_id");
				int icon_id = rs1.getInt("icon_id");
				pst2 = conn.prepareStatement(sql2);
				pst2.setInt(1,icon_id);
				rs2=pst2.executeQuery();
				while(rs2.next()) {
					al.setColor(rs2.getString("color"));
					al.setIcon(rs2.getString("icon_address"));
				}
				pst4 = conn.prepareStatement(sql4);
				pst4.setInt(1,activity_id);
				rs4=pst4.executeQuery();
				while(rs4.next()) {
					location_id=rs4.getInt("location_id");
					
				}
				pst3 = conn.prepareStatement(sql3);
				pst3.setInt(1,location_id);
				rs3=pst3.executeQuery();
				while(rs3.next()) {
					al.setLat(rs3.getDouble("location_lat"));
					al.setLng(rs3.getDouble("location_lng"));
					al.setDetailAdd(rs3.getString("location_detailed"));
					al.setLocation_name(rs3.getString("location_name"));
				}
				pst5 = conn.prepareStatement(sql5);
				pst5.setInt(1,activity_id);
				pst5.setInt(2,location_id);
				pst5.setString(3,date+"%");
				rs5=pst5.executeQuery();
				System.out.println("activity"+activity_id+"location"+location_id+"date"+date);
				while(rs5.next()) {
					al.setStartTime(rs5.getString("begin_time"));
					al.setEndTime(rs5.getString("finish_time"));
					System.out.println("start"+rs5.getString("begin_time"));
				}
				System.out.println("al"+al.getStartTime()+"-"+date);
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				DBManager.getInstance().closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return al;
		
		
	}

	public int updateDta(int user_id,int activity_id,int location_id,int icon_id,int old_id,String date) {
		String table = user_id+"_detaildata";
		String sql = "update "+table+" set activity_id=? and location_id = ?  where activity_id=? and begin_time like ?";
		Connection conn = null;
		int n = 0;
		try {
			conn = DBManager.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,activity_id);
			ps.setInt(2, location_id);
			ps.setInt(3, old_id);
			ps.setString(4,date+"%");
			n = ps.executeUpdate();
			ps.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				DBManager.getInstance().closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return n;
		
	}
	public int updateDayDta(int user_id,String date,String activity_name,String old_name) {
		String table = user_id+"_dayrecord";
		String sql = "update "+table+" set activity_name = ?  where activity_name = ? and data like ?";
		Connection conn = null;
		int n = 0;
		try {
			conn = DBManager.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,activity_name);
			ps.setString(2,old_name);
			ps.setString(3,date+"%");
			n = ps.executeUpdate();
			ps.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
>>>>>>> 303e91a9e2955412334c345d4e453b12cd85385a
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				DBManager.getInstance().closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return activity;
		return n;
		
	}
}
