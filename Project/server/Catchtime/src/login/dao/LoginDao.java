package login.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.google.gson.Gson;

import entity.User;
import util.DBManager;

public class LoginDao {
	public String findId(int userId) {
		int n=0;
		Connection conn= null;
		PreparedStatement pst = null;
		ResultSet rs=null;
		String gg = null;
		try {
			conn=DBManager.getInstance().getConnection();
			pst=conn.prepareStatement("select * from user where user_id=?");
			rs = pst.executeQuery();
			if(rs.next()) {
				User user = new User();
				user.setPhone(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setUsername(rs.getString(4));
				user.setRegister_date(rs.getString(5));
				user.setMoto(rs.getString(6));
				user.setImage(rs.getString(7));
				Gson gson = new Gson();
				gg = gson.toJson(user);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gg;
	}
}
