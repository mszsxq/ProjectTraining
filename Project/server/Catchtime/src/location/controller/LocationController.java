package location.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
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

import entity.Location;
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
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");

		LocationDao dao=new LocationDao();
		List<Location> locations=new ArrayList<Location>();
		PrintWriter writer = response.getWriter();
		String str=request.getParameter("userid");
		if(str != null) {
			Gson gsonn=new Gson();
			User user=gsonn.fromJson(str, User.class);
			int id=user.getUser_id();
			
			try {
				locations=dao.findAll(id);
				Gson gson=new Gson();
				String gs=gson.toJson(locations);
				System.out.print(gs);
				writer.write(gs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		PrintWriter out = response.getWriter();
		String loc = request.getParameter("loc");
		String id1 = request.getParameter("id");
		if(loc != null && id1 != null) {
			Type type = new TypeToken<Location>(){}.getType();
			Type type1 = new TypeToken<Integer>(){}.getType();
			Gson gson = new Gson();
			Location location = new Location();
			location = gson.fromJson(loc,type);
			int a = gson.fromJson(id1,type1);
			LocationService locationService = new LocationService();
			int i = locationService.InsertDefaultLocation(a, location);
			if(i>0) {
				out.write("添加成功");
			}else {
				out.write("添加失败");
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
