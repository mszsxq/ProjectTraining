package detail.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import detail.dao.DetailDao;
import entity.Activity;
import entity.Location;
import entity.Time;

/**
 * Servlet implementation class DetailController
 */
@WebServlet("/DetailController")
public class DetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetailController() {
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
		String acname = request.getParameter("acname");
		String userid = request.getParameter("userid");
		String time = request.getParameter("time");
		System.out.println(acname);
		System.out.println(userid);
		System.out.println(time);
		List<Time> listtime = new ArrayList<>();
		ActivityDao dao = new ActivityDao();
		if(acname!=null && userid!=null) {
			Activity ac = dao.findSingleforname(Integer.parseInt(userid), acname);
			DetailDao dao2 = new DetailDao();
			listtime = dao2.findTimeListbyidandtime(ac.getActivity_id(), time, Integer.parseInt(userid));
			Gson gson = new Gson();
			String timeac = gson.toJson(listtime);
			if(timeac!=null) {
				System.out.println("接受到时间了");
				System.out.println(timeac);
				out.write(timeac);
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
