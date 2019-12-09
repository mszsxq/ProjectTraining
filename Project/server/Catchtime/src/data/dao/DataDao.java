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
	public int createdatatable(String table_name) {
		int n=0;
		Connection conn=null;
		try {
			conn=DBManager.getInstance().getConnection();
			String sql="create table ? (data_id int,data varchar(50) not null,activity_name varchar(50) not null,activity_data varchar(50),primary key(data_id,activity_name))";
			PreparedStatement p=conn.prepareStatement(sql);
			p.setString(1, table_name);
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
	
	
	//鎻掑叆鏁版嵁
	public int addalldata(String tablename,int data_id,String data,String activity_name,String activity_data) {
		int n=0;
		Connection conn=null;
		try {
			conn=DBManager.getInstance().getConnection();
			String sql="insert into "+tablename+" values(?,?,?,?)";
			PreparedStatement p=conn.prepareStatement(sql);
			p.setInt(1, data_id);
			p.setString(2, data);
			p.setString(3,activity_name);
			p.setString(4, activity_data);
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
	
	//鍒犻櫎鏁版嵁
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
				System.out.print("鍒犻櫎鎴愬姛");
			}else {
				System.out.print("鍒犻櫎澶辫触");
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
	//鏌ヨ鏁版嵁
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
}
