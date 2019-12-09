package location.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import location.dao.LocationDao;

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
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String loc = request.getParameter("loc");
		String id = request.getParameter("id");
		System.out.println(loc);
		System.out.println(id);
		Type type = new TypeToken<Location>(){}.getType();
		Type type1 = new TypeToken<Integer>(){}.getType();
		Gson gson = new Gson();
		Location location = new Location();
		location = gson.fromJson(loc,type);
		int a = gson.fromJson(id,type1);
		LocationService locationService = new LocationService();
		int i = locationService.InsertDefaultLocation(a, location);
		if(i>0) {
			out.write("添加成功");
		}else {
			out.write("添加失败");
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
