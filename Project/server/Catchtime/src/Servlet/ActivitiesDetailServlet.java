package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import data.service.DataService;
import entity.All_data;
import entity.User;
import user.service.UserService;
import usertable.dao.UserTableDao;
import usertable.service.UserTableService;

/**
 * Servlet implementation class ActivitiesDetailServlet
 */
@WebServlet("/ActivitiesDetailServlet")
public class ActivitiesDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActivitiesDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		int  id =1;
//		String activityName ="cycle";
		int id =Integer.valueOf(request.getParameter("id"));
		String activityName =request.getParameter("activity");
		System.out.println(activityName);
		UserTableService userTableService =new UserTableService();	
		String tableName = userTableService.querryDayTableById(id);
		
		DataService dataService =new DataService();
//		List<All_data> list =null;
//		list =dataService.activityRecently(tableName, activityName);
		List<All_data> list1 =null;
		list1=dataService.activityMonthly(tableName, activityName);
		String liststr="";
		System.out.println(list1.toString());
		if(list1==null) { 	
		System.out.println("未查到数据");
		}else {
			Gson gson =new Gson();
			liststr =gson.toJson(list1);
			System.out.println(liststr);
		}	
		PrintWriter write = response.getWriter();
		write.write(liststr);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
