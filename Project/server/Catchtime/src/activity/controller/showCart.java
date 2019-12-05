package activity.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import activity.dao.ActivityDao;
import entity.Activity;
import entity.chartData;
import user.dao.UserDao;

/**
 * Servlet implementation class showCart
 */
@WebServlet("/showCart")
public class showCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public showCart() {
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
		int position = Integer.valueOf(request.getParameter("position"));
		int size = Integer.valueOf(request.getParameter("size"));
		int user_id = Integer.valueOf(request.getParameter("user_id"));
		int type = Integer.valueOf(request.getParameter("type"));
		System.out.println(position+"-"+size);
		ActivityDao activityDao = new ActivityDao();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		String date =null;
		HashMap<Integer,Long> TodayhashList= new HashMap<Integer,Long>();
		HashMap<String,String> hashList= new HashMap<String,String>();
		List<chartData> cdl = new ArrayList<>();
		if(type==0) {
			if(position==size-1) {
				date = sd.format(new Date());
					try {
						TodayhashList= new HashMap<Integer,Long>();
						TodayhashList=activityDao.findDetailData(user_id,date);
						System.out.println("hashList"+TodayhashList.size());
						Set set2=TodayhashList.entrySet();
						Iterator it2=set2.iterator();
					     while(it2.hasNext()){
					            Map.Entry me=(Map.Entry)it2.next();
					            chartData cd = new chartData();
					            if((int)me.getKey()!=404) {
					            String name = activityDao.findActivityName(user_id, (int)me.getKey());
								cd.setName(name);
								cd.setId((int)me.getKey());
					            cd.setTime((String) activityDao.fomat((long) me.getValue()));
					            System.out.println("cdaa"+cd.getTime());
					            Activity activity = activityDao.findSingle(user_id,(int) me.getKey());
						        cd.setIcon(activity.getIcon_name());
						        cd.setColor(activity.getIcon_color());
					            }else {
					            	cd.setName("?");
									cd.setId(404);
						            cd.setTime((String) activityDao.fomat((long) me.getValue()));
					            	cd.setIcon("flower");
						            cd.setColor("dividing_line");
					            }
					            
					            cdl.add(cd);
					            System.out.println(me.getKey()+"-"+me.getValue());
					      }
					
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 
				     
			}else {
				 hashList= new HashMap<String,String>();
				 Calendar calendar = new GregorianCalendar();
	             calendar.setTime(new Date());
	             calendar.add(calendar.DATE,-(size-position-1));
	             date= sd.format(calendar.getTime());
	             System.out.println("posi"+position+"-"+date);
	             hashList=activityDao.findDayRecord(user_id,date);
	             System.out.println("hashList"+hashList.size());
	             Set set=hashList.entrySet();
	    	     Iterator it=set.iterator();
	    	     while(it.hasNext()){
	    	            Map.Entry me=(Map.Entry)it.next();
	    	            chartData cd = new chartData();
	    	            String a = (String) me.getKey();
	    	            if(!a.equals("404")) {
	    	            	int name=activityDao.findActivityId(user_id,a);
	    	            	cd.setName(a);
	    	            	cd.setId(name);
	    	            	cd.setTime((String) activityDao.fomat((long) Integer.parseInt((String) me.getValue())));
	    	            	System.out.println("cd"+cd.getTime());
	    	            	Activity activity = activityDao.findSingle(user_id,name);
	    	            	cd.setIcon(activity.getIcon_name());
	    	            	cd.setColor(activity.getIcon_color());
	    	            }else {
	    	            	cd.setName("?");
	    	            	cd.setId(404);
	    	            	cd.setTime((String) me.getValue());
	    	            	cd.setIcon("flower");
	    		            cd.setColor("dividing_line");
	    	            }
	    	            
	    	            cdl.add(cd);
	    	            System.out.println(me.getKey()+"-"+me.getValue());
	    	      }
			}
		}else if(type ==1) {
			String theDate = request.getParameter("date");
			HashMap<String,Integer> weekHashList = activityDao.findWeekRecord(user_id, theDate);
			Set set2=weekHashList.entrySet();
			Iterator it2=set2.iterator();
		     while(it2.hasNext()){
		            Map.Entry me=(Map.Entry)it2.next();
		            chartData cd = new chartData();
		            if((int)me.getKey()!=404) {
		            int id=activityDao.findActivityId(user_id,(String) me.getKey());
					cd.setName((String) me.getKey());
					cd.setId(id);
		            cd.setTime((String) activityDao.fomat((long) Integer.parseInt((String) me.getValue())));
		            Activity activity = activityDao.findSingle(user_id,(int) me.getKey());
			        cd.setIcon(activity.getIcon_name());
			        cd.setColor(activity.getIcon_color());
		            }else {
		            	cd.setName("?");
						cd.setId(404);
			            cd.setTime((String) activityDao.fomat((long) me.getValue()));
		            	cd.setIcon("flower");
			            cd.setColor("dividing_line");
		            }
		            cdl.add(cd);
		            System.out.println(me.getKey()+"-"+me.getValue());
		      }
		}else if(type ==2) {
			String theDate = request.getParameter("date");
			HashMap<String,Integer> monthHashList =activityDao.findMonthRecord(user_id, theDate);
			Set set2=monthHashList.entrySet();
			Iterator it2=set2.iterator();
		     while(it2.hasNext()){
		            Map.Entry me=(Map.Entry)it2.next();
		            chartData cd = new chartData();
		            if((int)me.getKey()!=404) {
		            int id=activityDao.findActivityId(user_id,(String) me.getKey());
					cd.setName((String) me.getKey());
					cd.setId(id);
		            cd.setTime((String) activityDao.fomat((long) Integer.parseInt((String) me.getValue())));
		            Activity activity = activityDao.findSingle(user_id,(int) me.getKey());
			        cd.setIcon(activity.getIcon_name());
			        cd.setColor(activity.getIcon_color());
		            }else {
		            	cd.setName("?");
						cd.setId(404);
			            cd.setTime((String) activityDao.fomat((long) me.getValue()));
		            	cd.setIcon("flower");
			            cd.setColor("dividing_line");
		            }
		            cdl.add(cd);
		            System.out.println(me.getKey()+"-"+me.getValue());
		      }
			
		}else {
			String theDate = request.getParameter("date");
			HashMap<String,Integer> yearHashList =activityDao.findYearRecord(user_id, theDate);
			Set set2=yearHashList.entrySet();
			Iterator it2=set2.iterator();
		     while(it2.hasNext()){
		            Map.Entry me=(Map.Entry)it2.next();
		            chartData cd = new chartData();
		            if((int)me.getKey()!=404) {
		            int id=activityDao.findActivityId(user_id,(String) me.getKey());
					cd.setName((String) me.getKey());
					cd.setId(id);
		            cd.setTime((String) activityDao.fomat((long) Integer.parseInt((String) me.getValue())));
		            Activity activity = activityDao.findSingle(user_id,(int) me.getKey());
			        cd.setIcon(activity.getIcon_name());
			        cd.setColor(activity.getIcon_color());
		            }else {
		            	cd.setName("?");
						cd.setId(404);
			            cd.setTime((String) activityDao.fomat((long) me.getValue()));
		            	cd.setIcon("flower");
			            cd.setColor("dividing_line");
		            }
		            cdl.add(cd);
		            System.out.println(me.getKey()+"-"+me.getValue());
		      }
			
		}
		
		System.out.println(cdl.toString());
	     Gson gson = new Gson();
	     String str = gson.toJson(cdl);
	     out.write(str);
			
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
