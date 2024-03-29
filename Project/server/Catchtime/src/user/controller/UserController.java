﻿package user.controller;

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
import data.dao.DataDao;
import detail.dao.DetailDao;
import entity.User;
import location.dao.LocationDao;
import newplace.dao.NewPlaceDao;
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
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		UserDao userDao = new UserDao();
		int count = userDao.countUser();
		count = count+1;
		Gson gson = new Gson();
		String gs = gson.toJson(count);
		//��������
		String phone = request.getParameter("phone");
		String pwd = request.getParameter("pwd");
		if(phone !=null && pwd !=null) {
			int a = userDao.UpdateUserPassword(pwd, phone);
			if(a!=0) {
				out.write("重置成功");
			}else {
				out.write("重置失败");
			}
		}
		//ע��
		String client = request.getParameter("client");
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(new Date());
		if(client != null) {
			System.out.println(client);
//			Type listType=new TypeToken<User>(){}.getType();
	        User user = gson.fromJson(client,User.class);
//	        User user=new User();
//	        user.setPassword("123");
//	        user.setPhone("13731177152");
//	        user.setUser_id(0);
//	        System.out.println(user.toString());
//	        System.out.println(gson.toJson(user));

	        UserService userService = new UserService();
//	        int n = userService.addUser(count,"15264327461","123456",time);
	        int n = userService.addUser(count,user.getPhone(),user.getPassword(),time);
	        if(n!=0) {
				out.write(gs);
				LocationDao locationDao = new LocationDao();
				ActivityDao activityDao = new ActivityDao();
				DetailDao detailDao = new DetailDao();
				ContactDao contactDao = new ContactDao();
				DataDao dataDao = new DataDao();
				NewPlaceDao newPlaceDao = new NewPlaceDao();
				try {
					locationDao.createTable(count);
					activityDao.createTable(count);
					activityDao.insertData(count, "行走", 1);
					activityDao.insertData(count, "睡觉", 2);
					activityDao.insertData(count, "吃饭", 4);
					activityDao.insertData(count, "娱乐", 3);
					activityDao.insertData(count, "学习", 12);
					activityDao.insertData(count, "购物", 10);
					detailDao.createTable(count);
					contactDao.createContact(count);
					dataDao.createdatatable(count);
					newPlaceDao.createTable(count);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				out.write("手机号重复使用");
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
