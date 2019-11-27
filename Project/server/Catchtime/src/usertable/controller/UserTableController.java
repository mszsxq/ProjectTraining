package usertable.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.User_table;
import usertable.dao.UserTableDao;

/**
 * Servlet implementation class UserTableController
 */
@WebServlet("/UserTableController")
public class UserTableController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserTableController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		UserTableDao dao=new UserTableDao();
		int id=0;
		User_table usertable=dao.findusertableByuserid(id);
		//根据id得到location_table_name
		String location=usertable.getLocation_table_name().toString();
		//根据id得到activity_table_name
		String activity_table_name=usertable.getActivity_table_name().toString();
		//根据id得到connection_table_name
		String connection_table_name=usertable.getConnection_table_name().toString();
		//根据id得到detail_table_name
		String detail_table_name=usertable.getDetaildata_table_name().toString();
		//根据id得到newplace_table_name
		String newplace_table_name=usertable.getNewplace_table_name().toString();
		//根据id得到dayscord_table_name
		String dayscord_table_name=usertable.getDayrecord_table_name().toString();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
