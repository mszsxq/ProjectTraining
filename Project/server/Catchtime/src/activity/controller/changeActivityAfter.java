package activity.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		//int user_id = Integer.parseInt(request.getParameter("userId"));
		int user_id = Integer.parseInt(request.getParameter("userId"));
		int activity_id = Integer.parseInt(request.getParameter("activityId"));
		int icon_id = Integer.parseInt(request.getParameter("iconId"));
		String old_Name = request.getParameter("oldName");
		String date = request.getParameter("date");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd");
		String location_name =request.getParameter("locationName");
		String today = sdf.format(new Date());
		int old_id =0;
		if(!old_Name.equals("404")) {
			old_id= ad.findActivityId(user_id, old_Name);
		}else {
			old_id = 404;
		}
		int location_id = ad.findLocationId(user_id,location_name);
		if(today.equals(date)) {
			ad.updateDta(user_id, activity_id, location_id, icon_id, old_id,today);
		}else {
			String activity_name = ad.findActivityName(user_id, activity_id);
			System.out.println("¼ì²â"+user_id+"--"+activity_name+"-"+old_Name+"--"+date);
			ad.updateDayDta(user_id,date,activity_name, old_Name);
			ad.updateDta(user_id, activity_id, location_id, icon_id, old_id,date);
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
