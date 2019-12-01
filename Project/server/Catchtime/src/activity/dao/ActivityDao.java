/**
 * 
 */
package activity.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import activity.activity_entity;
import util.DBManager;

/**
 * @author ZSX
 *
 */
public class ActivityDao {
	public void createTable() throws SQLException {
//		String add = "alter table ol_activity add constraint lll foreign key(icon_id) REFERENCES icon(icon_id);";
		Connection conn= null;
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		String sql = "CREATE TABLE ol_activity (activity_id int not null, "
				+ "activity_name varchar(50), icon_id smallint,"
				+ "primary key(activity_id),foreign key(icon_id) references icon(icon_id));";
		try {
			 conn= DBManager.getInstance().getConnection();
			 pst = conn.prepareStatement(sql);
			 int i=pst.executeUpdate();
//			 pst1 = conn.prepareStatement(add);
//			 pst1.execute();
			 if(i>0) {
				 System.out.println("创建成功");
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
	public void insertData(String activity_name,int icon_id) throws ClassNotFoundException {
		Connection conn= null;
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		PreparedStatement pst2 = null;
		ResultSet rs=null;
		int location_id=0;
		String allNum="select * from ol_activity;";
		String s = "insert into ol_activity values(?,?,?);";
		try {
			conn = DBManager.getInstance().getConnection();
			pst1 = conn.prepareStatement(allNum);
			rs = pst1.executeQuery();
			while(rs.next()) {
				location_id = rs.getInt(1);
			}
			pst = conn.prepareStatement(s);
			pst.setInt(1,location_id+1);
			pst.setString(2, activity_name);
			pst.setInt(3, icon_id);
			int i = pst.executeUpdate();
			
			if(i>0) {
				System.out.println("插入成功");
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void delete(int i) throws SQLException {
		Connection conn= null;
		PreparedStatement pst1 = null;
		String sql1 = "delete from ol_activity where icon_id = ?;";
		try {
			conn = DBManager.getInstance().getConnection();
			pst1 = conn.prepareStatement(sql1);
			pst1.setInt(1, i);
			int m =pst1.executeUpdate();	
			if(m>0) {
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
			pst1.close();
		}
		
	}
	public void updateName(int id,int icon_id) throws SQLException{
		Connection conn= null;
		PreparedStatement pst = null;
		String sql ="update ol_activity set icon_id=? where activity_id = ?";
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
			conn.close();
			pst.close();
		}
	}
	public List<activity_entity> findAll() {
		Connection conn= null;
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		ResultSet rs=null;
		ResultSet rs1=null;
		List<activity_entity> list=new ArrayList<activity_entity>();
		try {
			conn = DBManager.getInstance().getConnection();
			pst = conn.prepareStatement("select * from ol_activity;");
			rs = pst.executeQuery();
			while(rs.next()) {
				activity_entity activity = new activity_entity();
				activity.setActivity_name(rs.getString(2));
				int icon_id = rs.getInt(3);
				pst1 = conn.prepareStatement("select * from icon where icon_id = ?");
				pst1.setInt(1,icon_id);
				rs1 = pst1.executeQuery();
				while(rs1.next()) {
					activity.setIcon_name(rs1.getString(2));
					activity.setIcon_color(rs1.getString(3));
				}
				list.add(activity);		
			}
			System.out.println(list.get(1).toString());
			return list;
			} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public activity_entity findSingle(int id) {
		Connection conn= null;
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		ResultSet rs=null;
		ResultSet rs1=null;
		activity_entity activity = null;
		try {
			conn = DBManager.getInstance().getConnection();
			pst = conn.prepareStatement("select * from ol_activity where activity_id=?;");
			pst.setInt(1, id);
			rs = pst.executeQuery();
			activity = new activity_entity();
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
			return activity;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return activity;
	}
}
