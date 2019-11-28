package detail.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Time;
import entity.User;
import util.DBManager;

public class DetailDao {
	public void createTable() {
		try {
			Connection conn = DBManager.getInstance().getConnection();
			String sql = "create table detail_tablename(detail_id int primary key,activity_id int,loaction_id int,begin_time varchar(40),finish_time varchar(40))";
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
	public int addDetail(int id,int activity_id,int location_id,String begin,String finish) {
		int n = 0;
		try {
			Connection conn = DBManager.getInstance().getConnection();
			String sql = "Insert into detail_tablename value(?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, activity_id);
			ps.setInt(3, location_id);
			ps.setString(4, begin);
			ps.setString(5, finish);
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
	public Time findDetail(int activity_id,int location_id) {
		Time time = null;
		List<Time> times = new ArrayList<>();
		String sql = "select begin_time,finish_time from detail_tablename where activity_id = ? and location_id = ?";
		try {
			Connection conn = DBManager.getInstance().getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, activity_id);
			pstm.setInt(2, location_id);
			ResultSet rs = pstm.executeQuery();
			if(rs.next()) {
				time = new Time();
				time.setBegin_time(rs.getString(4));
				time.setFinish_time(rs.getString(5));
				times.add(time);
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
		return time;
	}
}
