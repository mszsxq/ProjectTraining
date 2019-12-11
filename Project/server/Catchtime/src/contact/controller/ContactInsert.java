package contact.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import activity.service.ActivityService;
import contact.dao.ContactDao;
import entity.Contact;

/**
 * Servlet implementation class ContactInsert
 */
@WebServlet("/ContactInsert")
public class ContactInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContactInsert() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		ContactDao ad=new ContactDao();
		String infor=request.getParameter("info");
		Contact contact=new Contact();
		int activityId=Integer.parseInt(request.getParameter("activityId"));
		System.out.println(activityId);
		int locationId=Integer.parseInt(request.getParameter("locationId"));
		int userId1=Integer.parseInt(request.getParameter("userId"));
		contact.setActivity_Id(activityId);
		contact.setLocation_Id(locationId);
		System.out.println("ssss");
		switch(infor) {
		case "insert":
			try {
				int i =ad.addIcon(userId1,contact);
				if(i>0) {
					response.getWriter().print("suc");
				}else {
					System.out.println("contact四百");
					response.getWriter().print("false");
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
