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
		ActivityService ls = new ActivityService();
//		try {
//			new ActivityDao().createTable(01);
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		switch(infor) {
			case "create":
				break;
			case "insert":
				String activityName=request.getParameter("activityName");
				System.out.println(activityName);
				String iconId = request.getParameter("iconId");
				int iconid = Integer.parseInt(iconId);
				try {
					int id= ls.insertTo(01,activityName,iconid);
					if(id>0) {
						System.out.println(id);
						response.getWriter().print(id);
						
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
