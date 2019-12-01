/**
 * 
 */
package user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import entity.User;
import util.DBManager;

/**
 * @author ZSX
 *
 */
public class UserDao {
	public User findPassword(String phone,String password) {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		User user = null;
		try {
			String sql = "select * from user where phone = ? and password = ?";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, phone);
			pstm.setString(2, password);
			ResultSet rs = pstm.executeQuery();
			if(rs.next()) {
				user = new User();
				user.setPhone(rs.getString(2));
				user.setUsername(rs.getString(4));
				user.setRegister_date(rs.getString(5));
				user.setMoto(rs.getString(6));
				user.setImage(rs.getString(7));
			}
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				DBManager.getInstance().closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public int countUser() {
		String sql = "select count(*) from user";
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
	public int register(int id,String phone,String password,String time) {
		String sql = "Insert into user(user_id,phone,password,register_date) value(?,?,?,?)";
		int n = 0;
		Connection conn = null;
		try {
			conn = DBManager.getInstance().getConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(2, phone);
			ps.setString(3, password);
			ps.setInt(1, id);
			ps.setString(4, time);
			n = ps.executeUpdate();
			ps.close();
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
	public int addUser(User user){
		String sql = "Insert into user(user_id,phone,password,username,register_date,moto,image) value(?,?,?,?,?,?,?)";
		Connection conn = null;
		try {
			conn = DBManager.getInstance().getConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int n = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, user.getUser_id());
			ps.setString(2, user.getPhone());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getUsername());
			ps.setString(5, user.getRegister_date());
			ps.setString(6, user.getMoto());
			ps.setString(7, user.getImage());
			n = ps.executeUpdate();
			ps.close();
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
	public int UpdateUserMoto(String moto,int user_id) {
		String sql = "update user set moto=? where user_id=?";
		Connection conn = null;
		int n = 0;
		try {
			conn = DBManager.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,moto);
			ps.setInt(2, user_id);
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
	public int UpdateUserImage(String image,int user_id) {
		String sql = "update user set image=? where user_id=?";
		Connection conn = null;
		int n = 0;
		try {
			conn = DBManager.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,image);
			ps.setInt(2, user_id);
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
	public int UpdateUserPassword(String password,int user_id) {
		String sql = "update user set password=? where user_id=?";
		Connection conn = null;
		int n = 0;
		try {
			conn = DBManager.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,password);
			ps.setInt(2, user_id);
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
}
