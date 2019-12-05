/**
 * 
 */
package user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
<<<<<<< HEAD
	public int findDays(int user_id) throws ParseException {
		String sql = "select register_date from user where user_id=?";
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps=null;
		int days=0;
		try {
			conn = DBManager.getInstance().getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, user_id);
			rs = ps.executeQuery();
			while(rs.next()) {
				String date = rs.getString(1);
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				String today = sd.format(new Date());
				System.out.println(date+"+"+today);
				days=(int) dateDiff(date,today,"yyyy-MM-dd");
			}
			return days;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
=======
	public User querryById(int id) {
		User user =null;
		String sql="select * from user where user_id =?";
		Connection conn =null;
		try {
			conn =DBManager.getInstance().getConnection();
			PreparedStatement ps =conn.prepareStatement(sql);
			ps.setInt(1,id);
			ResultSet rs =ps.executeQuery();
			while(rs.next()) {
				int id1 =rs.getInt(1);
				String phone =rs.getString(2);
				String password =rs.getString(3);
				String username =rs.getString(4);
				String register_date =rs.getString(5);
				String moto =rs.getString(6);
				String image =rs.getString(7);
				user = new User(id,phone,password,username,register_date,moto,image);
			}
		}catch (SQLException | ClassNotFoundException e) {
>>>>>>> 2d3f4686c22d2fe3876988538701c5bf0261d76d
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
<<<<<<< HEAD
				rs.close();
				ps.close();
=======
>>>>>>> 2d3f4686c22d2fe3876988538701c5bf0261d76d
				DBManager.getInstance().closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
<<<<<<< HEAD
		return days;
	}
	
	public long dateDiff(String startTime, String endTime, String format) throws ParseException {
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
            // 获得两个时间的毫秒时间差异
            diff = sd.parse(endTime).getTime()
                    - sd.parse(startTime).getTime();
            day = diff / nd;// 计算差多少天
            long hour = diff % nd / nh;// 计算差多少小时
            long min = diff % nd % nh / nm;// 计算差多少分钟
            long sec = diff % nd % nh % nm / ns;// 计算差多少秒
            // 输出结果
            System.out.println("时间相差：" + day + "天" + hour + "小时" + min
                    + "分钟" + sec + "秒。");
            if (day>=1) {
                  return day;
            }else {
                if (day==0) {
                    return 1;
                }else {
                    return 0;
                }
                
            }
        
    }
=======
		return user;
	}
>>>>>>> 2d3f4686c22d2fe3876988538701c5bf0261d76d
}
