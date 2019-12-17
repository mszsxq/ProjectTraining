package login.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import entity.User;
import login.dao.LoginDao;
import util.DBManager;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		DBManager db = DBManager.getInstance();
		Connection conn = null;
		try {
			conn = db.getConnection();
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String client = request.getParameter("client");
		String userid = request.getParameter("userid");
		if(client!=null) {
			System.out.println("1");
			Gson gson=new Gson();
	        User user = gson.fromJson(client,User.class);
	        String sql = "select * from user where phone=?";
	        try {
				PreparedStatement pstm = conn.prepareStatement(sql);
				pstm.setString(1,user.getPhone());
				ResultSet rst = pstm.executeQuery();
				while (rst.next()) {	
					String android=user.getPassword();
					String shujuku=rst.getString(3);
					if(android.equals(shujuku)) {
						user.setUser_id(rst.getInt(1));
						user.setUsername(rst.getString(4));
						user.setMoto(rst.getString(6));
						user.setRegister_date(rst.getString(5));
						user.setImage(rst.getString(7));
						Gson gson1 = new Gson();
						String gs = gson1.toJson(user);
						System.out.println(gs);
						out.write(gs);
//						request.setAttribute("usering", newUser.getUser_id());
//						request.getRequestDispatcher("ActivityController").forward(request, response);
					}else {
						out.write("密码错误");
					}
				}
				db.closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(userid!=null) {
			System.out.println("userid"+userid);
			LoginDao lo = new LoginDao();
			String gg = lo.findId(Integer.parseInt(userid));
			System.out.println("user"+gg);
			out.write(gg);
		}
		
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
