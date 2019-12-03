package Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
//		int id =Integer.valueOf(request.getParameter("id"));
		int  id =1;
		String activityName ="cycle";
		List<All_data> list =null;
		UserTableService userTableService =new UserTableService();
		String tableName = userTableService.querryDayTableById(id);
		DataService dataService =new DataService();
		list =dataService.activityRecently(tableName, activityName);
		if(list==null) {
		System.out.println("未查到数据");
		}else {
			for(All_data da :list) {
				System.out.println(da);
			}
		}
		
//		User user =new User(1,"133","ps","zsx","1999","eat","d");
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
