package Servlet;

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
import contact.dao.ContactDao;
import entity.Activity;
import entity.Contact;
import entity.Location;
import location.dao.LocationDao;
import location.service.LocationService;

/**
 * Servlet implementation class BackgroundServlet
 */
@WebServlet("/BackgroundServlet")
public class BackgroundServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BackgroundServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		int id=Integer.valueOf(request.getParameter("userid"));
		List<Activity> activities=new ActivityDao().findAll(id);
		List<Location> locations = null;
		List<Contact> contacts=null;
		if (activities==null) {
			activities=new ArrayList<Activity>();
		}
		try {
			contacts=new ContactDao().findAll(id);
			locations=new LocationDao().findAll(id);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (locations==null) {
			locations=new ArrayList<Location>();
		}
		if (contacts==null) {
			contacts=new ArrayList<Contact>();
		}
		
		Gson gson=new Gson();
		response.getWriter().write(gson.toJson(activities)+"-"+gson.toJson(locations)+"-"+gson.toJson(contacts));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
