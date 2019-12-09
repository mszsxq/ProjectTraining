package data.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ActivityController
 */
@WebServlet("/DataController")
public class DataController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String user_id = request.getParameter("id");
		Gson gson = new Gson();
		Type type=new TypeToken<User>(){}.getType();
		int id = gson.fromJson(user_id, type);
		//添加每项活动的总时间;
		List<All_data> datas = new ArrayList<All_data>();
		DataService dataService = new DataService();
		datas = dataService.InsertActivityTime(id);
		DataDao dataDao = new DataDao();
		for(int i = 0;i<datas.size();i++) {
			dataDao.addalldata(id,datas.get(i).getData_id(), datas.get(i).getData(),datas.get(i).getActivity_name(), datas.get(i).getActivity_data());
			System.out.println("添加成功"+i);
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
