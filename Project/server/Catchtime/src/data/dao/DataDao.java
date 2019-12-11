/**
 * 
 */
package data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.All_data;
import util.DBManager;

/**
 * @author ZSX
 *
 */
public class DataDao {
	//鍒涘缓琛�
	public int createdatatable(int id) {
		int n=0;
		String table = id+"_data";
		Connection conn=null;
		try {
			conn=DBManager.getInstance().getConnection();
			String sql="create table "+table+" (data_id int,data date not null,activity_name varchar(50) not null,activity_data varchar(50),primary key(data_id,activity_name))";
			PreparedStatement p=conn.prepareStatement(sql);
			n=p.executeUpdate();	
			if(n==1) {
				System.out.print("鍒涘缓鎴愬姛");
			}else {
				System.out.print("鍒涘缓澶辫触");
			}
			return n;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return n;
	}
	
	
	//閹绘帒鍙嗛弫鐗堝祦
	public int addalldata(String tablename,int data_id,String data,String activity_name,String activity_data) {
		int n=0;
		Connection conn=null;
		try {
			conn=DBManager.getInstance().getConnection();
			String sql="insert into "+tablename+" values(?,curdate(),?,?)";
			PreparedStatement p=conn.prepareStatement(sql);
			p.setInt(1, data_id);
			p.setString(2,activity_name);
			p.setString(3, activity_data);
			n=p.executeUpdate();
			if(n==1) {
				System.out.print("閹绘帒鍙嗛幋鎰");
			}else {
				System.out.print("閹绘帒鍙嗘径杈Е");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return n;
	}
	
	//閸掔娀娅庨弫鐗堝祦
	public int deletealldata(String table_name,int data_id) {
		int n=0;
		Connection conn=null;
		try {
			conn=DBManager.getInstance().getConnection();
			String sql="delete from ? where data_id=?";
			PreparedStatement p=conn.prepareStatement(sql);
			p.setString(1, table_name);
			p.setInt(2, data_id);
			n=p.executeUpdate();
			if(n==1) {
				System.out.print("閸掔娀娅庨幋鎰");
			}else {
				System.out.print("閸掔娀娅庢径杈Е");
			}
			return n;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return n;
	}
	//閺屻儴顕楅弫鐗堝祦
	public All_data findalldataBydataId(String tablename,int data_id) {
		All_data all_data=null;
		Connection conn=null;
		ResultSet rs;
		try {
			conn=DBManager.getInstance().getConnection();
			String sql="select * from "+tablename+" where data_id=?";
			PreparedStatement p=conn.prepareStatement(sql);
			p.setInt(1, data_id);
			rs=p.executeQuery();
			while(rs.next()) {
				all_data=new All_data();
				all_data.setData_id(rs.getInt(1));
				all_data.setData(rs.getString(2));
				all_data.setActivity_name(rs.getString(3));
				all_data.setActivity_data(rs.getString(4));
			}
			return all_data;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return all_data;
		
	}
	public int findalldata(String tablename) {
		All_data all_data=null;
		List<All_data> data = new ArrayList<>();
		Connection conn=null;
		ResultSet rs;
		try {
			conn=DBManager.getInstance().getConnection();
			String sql="select * from "+tablename;
			PreparedStatement p=conn.prepareStatement(sql);
			rs=p.executeQuery();
			while(rs.next()) {
				all_data=new All_data();
				all_data.setData_id(rs.getInt(1));
				all_data.setData(rs.getString(2));
				all_data.setActivity_name(rs.getString(3));
				all_data.setActivity_data(rs.getString(4));
				data.add(all_data);
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data.size();
		
	}
	public int findataid(String tablename,String activity_name,String data) {
		int id=0;
		Connection conn=null;
		ResultSet rs;
		try {
			conn=DBManager.getInstance().getConnection();
			String sql="select data_id from "+tablename+" where activity_name=? and data=?";
			PreparedStatement p=conn.prepareStatement(sql);
			p.setString(1, activity_name);
			p.setString(2, data);
			rs=p.executeQuery();
			if(rs.next()) {
				id=rs.getInt(1);
				System.out.println("dataid="+id);
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
		
	}
	public int finacdata(String tablename,String activity_name,int id) {
		int acdata=0;
		Connection conn=null;
		ResultSet rs;
		try {
			conn=DBManager.getInstance().getConnection();
			String sql="select activity_data from "+tablename+" where activity_name=? and data_id=?";
			PreparedStatement p=conn.prepareStatement(sql);
			p.setString(1, activity_name);
			p.setInt(2, id);
			rs=p.executeQuery();
			if(rs.next()) {
				acdata=Integer.parseInt(rs.getString(1));
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return acdata;
		
	}
	public int upDateNum(String tablename,String activity_name,String data,String activity_data) {
		int id=0;
		Connection conn=null;
		ResultSet rs;
		try {
			conn=DBManager.getInstance().getConnection();
			String sql ="update "+tablename+" set activity_data=? where activity_name = ? and data=?";
			PreparedStatement p=conn.prepareStatement(sql);
			p.setString(1, activity_data);
			p.setString(2, activity_name);
			p.setString(3, data);
			p.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
		
	}
	//鏌ヨ杩戜竷澶╂煇椤规椿鍔ㄧ殑鏃堕棿
		public List<All_data> activityRecently(String table_name, String activity_name) {
			List<All_data> list= new ArrayList() ;
			Connection conn =null;
			ResultSet rs = null;
//			String sql ="select * from "+table_name+" where date between current_date()-7 and sysdate() and activity_name=?";
//			String sql ="select * from "+table_name;
			String sql ="select * from "+table_name+" where activity_name=? and  date_sub(curdate(), interval 7 day) <= date(date);";

			try {
				conn=DBManager.getInstance().getConnection();
				PreparedStatement ps=conn.prepareStatement(sql);
				ps.setString(1,activity_name);
				rs=ps.executeQuery();
				while(rs.next()) {
					All_data all_data = new All_data();
					all_data.setData_id(rs.getInt(1));
					all_data.setData(rs.getString(2));
					all_data.setActivity_name(rs.getString(3));
					all_data.setActivity_data(rs.getString(4));
					list.add(all_data);
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					rs.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return list;
		}
		//鏌ヨ杩戜竴涓湀鏌愰」娲诲姩鐨勬椂闂�
			public List<All_data> activityMonthly(String table_name, String activity_name) {
				List<All_data> list= new ArrayList() ;
				Connection conn =null;
				ResultSet rs = null;
//				String sql ="select * from "+table_name+" where date between current_date()-7 and sysdate() and activity_name=?";
//				String sql ="select * from "+table_name;
				String sql ="select * from "+ table_name+" where activity_name=? and date_sub(curdate(), interval 30 day) <= date(data) order by data;";

				try {
					conn=DBManager.getInstance().getConnection();
					PreparedStatement ps=conn.prepareStatement(sql);
					ps.setString(1,activity_name);
					rs=ps.executeQuery();
					while(rs.next()) {
						All_data all_data = new All_data();
						all_data.setData_id(rs.getInt(1));
						all_data.setData(rs.getString(2));
						all_data.setActivity_name(rs.getString(3));
						all_data.setActivity_data(rs.getString(4));
						list.add(all_data);
					}
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return list;
			}
	public int countData(int id) {
		String table = id+"_data";
		String sql = "select count(*) from "+table;
		Connection con = null;
		int n = 0;
		try {
			con = DBManager.getInstance().getConnection();
			PreparedStatement pstm = con.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			if(rs.next()) {
				n = rs.getInt(1);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return n;
	}
	public int addalldata(int id,int data_id,String data,String activity_name,String activity_data) {
		int n=0;
		String table = id+"_data";
		Connection conn=null;
		try {
			conn=DBManager.getInstance().getConnection();
			String sql="insert into "+table+" values(?,curdate(),?,?)";
			PreparedStatement p=conn.prepareStatement(sql);
			p.setInt(1, data_id);
			p.setString(2,activity_name);
			p.setString(3, activity_data);
			n=p.executeUpdate();
			if(n==1) {
				System.out.print("鎻掑叆鎴愬姛");
			}else {
				System.out.print("鎻掑叆澶辫触");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return n;
	}
}
