/**
 * 
 */
package icon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Icon;
import util.DBManager;

/**
 * @author ZSX
 *
 */
public class IconDao {
	private Connection conn;
	private PreparedStatement ps=null;
	public int addIcon(Icon icon) throws ClassNotFoundException, SQLException{
		conn = DBManager.getInstance().getConnection();
		String sql="insert into icon (icon_id,icon_address,color) values(?,?,?)";
		ps=conn.prepareStatement(sql);
		ps.setInt(1, icon.getIcon_Id());
        ps.setString(2, icon.getIcon_address());
        ps.setString(3, icon.getColor());
        int i= ps.executeUpdate();
        conn.close();
        ps.close();
        return i;
	}
	public List<Icon> queryIconAll(int icon_id) throws ClassNotFoundException, SQLException {
		List<Icon> icon= new ArrayList<Icon>();
		DBManager db = DBManager.getInstance();
		conn = db.getConnection();
		String sql = "select * from icon where icon_id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, icon_id);
			ResultSet rst = ps.executeQuery();
			while (rst.next()) {
				Icon newIcon = new Icon();			
				newIcon.setIcon_Id(rst.getInt(1));
				newIcon.setIcon_address(rst.getString(2));
				newIcon.setColor(rst.getString(3));
				icon.add(newIcon);
			}
			db.closeConnection();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return icon;
			
	}
	public String queryIconColor(int icon_id) throws ClassNotFoundException, SQLException {
		DBManager db = DBManager.getInstance();
		conn = db.getConnection();
		String color=null;
		String sql = "select color from icon where icon_id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, icon_id);
			ResultSet rst = ps.executeQuery();
			while (rst.next()) {			
				color = rst.getString(3);
			}
			db.closeConnection();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return color;
			
	}
	public String queryIconAddress(int icon_id) throws ClassNotFoundException, SQLException {
		DBManager db = DBManager.getInstance();
		conn = db.getConnection();
		String name=null;
		String sql = "select * from icon where icon_id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, icon_id);
			ResultSet rst = ps.executeQuery();
			while (rst.next()) {			
				name = rst.getString(2);
			}
			db.closeConnection();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
			
	}
}
