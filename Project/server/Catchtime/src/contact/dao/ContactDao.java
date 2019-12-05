package contact.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Contact;
import util.DBManager;

public class ContactDao {
	private Connection conn;
	private PreparedStatement ps=null;
	
	public void createContact(int id) throws ClassNotFoundException, SQLException {
		conn = DBManager.getInstance().getConnection();
		String table = id+"_contact";
		String sql="create table "+table+"(location_id int,activity_id int,primary key(location_id,activity_id))";
		ps=conn.prepareStatement(sql);
		ps.executeUpdate();
	}
	public int addIcon(int userId,Contact contact) throws ClassNotFoundException, SQLException{
		conn = DBManager.getInstance().getConnection();
		String table = userId+"_contact";
		String sql="insert into "+table+" (location_id,activity_id) values(?,?)";
		ps=conn.prepareStatement(sql);
		ps.setInt(1, contact.getLocation_Id());
        ps.setInt(2, contact.getActivity_Id());
        int i= ps.executeUpdate();
        conn.close();
        ps.close();
        return i;
	}
	public int queryContactLocation(int activity_id) throws ClassNotFoundException, SQLException {
		DBManager db = DBManager.getInstance();
		conn = db.getConnection();
		int location = 0;
		String sql = "select location_id from icon where activity_id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, activity_id);
			ResultSet rst = ps.executeQuery();
			while (rst.next()) {			
				 location = rst.getInt(1);
			}
			db.closeConnection();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return location;
			
	}
	public int queryContactActivity(int location_id) throws ClassNotFoundException, SQLException {
		DBManager db = DBManager.getInstance();
		conn = db.getConnection();
		int activity = 0;
		String sql = "select activity_id from icon where location_id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, location_id);
			ResultSet rst = ps.executeQuery();
			while (rst.next()) {			
				 activity = rst.getInt(2);
			}
			db.closeConnection();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return activity;
			
	}
}
