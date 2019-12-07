package activity.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import activity.dao.ActivityDao;

/**
 * Servlet implementation class changeActivityAfter
 */
@WebServlet("/changeActivityAfter")
public class changeActivityAfter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public changeActivityAfter() {
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
		PrintWriter out = response.getWriter();
		ActivityDao ad = new ActivityDao();
		int user_id = Integer.parseInt(request.getParameter("userId"));
		int activity_id = Integer.parseInt(request.getParameter("activityId"));
		int icon_id = Integer.parseInt(request.getParameter("iconId"));
		String old_Name = request.getParameter("oldName");
		int old_id =0;
		if(!old_Name.equals("404")) {
			old_id= ad.findActivityId(user_id, old_Name);
		}
		old_id = 404;
		int location_id = Integer.parseInt(request.getParameter("locationId"));
		
		ad.updateDta(user_id, activity_id, location_id, icon_id, old_id);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
