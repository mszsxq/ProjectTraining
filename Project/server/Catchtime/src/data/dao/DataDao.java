/**
 * 
 */
package data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.All_data;
import util.DBManager;

/**
 * @author ZSX
 *
 */
public class DataDao {
	//创建表
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
				System.out.print("创建成功");
			}else {
				System.out.print("创建失败");
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
	
	
	//插入数据
	public int addalldata(String tablename,int data_id,String data,String activity_name,String activity_data) {
		int n=0;
		Connection conn=null;
		try {
			conn=DBManager.getInstance().getConnection();
			String sql="insert into ? values(?,?,?,?)";
			PreparedStatement p=conn.prepareStatement(sql);
			p.setString(1, tablename);
			p.setInt(2, data_id);
			p.setString(3, data);
			p.setString(4,activity_name);
			p.setString(5, activity_data);
			n=p.executeUpdate();
			if(n==1) {
				System.out.print("插入成功");
			}else {
				System.out.print("插入失败");
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
	
	//删除数据
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
				System.out.print("删除成功");
			}else {
				System.out.print("删除失败");
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
	//查询数据
	public All_data findalldataBydataId(String tablename,int data_id) {
		All_data all_data=null;
		Connection conn=null;
		ResultSet rs;
		try {
			conn=DBManager.getInstance().getConnection();
			String sql="select * from ? where data_id=?";
			PreparedStatement p=conn.prepareStatement(sql);
			p.setString(1, tablename);
			p.setInt(2, data_id);
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
}
