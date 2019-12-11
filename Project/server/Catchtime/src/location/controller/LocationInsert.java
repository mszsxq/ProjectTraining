package location.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import activity.dao.ActivityDao;
import entity.Activity;
import location.dao.LocationDao;
import location.service.LocationService;

/**
 * Servlet implementation class LocationInsert
 */
@WebServlet("/LocationInsert")
public class LocationInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LocationInsert() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
//		int userid=Integer.parseInt(request.getParameter("userid"));
		String infor=request.getParameter("info");
		LocationDao ls = new LocationDao();
		switch(infor) {
		case "create":
			break;
		case "insert":
			String locationName=request.getParameter("locationName");
			String detailName=request.getParameter("detailName");
			double lat=Double.parseDouble(request.getParameter("lat")) ;
			double lng=Double.parseDouble(request.getParameter("lng")) ;
			int userId1=Integer.parseInt(request.getParameter("userId"));
			try {
				int id= ls.insertData(userId1,locationName,lat,lng,50,detailName);
				System.out.println(id);
				if(id>0) {
					response.getWriter().print(id);
					
				}else {
					response.getWriter().print("false");
				}
				break;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case "findall":
				System.out.println(2);
				Gson gson = new Gson();
				int userId = gson.fromJson("1", int.class);
				List<Activity> list = new ArrayList<>();
				ActivityDao dao = new ActivityDao();
				list = dao.findAll(userId);
				String aclist = gson.toJson(list);
				System.out.println(aclist);
				response.getWriter().print(aclist);
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
