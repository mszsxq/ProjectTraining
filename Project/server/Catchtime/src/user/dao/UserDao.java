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
		String sql = "Insert into user(user_id,phone,password,register_date,username) value(?,?,?,?,\"moren\")";
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
				String[] list = date.split(" ");
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				String today = sd.format(new Date());
				System.out.println(date+"+"+today);
				days=(int) dateDiff(list[0],today,"yyyy-MM-dd");
			}
			return days;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				ps.close();
				DBManager.getInstance().closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return days;
	}
	
	public long dateDiff(String startTime, String endTime, String format) throws ParseException {
        // 锟斤拷锟秸达拷锟斤拷母锟绞斤拷锟斤拷锟揭伙拷锟絪impledateformate锟斤拷锟斤拷
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;// 一锟斤拷暮锟斤拷锟斤拷锟�
        long nh = 1000 * 60 * 60;// 一小时锟侥猴拷锟斤拷锟斤拷
        long nm = 1000 * 60;// 一锟斤拷锟接的猴拷锟斤拷锟斤拷
        long ns = 1000;// 一锟斤拷锟接的猴拷锟斤拷锟斤拷
        long diff;
        long day = 0;
            // 锟斤拷锟斤拷锟斤拷锟绞憋拷锟侥猴拷锟斤拷时锟斤拷锟斤拷锟�
            diff = sd.parse(endTime).getTime()
                    - sd.parse(startTime).getTime();
            day = diff / nd;// 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
            long hour = diff % nd / nh;// 锟斤拷锟斤拷锟斤拷锟斤拷小时
            long min = diff % nd % nh / nm;// 锟斤拷锟斤拷锟斤拷锟劫凤拷锟斤拷
            long sec = diff % nd % nh % nm / ns;// 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
            // 锟斤拷锟斤拷锟斤拷
            System.out.println("时锟斤拷锟斤拷睿�" + day + "锟斤拷" + hour + "小时" + min
                    + "锟斤拷锟斤拷" + sec + "锟诫。");
            if (day>=1) {
                  return day+1;
            }else {
                if (day==0) {
                    return 1;
                }else {
                    return 0;
                }
                
            }
        
    }
	public int UpdateUserPassword(String password,String phone) {
		String sql = "update user set password=? where phone=?";
		Connection conn = null;
		int n = 0;
		try {
			conn = DBManager.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,password);
			ps.setString(2, phone);
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
	public User findUser(int userId) {
		int n=0;
		Connection conn= null;
		PreparedStatement pst = null;
		ResultSet rs=null;
		User user = null;
		try {
			conn=DBManager.getInstance().getConnection();
			pst=conn.prepareStatement("select * from user where user_id=?;");
			pst.setInt(1, userId);
			rs = pst.executeQuery();
			while(rs.next()) {
				user = new User();
				user.setUsername(rs.getString("username"));
				user.setMoto(rs.getString("moto"));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
}
