package newplace.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import newplace.dao.NewPlaceDao;

/**
 * Servlet implementation class NewPlaceController
 */
@WebServlet("/NewPlaceController")
public class NewPlaceController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewPlaceController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		NewPlaceDao npd = new NewPlaceDao();
//		npd.createTable(02);
//		npd.insert(01, 01, "dss", 1, 102, 120, 50);
		npd.insert(02, "ds",125, 120, 50);
//		npd.update(01, 01);
//		npd.update(01, 01);
//		npd.update(01, 02);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
