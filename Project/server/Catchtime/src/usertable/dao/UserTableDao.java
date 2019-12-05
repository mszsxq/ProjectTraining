/**
 * 
 */
package usertable.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonObject;

import entity.User_table;
import util.DBManager;

/**
 * @author ZSX
 *
 */
public class UserTableDao {
	//user_table表的插入
	public int addusertabledata(int user_id, String location_table_name, String activity_table_name, String connection_table_name,
			String detaildata_table_name, String newplace_table_name, String dayrecord_table_name) throws ClassNotFoundException {
		int n=0;
		PreparedStatement p = null;
		try {
			Connection conn=DBManager.getInstance().getConnection();
			String sql="insert into user_table values(?,?,?,?,?,?,?)";
			p=conn.prepareStatement(sql);
			p.setInt(1, user_id);
			p.setString(2, location_table_name);
			p.setString(3, activity_table_name);
			p.setString(4, connection_table_name);
			p.setString(5,detaildata_table_name);
			p.setString(6, newplace_table_name);
			p.setString(7, dayrecord_table_name);
			n=p.executeUpdate();
			if(n==1) {
				System.out.print("插入成功");
			}else {
				System.out.print("插入失败");
			}
			return n;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return n;
	}
	
	//根据user_id查询
	
	public User_table findusertableByuserid(int id) {
		ResultSet rs=null;
		User_table usertable=null;
		PreparedStatement pstm = null;
		
		try {
			Connection conn=DBManager.getInstance().getConnection();
			String sql="select * from user_table where user_id=?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id);
			rs=pstm.executeQuery();
			while(rs.next()) {
				usertable=new User_table();
				usertable.setUser_id(rs.getInt(1));
				usertable.setLocation_table_name(rs.getString(2));
				usertable.setActivity_table_name(rs.getString(3));
				usertable.setConnection_table_name(rs.getString(4));
				usertable.setDetaildata_table_name(rs.getString(5));
				usertable.setNewplace_table_name(rs.getString(6));
				usertable.setDayrecord_table_name(rs.getString(7));
			}
			return usertable;
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// TODO Auto-generated method stub
		return usertable;
	}

}
