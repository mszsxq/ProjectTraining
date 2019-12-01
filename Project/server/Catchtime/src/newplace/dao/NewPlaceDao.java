/**
 * 
 */
package newplace.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.DBManager;

/**
 * @author ZSX
 *
 */
public class NewPlaceDao {
	public void createTable(int id) {
		String table = id+"_newLocation";
		try {
			Connection conn = DBManager.getInstance().getConnection();
			String sql = "create table "+table+"(newLocation_id int primary key,newLocation_name varchar(100),number int,lat double,lnt double,range int);";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.executeUpdate();
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
	}
	public void insert(int userId,int id,String name,int num,double lat,double lng,int range) {
		Connection conn= null;
		PreparedStatement pst = null;
		ResultSet rs=null;
		String table=userId+"_newlocation";
		String sql = "insert into "+table+" values(?,?,?,?,?,?);";
		try {
			conn = DBManager.getInstance().getConnection();
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			pst.setString(2, name);
			pst.setInt(3, num);
			pst.setDouble(4, lat);
			pst.setDouble(5, lng);
			pst.setInt(6, range);
			int i = pst.executeUpdate();
			if(i>0) {
				System.out.println("插入成功");
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
	public void update(int userId,int id) {
		Connection conn= null;
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		ResultSet rs=null;
		ResultSet rs1=null;
		int num=0;
		String table=userId+"_newlocation";
		String sql = "select number from "+table+"where id = ?;";
		String sql1 = "update "+table+" set number = ? where id = ?;";
		try {
			conn = DBManager.getInstance().getConnection();
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			while(rs.next()) {
				num=rs.getInt(3);
			}
			pst1 = conn.prepareStatement(sql1);
			pst1.setInt(1, num);
			pst1.setInt(2, id);
			int i=pst1.executeUpdate();
			if(i>0) {
				System.out.println("更新成工");
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
