package detail.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import activity.dao.ActivityDao;
import contact.dao.ContactDao;
import data.dao.DataDao;
import detail.dao.DetailDao;
import detail.service.DetailService;
import entity.Activity;
import entity.All_data;
import entity.Contact;
import entity.Time;

/**
 * Servlet implementation class DetailSaveContrallor
 */
@WebServlet("/DetailSaveContrallor")
public class DetailSaveContrallor extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String timenow;
	private int pos;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetailSaveContrallor() {
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
		String acid = request.getParameter("acid");
		String loid = request.getParameter("loid");
		String list = request.getParameter("timelist");
		String timelike = request.getParameter("time");
		
		Date dstart=null;
		Date dallstart=null;
		Date dend=null;
		Date dallend=null;
		
		
		//打开Dao
		ContactDao tDao = new ContactDao();
		DetailDao dDao = new DetailDao();
		DataDao aDao = new DataDao();
		ActivityDao acDao = new ActivityDao();
		DetailService ds = new DetailService();
		
		
		List<Time> timelistnew = new ArrayList<>();
		List<Time> timelistold = new ArrayList<>();
		Time newactime = new Time();
		Time oldactime = new Time();
		
		
		Type typenew=new TypeToken<List<Time>>(){}.getType();
        Gson gson=new Gson();
        System.out.println(list);
        System.out.println(acname);
		System.out.println(userid);
		System.out.println(timelike);
        if(list!=null) {
        	System.out.println("收到的返回newActivirt"+list);
        	timelistnew = gson.fromJson(list,typenew);
            for(int i=0;i<timelistnew.size();i++) {
            	if(timelistnew.get(i).getFlag()==1) {
            		newactime.setBegin_time(timelistnew.get(1).getBegin_time());
            		newactime.setFinish_time(timelistnew.get(i).getFinish_time());
            		pos = i;
            	}
            }
            
            
            //现在的时间
            DateFormat df= new SimpleDateFormat("yyyy-MM-dd");//对日期进行格式化
            timenow = df.format(new Date());
            
            //获取改变旧activity的时间
            String timeold = ds.findStartAndEnd(acname, Integer.parseInt(userid), timelike);
            System.out.println(timeold);
            Type typeold=new TypeToken<List<Time>>(){}.getType();
            timelistold = gson.fromJson(timeold,typeold);
            System.out.println("返回的其实时间"+timelistold.get(pos).getBegin_time());
            System.out.println("返回的结束时间"+timelistold.get(pos).getFinish_time());
            oldactime.setBegin_time(timelistold.get(pos).getBegin_time());
            oldactime.setFinish_time(timelistold.get(pos).getFinish_time());

            
            //转化data
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
    		try {
    			String ds1 = timelike+" "+newactime.getBegin_time()+":00";
    			System.out.println("newb"+ds1);
    			dstart = sdf.parse(timelike+" "+newactime.getBegin_time()+":00");

    			
    			System.out.println("oldb"+oldactime.getBegin_time());
    			dallstart = sdf.parse(oldactime.getBegin_time());
    			
    			String ds2 = timelike+" "+newactime.getFinish_time()+":00";
    			System.out.println("newf"+ds2);
    		    dend = sdf.parse(timelike+" "+newactime.getFinish_time()+":00");
    		    
    		    System.out.println("oldf"+oldactime.getFinish_time());
    		    dallend = sdf.parse(oldactime.getFinish_time());
    		} catch (ParseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		//判断改变旧activity是今天还是过去
    		if(timenow.equals(timelike)) {
            	//是今天
            	//更新今天新activity单条信息
            	
            	Contact ct = new Contact(Integer.parseInt(loid),Integer.parseInt(acid));
    			System.out.println("Detail:loid"+Integer.parseInt(loid));
    			System.out.println("Detail:acid"+Integer.parseInt(acid));
    			int detailnewid = dDao.findIdbyidandtime(Integer.parseInt(acid), timelike,Integer.parseInt(userid));
    			if(detailnewid>0) {
    				int i = dDao.upDateDetailbyDetailid(Integer.parseInt(userid),timelike+" "+newactime.getBegin_time()+":00", timelike+" "+newactime.getFinish_time()+":00", detailnewid);
    				System.out.println("修改当天新activity成功");
    			}else {
    				dDao.addDetail(Integer.parseInt(userid), Integer.parseInt(acid), Integer.parseInt(loid), timelike+" "+newactime.getBegin_time()+":00", timelike+" "+newactime.getFinish_time()+":00");
    				System.out.println("增加当天新activity成功");
    			}
    			//更新今天旧activity
    			if(dstart.before(dallstart) || dend.after(dallend)) {
    				System.out.println("非法输入");
    				out.write("非法输入");
    			}else {
    				//如果开始时间相同
    				if((dallstart.compareTo(dstart))==0){
                      //如果结束时间相同
                      if((dallend.compareTo(dend)==0)){
                    	  Time oldtime = new Time();
                    	  oldtime.setBegin_time(oldactime.getBegin_time());
                    	  oldtime.setFinish_time(oldactime.getBegin_time());
                    	  ds.updatetodaySimple(acname, Integer.parseInt(userid), timelike, oldtime);
                    	  out.write("修改成功");
                      }else{
                      //如果结束时间不同
                    	  Time oldtime = new Time();
                    	  oldtime.setBegin_time(timelike+" "+newactime.getFinish_time()+":00");
                    	  oldtime.setFinish_time(oldactime.getFinish_time());
                    	  ds.updatetodaySimple(acname, Integer.parseInt(userid), timelike, oldtime);
                    	  out.write("修改成功");
                      }
                  }else{
                  //如果开始时间不同，结束时间相同
                      if((dallend.compareTo(dend)==0)) {
                    	  Time oldtime = new Time();
                    	  oldtime.setBegin_time(oldactime.getBegin_time());
                    	  oldtime.setFinish_time(timelike+" "+newactime.getBegin_time()+":00");
                    	  ds.updatetodaySimple(acname, Integer.parseInt(userid), timelike, oldtime);
                    	  out.write("修改成功");
                      }else{
                       //如果开始时间不同，结束时间不同
                    	  Time oldtime1 = new Time();
                    	  Time oldtime2 = new Time();
                    	  oldtime1.setBegin_time(oldactime.getBegin_time());
                    	  oldtime1.setFinish_time(timelike+" "+newactime.getBegin_time()+":00");
                    	  oldtime2.setBegin_time(timelike+" "+newactime.getFinish_time()+":00");
                    	  oldtime2.setFinish_time(oldactime.getFinish_time());
                    	  ds.updatetodayMuch(acname, Integer.parseInt(userid), timelistold.get(pos).getBegin_time(), oldtime1,oldtime2);
                    	  out.write("修改成功");
                      }
                  }
    			}	
            }else {
            //不是今天
            	//更新以前新activity单条信息
            	
            	Contact ct = new Contact(Integer.parseInt(loid),Integer.parseInt(acid));
    			System.out.println("Detail:loid"+Integer.parseInt(loid));
    			System.out.println("Detail:acid"+Integer.parseInt(acid));
    			int detailnewid = dDao.findIdbyidandtime(Integer.parseInt(acid), timelike,Integer.parseInt(userid));
    			if(detailnewid>0) {
    				String table = userid+"_data";
    				int i = dDao.upDateDetailbyDetailid(Integer.parseInt(userid),timelike+" "+newactime.getBegin_time()+":00", timelike+" "+newactime.getFinish_time()+":00", detailnewid);
    				Activity acn = acDao.findSingle(Integer.parseInt(userid), Integer.parseInt(acid));
    				int did = aDao.findataid(table, acn.getActivity_name(), timelike);
    				long num1 = aDao.finacdata(table, acn.getActivity_name(), did);
    				long num2 = (dend.getTime()-dstart.getTime())/60000;
    				long num = num1+num2;
    				System.out.println("新添加的activity总时间时间"+num);
    				aDao.upDateNum(table,acn.getActivity_name(), timelike, String.valueOf(num));
    				System.out.println("修改以前新activity成功");
    			}else {
    				String table = userid+"_data";
    				dDao.addDetail(Integer.parseInt(userid), Integer.parseInt(acid), Integer.parseInt(loid), timelike+" "+newactime.getBegin_time()+":00", timelike+" "+newactime.getFinish_time()+":00");
    				Activity acn = acDao.findSingle(Integer.parseInt(userid), Integer.parseInt(acid));
    				long num = (dend.getTime()-dstart.getTime())/60000;
    				System.out.println("新添加的activity总时间时间"+num);
    				int ndid = aDao.findalldata(table);
    				aDao.addalldata(table, ndid+1, timelike, acn.getActivity_name(), String.valueOf(num));
    				System.out.println("增加以前新activity成功");
    			}
    			//更新今天旧activity
            	if(dstart.before(dallstart) || dend.after(dallend)) {
            		out.write("非法输入");
    				System.out.println("非法输入");
    			}else {
    				//如果开始时间相同
    				if((dallstart.compareTo(dstart))==0){
                      //如果结束时间相同
                      if((dallend.compareTo(dend)==0)){
                    	  String table = userid+"_data";
                    	  Time oldtime = new Time();
                    	  oldtime.setBegin_time(oldactime.getBegin_time());
                    	  oldtime.setFinish_time(oldactime.getBegin_time());
                    	  int did = aDao.findataid(table, acname, timelike);
                    	  long sum1 = aDao.finacdata(table,acname, did);
                    	  long sum2 = (dallend.getTime()-dallstart.getTime())/60000;
                    	  long sum = sum1-sum2;
                    	  System.out.println("新修改的旧activity时间"+sum);
                    	  ds.updatetodaySimple(acname, Integer.parseInt(userid), timelike, oldtime);
                    	  ds.updatebeforeSimple(Integer.parseInt(userid), acname, timelike, String.valueOf(sum));
						  out.write("修改成功");
                      }else{
                      //如果结束时间不同
                    	  String table = userid+"_data";
                    	  Time oldtime = new Time();
                    	  oldtime.setBegin_time(timelike+" "+newactime.getFinish_time()+":00");
                    	  oldtime.setFinish_time(oldactime.getFinish_time());
                    	  try {
							Date nos = sdf.parse(oldtime.getBegin_time());
							Date nof = sdf.parse(oldtime.getFinish_time());
							int did = aDao.findataid(table, acname, timelike);
							long sum1 = aDao.finacdata(table,acname, did);
							long sum2 = (nof.getTime()-nos.getTime())/60000;
							long sum = sum1-sum2;
							ds.updatebeforeSimple(Integer.parseInt(userid), acname, timelike, String.valueOf(sum));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    	  ds.updatetodaySimple(acname, Integer.parseInt(userid), timelike, oldtime);
						  out.write("修改成功");
                      }
                  }else{
                  //如果开始时间不同，结束时间相同
                      if((dallend.compareTo(dend)==0)) {
                    	  String table = userid+"_data";
                    	  Time oldtime = new Time();
                    	  oldtime.setBegin_time(oldactime.getBegin_time());
                    	  oldtime.setFinish_time(timelike+" "+newactime.getBegin_time()+":00");
                    	  try {
  							Date nos = sdf.parse(oldtime.getBegin_time());
  							Date nof = sdf.parse(oldtime.getFinish_time());
  							int did = aDao.findataid(table, acname, timelike);
  							long sum1 = aDao.finacdata(table,acname, did);
  							long sum2 = (nof.getTime()-nos.getTime())/600000;
  							long sum = sum1-sum2;
  							ds.updatebeforeSimple(Integer.parseInt(userid), acname, timelike, String.valueOf(sum));
							
                    	  }catch (ParseException e) {
  							// TODO Auto-generated catch block
  							e.printStackTrace();
                    	  }
                    	  ds.updatetodaySimple(acname, Integer.parseInt(userid), timelike, oldtime);
                    	  out.write("修改成功");
                      }else{
                       //如果开始时间不同，结束时间不同
                    	  String table = userid+"_data";
                    	  Time oldtime1 = new Time();
                    	  Time oldtime2 = new Time();
                    	  oldtime1.setBegin_time(oldactime.getBegin_time());
                    	  oldtime1.setFinish_time(timelike+" "+newactime.getBegin_time()+":00");
                    	  oldtime2.setBegin_time(timelike+" "+newactime.getFinish_time()+":00");
                    	  oldtime2.setFinish_time(oldactime.getFinish_time());
//                    	  try {
//    							Date nos1 = sdf.parse(oldtime1.getBegin_time());
//    							Date nof1 = sdf.parse(oldtime1.getFinish_time());
//    							Date nos2 = sdf.parse(oldtime2.getBegin_time());
//    							Date nof2 = sdf.parse(oldtime2.getFinish_time());
    							int did = aDao.findataid(table, acname, timelike);
    							long sum1 = aDao.finacdata(table,acname, did);
    							System.out.println("获取到的sum1"+sum1);
//    							long sum2 = (nof1.getTime()-nos1.getTime())/60000;
//    							System.out.println("新修改的旧activity时间222"+sum2);
    							long sum3 = (dend.getTime()-dstart.getTime())/60000;
    							System.out.println("新修改的旧activity时间333"+sum3);
    							long sum = sum1-sum3;
    							System.out.println("新修改的旧activity时间zzz"+sum);
    							ds.updatebeforeSimple(Integer.parseInt(userid), acname, timelike, String.valueOf(sum));
    							
//                      	  }catch (ParseException e) {
//    							// TODO Auto-generated catch block
//    							e.printStackTrace();
//                      	  }
                    	  ds.updatetodayMuch(acname, Integer.parseInt(userid), timelistold.get(pos).getBegin_time(), oldtime1,oldtime2);
                    	  out.write("修改成功");
                      }
                  }
    			}	
            	
            }
        }
       
//		//更新以前单条数据
//		else if(newtime!=null) {
//			try {
//				tDao.addContact(ct, Integer.parseInt(userid));
//			} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			int detailid = dDao.findIdbyidandtime(Integer.parseInt(acid), timelike,Integer.parseInt(userid));
//			if(detailid>0) {
//				String table = userid+"_data";
//				int i = dDao.upDateDetailbyDetailid(Integer.parseInt(userid),tnewtime.getBegin_time(), tnewtime.getFinish_time(), detailid);
//				Activity acn = acDao.findSingle(Integer.parseInt(userid), Integer.parseInt(acid));
//				int did = aDao.findataid(table, acn.getActivity_name(), timelike);
//				int j = aDao.upDateNum(table, acn.getActivity_name(), timelike, num);
//				out.write("修改以前新activity成功");
//			}else {
//				dDao.addDetail(Integer.parseInt(userid), Integer.parseInt(acid), Integer.parseInt(loid), tnewtime.getBegin_time(), tnewtime.getFinish_time());
//				String table = userid+"_data";
//				int iddata = aDao.findalldata(table);
//				Activity acn = acDao.findSingle(Integer.parseInt(userid), Integer.parseInt(acid));
//				aDao.addalldata(table,iddata+1,timelike,acn.getActivity_name(),num);
//				out.write("添加以前新activity成功");
//			}
//		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}