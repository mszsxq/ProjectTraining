/**
 * 
 */
package location.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import activity.activity_entity;
import location.Location_entity;
import util.DBManager;

/**
 * @author ZSX
 *
 */
public class LocationDao {
	public void createTable() throws SQLException {
		Connection conn= null;
		PreparedStatement pst = null;
		String sql = "CREATE TABLE ol_location (location_id int not null, "
				+ "location_name varchar(50), location_lat double,location_lng double,"
				+"location_range int,location_detailed varchar(50),"
				+ "primary key(location_id));";
		try {
			 conn= DBManager.getInstance().getConnection();
			 pst = conn.prepareStatement(sql);
			 int i=pst.executeUpdate();
			 if(i>0) {
				 System.out.println("创建成功");
			 }
			 
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			conn.close();
			pst.close();
		}
	}
	public void insertData(String name,double lat,double lng,int range,String detail) throws SQLException {
		Connection conn= null;
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		ResultSet rs=null;
		int id=0;
		String allNum="select * from ol_location;";
		String s = "insert into ol_location values(?,?,?,?,?,?);";
		try {
			conn = DBManager.getInstance().getConnection();
			pst1 = conn.prepareStatement(allNum);
			rs = pst1.executeQuery();
			while(rs.next()) {
				id = rs.getInt(1);
			}
			pst = conn.prepareStatement(s);
			pst.setInt(1,id+1);
			pst.setString(2, name);
			pst.setDouble(3, lat);
			pst.setDouble(4, lng);
			pst.setInt(5, range);
			pst.setString(6, detail);
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
			conn.close();
			pst.close();
		}
	}
	public void changeInfor(int id,String locationName,double lat,double lng,int range,String detail) {
		Connection conn= null;
		PreparedStatement pst = null;
		String sql = "update ol_location set location_name = ?,location_lat = ?"
				+ ",location_lng = ?,location_range = ?,location_detailed = ? where location_id = ?;";
		try {
			conn = DBManager.getInstance().getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, locationName);
			pst.setDouble(2, lat);
			pst.setDouble(3, lng);
			pst.setInt(4, range);
			pst.setString(5, detail);
			pst.setInt(6, id);
			int i =pst.executeUpdate();
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
	public void delete(int i) throws SQLException {
		Connection conn= null;
		PreparedStatement pst = null;
		String sql = "delete from ol_location where location_id = ?";
		try {
			conn = DBManager.getInstance().getConnection();
			pst = conn.prepareStatement(sql);
			pst.setInt(1, i);
			int j = pst.executeUpdate();
			if(j>0) {
				System.out.println("删除成功");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			conn.close();
			pst.close();
		}
		
	}
	public List<Location_entity> findAll() throws SQLException {
		Connection conn= null;
		PreparedStatement pst = null;
		ResultSet rs=null;
		List<Location_entity> list = new ArrayList<Location_entity>();
		try {
			conn = DBManager.getInstance().getConnection();
			pst = conn.prepareStatement("select * from ol_location");
			rs = pst.executeQuery();
			while(rs.next()) {
				Location_entity location = new Location_entity();
				location.setLocationId(rs.getInt(1));
				location.setLocationName(rs.getString(2));
				location.setLocationLat(rs.getDouble(3));
				location.setLocationLng(rs.getDouble(4));
				location.setLocationRange(rs.getInt(5));
				location.setLocationDetail(rs.getString(6));
				list.add(location);
			}
			return list;
			} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			conn.close();
			pst.close();
		}
		return list;
	}
	public Location_entity findSingle(int i) throws SQLException {
		Connection conn= null;
		PreparedStatement pst = null;
		ResultSet rs=null;
		Location_entity location = null;
		try {
			conn = DBManager.getInstance().getConnection();
			pst = conn.prepareStatement("select * from ol_location where location_id = ?");
			pst.setInt(1,i );
			rs = pst.executeQuery();
			while(rs.next()) {
				location = new Location_entity();
				location.setLocationName(rs.getString(2));
				location.setLocationLat(rs.getDouble(3));
				location.setLocationLng(rs.getDouble(4));
				location.setLocationRange(rs.getInt(5));
				location.setLocationDetail(rs.getString(6));
				return location;
			}
			return location;
			} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			conn.close();
			pst.close();
		}
		return location;
	}
}
