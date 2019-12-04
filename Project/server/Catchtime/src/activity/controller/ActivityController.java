package activity.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import activity.dao.ActivityDao;
import activity.service.ActivityService;
import entity.Activity;
import location.service.LocationService;

/**
 * Servlet implementation class ActivityController
 */
@WebServlet("/ActivityController")
public class ActivityController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActivityController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String infor=request.getParameter("info");
		System.out.println(infor);
		ActivityService ls = new ActivityService();
		switch(infor) {
			case "create":
				break;
			case "insert":
				System.out.println(infor);
				String activityName=request.getParameter("activityName");
				System.out.println(activityName);
				String iconId = request.getParameter("iconId");
				int id = Integer.parseInt(iconId);
				System.out.println(iconId);
				try {
					int i= ls.insertTo(01,activityName,id);
					if(i>0) {
						response.getWriter().print("suc");
					}else {
						response.getWriter().print("false");
					}
					break;
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			case "findall":
				break;
			case "update":
				break;
			case "findSingle":
				break;
			case "delete":
				break;
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
