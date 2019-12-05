package location.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import location.dao.LocationDao;
import location.service.LocationService;

/**
 * Servlet implementation class LocationController
 */
@WebServlet("/LocationController")
public class LocationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LocationController() {
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
		LocationService ls = new LocationService();
//		try {
//			new LocationDao().createTable(01);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		switch(infor) {
			case "insert":
				String detailName=request.getParameter("detailName");
				String locationName = request.getParameter("locationName");
				String lat = request.getParameter("lat");
				double lat1=Double.parseDouble(lat);
				String lng = request.getParameter("lng");
				double lng1=Double.parseDouble(lng);
				int id=ls.insertTo(01,locationName, "det", lat1, lng1);
				if(id>0) {
					System.out.println(id);
					response.getWriter().print(id);
				}else {
					response.getWriter().print("false");
				}
				break;
			case "create":
				break;
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
