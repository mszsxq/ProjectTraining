package detail.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.All_data;
import entity.Detail;
import entity.Time;
import entity.User;
import util.DBManager;

public class DetailDao {
	public void createTable(int id) {
		String table = id+"detail_tablename";
		try {
			Connection conn = DBManager.getInstance().getConnection();
			String sql = "create table "+table+"(detail_id int primary key,activity_id int,location_id int,begin_time varchar(40),finish_time varchar(40))";
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
	public int upDateDetail(int id,int activity_id,int location_id,String begin,String finish) {
		int n = 0;
		String table = id+"detail_tablename";
		try {
			Connection conn = DBManager.getInstance().getConnection();
			String sql ="update "+table+"detail_tablename set activity_id=? and location where activity_id = ?";
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
	public int upDateDetailbyDetailid(int id,String begin,String finish,int detailid) {
		int n = 0;
		String table = id+"detail_tablename";
		try {
			Connection conn = DBManager.getInstance().getConnection();
			String sql ="update "+table+" set begin_time=? , finish_time=? where detail_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, begin);
			ps.setString(2, finish);
			ps.setInt(3, detailid);
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
	public int addDetail(int id,int activity_id,int location_id,String begin,String finish) {
		int n = 0;
		try {
			Connection conn = DBManager.getInstance().getConnection();
			String sql = "Insert into "+id+"detail_tablename value(?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			System.out.println(findalldata(id));
			ps.setInt(1,findalldata(id)+1);
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
	public int findalldata(int id) {
		Detail all_data=null;
		List<Detail> data = new ArrayList<>();
		Connection conn=null;
		ResultSet rs;
		try {
			conn=DBManager.getInstance().getConnection();
			String sql="select * from "+id+"detail_tablename";
			PreparedStatement p=conn.prepareStatement(sql);
			rs=p.executeQuery();
			while(rs.next()) {
				all_data=new Detail();
				all_data.setDetail_id(rs.getInt(1));
				all_data.setActivity_id(rs.getInt(2));
				all_data.setLocation_id(rs.getInt(3));
				all_data.setBegin_time(rs.getString(4));
				all_data.setFinish_time(rs.getString(5));
				data.add(all_data);
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data.size();
		
	}
	public Time findDetail(int id,int activity_id,int location_id) {
		Time time = null;
		List<Time> times = new ArrayList<>();
		String sql = "select begin_time,finish_time from "+id+"detail_tablename where activity_id = ? and location_id = ?";
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
	public Time findTimebyidandtime(int activity_id,String timelike,int id) {
		Time time = null;
		List<Time> times = new ArrayList<>();
		String sql = "select * from "+id+"detail_tablename where begin_time like ?";
		try {
			Connection conn = DBManager.getInstance().getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, timelike+"%");
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				time = new Time();
				System.out.println("begin"+rs.getString(1));
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
	public int findIdbyidandtime(int activity_id,String timelike,int id) {
		int num=0;
		String sql = "select * from "+id+"detail_tablename where begin_time like ? and activity_id=?";
		try {
			Connection conn = DBManager.getInstance().getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, timelike+"%");
			pstm.setInt(2, activity_id);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				System.out.println("Detail_id"+rs.getInt(1));
				num = rs.getInt(1);
				
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
		return num;
	}
	public int findIdbyidandextime(int activity_id,String timelike,int id) {
		int num=0;
		String sql = "select * from "+id+"detail_tablename where begin_time= ? and activity_id=?";
		try {
			Connection conn = DBManager.getInstance().getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, timelike);
			pstm.setInt(2, activity_id);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				System.out.println("OldBeginDetail_id"+rs.getInt(1));
				num = rs.getInt(1);
				
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
		return num;
	}
	public List<Time> findTimeListbyidandtime(int activity_id,String timelike,int id) {
		Time time = null;
		List<Time> times = new ArrayList<>();
		String sql = "select * from "+id+"detail_tablename where begin_time like ?";
		try {
			Connection conn = DBManager.getInstance().getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, timelike+"%");
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				time = new Time();
				System.out.println("begin"+rs.getString(1));
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
		return times;
	}
	public List<Time> findDetails(int id,int activity_id) {
		String table = id+"detail_tablename";
		List<Time> times = new ArrayList<>();
		String sql = "select * from "+table+" where activity_id = ?";
		try {
			Connection conn = DBManager.getInstance().getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, activity_id);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				Time time = new Time();
				time.setBegin_time(rs.getString("begin_time"));
				time.setFinish_time(rs.getString("finish_time"));
				times.add(time);
			}
			rs.close();
			pstm.close();
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
		return times;
	}
	public List<Time> findDetail(int id) {
		List<Time> times = new ArrayList<>();
		String sql = "select * from "+id+"detail_tablename";
		try {
			Connection conn = DBManager.getInstance().getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				Time time = new Time();
				time.setBegin_time(rs.getString("begin_time"));
				time.setFinish_time(rs.getString("finish_time"));
				times.add(time);
			}
			rs.close();
			pstm.close();
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
		return times;
	}
}
