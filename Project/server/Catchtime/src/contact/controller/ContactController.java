package contact.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import activity.dao.ActivityDao;
import contact.dao.ContactDao;
import entity.Contact;
import location.dao.LocationDao;

/**
 * Servlet implementation class ContactController
 */
@WebServlet("/ContactController")
public class ContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContactController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String location_name=request.getParameter("location_name");//Android传过来的name
		String activity_name=request.getParameter("activiity_name");//Android传过来的name
		String str=request.getParameter("userid");
		Gson gson=new Gson();
		User user=gson.fromJson(str, User.class);
		int id=user.getUser_id();//Android传过来的id
		int activity_id=0;
		int location_id=0;
		ContactDao dao=new ContactDao();
		ActivityDao dao1=new ActivityDao();
		LocationDao dao2=new LocationDao();
		activity_id=dao1.findId(id, activity_name);
		location_id=dao2.findId(id,location_name);
		Contact contact=new Contact();
		contact.setActivity_Id(activity_id);
		contact.setLocation_Id(location_id);
		try {
			int a=dao.addContact(id,contact);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
