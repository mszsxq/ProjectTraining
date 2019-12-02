package user.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import activity.dao.ActivityDao;
import contact.dao.ContactDao;
import detail.dao.DetailDao;
import entity.User;
import location.dao.LocationDao;
import user.dao.UserDao;
import user.service.UserService;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/UserController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		UserDao userDao = new UserDao();
		int count = userDao.countUser();
		count = count+1;
		String client = request.getParameter("client");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(new Date());
		if(client != null) {
			Type listType=new TypeToken<User>(){}.getType();
			Gson gson=new Gson();
	        User user = gson.fromJson(client,listType);
	        UserService userService = new UserService();
	        int n = userService.addUser(count,user.getPhone(),user.getPassword(),time);
	        if(n!=0) {
				out.write("注册成功");
				LocationDao locationDao = new LocationDao();
				ActivityDao activityDao = new ActivityDao();
				DetailDao detailDao = new DetailDao();
				ContactDao contactDao = new ContactDao();
				try {
					locationDao.createTable(count);
					activityDao.createTable(count);
					detailDao.createTable(count);
					contactDao.createContact(count);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				out.write("手机号码重复使用!");
			}
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
