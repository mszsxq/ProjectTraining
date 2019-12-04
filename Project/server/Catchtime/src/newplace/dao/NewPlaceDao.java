/**
 * 
 */
package newplace.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Location;
import entity.NewLocation;
import util.DBManager;

/**
 * @author ZSX
 *
 */
public class NewPlaceDao {
	public void createTable(int id) {
		Connection conn= null;
		PreparedStatement pst = null;
		String table = id+"_newlocation";
		String sql = "CREATE TABLE "+table+" (default_Location_Id int not null,"
				+ "default_Location_Name varchar(50),number int,longitude double,latitude double,"
				+"newLocation_range int,"
				+"primary key(default_Location_Id));";
		try {
			conn = DBManager.getInstance().getConnection();
			pst = conn.prepareStatement(sql);
			int i=pst.executeUpdate();
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
	public void insert(int userId,String name,double lat,double lng,int range) {
		Connection conn= null;
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		ResultSet rs1=null;
		int newlocation_id=0;
		int num=1;
		String table=userId+"_newlocation";
		String sql1="select * from "+table;
		String sql = "insert into "+table+" values(?,?,?,?,?,?);";
		try {
			conn = DBManager.getInstance().getConnection();
			pst1 = conn.prepareStatement(sql1);
			rs1 = pst1.executeQuery();
			while(rs1.next()) {
				newlocation_id = rs1.getInt(1);
			}
			pst = conn.prepareStatement(sql);
			pst.setInt(1, newlocation_id+1);
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
	public void delete(int userId,int i) throws SQLException {
		Connection conn= null;
		PreparedStatement pst = null;
		String table=userId+"_newlocation";
		String sql = "delete from "+table+" where default_Location_Id = ?";
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
		int num=0;
		String table=userId+"_newlocation";
		String sql = "select * from "+table+" where default_Location_Id = ?;";
		String sql1 = "update "+table+" set number = ? where default_Location_Id = ?;";
		try {
			conn = DBManager.getInstance().getConnection();
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			while(rs.next()) {
				num=rs.getInt(3);
				System.out.println(num);
			}
			pst1 = conn.prepareStatement(sql1);
			pst1.setInt(1, ++num);
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
	public List<NewLocation> findAll(int userId) throws SQLException {
		Connection conn= null;
		PreparedStatement pst = null;
		ResultSet rs=null;
		String table=userId+"_newlocation";
		List<NewLocation> list = new ArrayList<NewLocation>();
		try {
			conn = DBManager.getInstance().getConnection();
			pst = conn.prepareStatement("select * from "+table);
			rs = pst.executeQuery();
			while(rs.next()) {
				NewLocation newLocation = new NewLocation();
				newLocation.setDefault_Location_Id(rs.getInt(1));
				newLocation.setDefault_Location_Name(rs.getString(2));
				newLocation.setNumber(rs.getInt(3));
				newLocation.setLongitude(rs.getDouble(4));
				newLocation.setLatitude(rs.getDouble(5));
				newLocation.setRange(rs.getInt(6));
				list.add(newLocation);
			}
			return list;
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
	public NewLocation findSingle(int userId,int i) throws SQLException {
		Connection conn= null;
		PreparedStatement pst = null;
		ResultSet rs=null;
		NewLocation newLocation = null;
		String table=userId+"_newlocation";
		try {
			conn = DBManager.getInstance().getConnection();
			pst = conn.prepareStatement("select * from "+table+" where default_Location_Id = ?");
			pst.setInt(1,i );
			rs = pst.executeQuery();
			while(rs.next()) {
				newLocation = new NewLocation();
				newLocation.setDefault_Location_Id(rs.getInt(1));
				newLocation.setDefault_Location_Name(rs.getString(2));
				newLocation.setNumber(rs.getInt(3));
				newLocation.setLongitude(rs.getDouble(4));
				newLocation.setLatitude(rs.getDouble(5));
				newLocation.setRange(rs.getInt(6));
				return newLocation;
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
		return newLocation;
	}
	
}
