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
import activity.service.ActivityService;
import entity.Activity;
import location.dao.LocationDao;
import location.service.LocationService;

/**
 * Servlet implementation class LocationFind
 */
@WebServlet("/LocationFind")
public class LocationFind extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LocationFind() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		int userid=Integer.parseInt(request.getParameter("userid"));
		LocationService ls = new LocationService();
		Gson gson = new Gson();
		List<String> list = new ArrayList<>();
		LocationDao ld = new LocationDao();
		try {
			list = ld.findAllLoc(userid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String aclist = gson.toJson(list);
		System.out.println(aclist);
		response.getWriter().print(aclist);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
