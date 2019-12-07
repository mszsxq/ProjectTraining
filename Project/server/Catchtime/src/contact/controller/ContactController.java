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
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String infor=request.getParameter("info");
		System.out.println(infor);
		ActivityService ls = new ActivityService();
		ContactDao ad = new ContactDao();
//		try {
//			ad.createContact(01);
//		} catch (ClassNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		switch(infor) {
			case "create":
				break;
			case "insert":
				System.out.println(infor);
				String activityid=request.getParameter("activityId");
				int activityId=Integer.parseInt(activityid)
;				System.out.println(activityId);
				String locationid = request.getParameter("locationId");
				int locationId = Integer.parseInt(locationid);
				Contact contact = new Contact(locationId,activityId);
				System.out.println(locationId);
				try {
					int id= ad.addIcon(01,contact);
					System.out.println("acID="+contact.getActivity_Id());
					if(id>0) {
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
