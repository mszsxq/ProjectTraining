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
		
		
		//闁瑰灚鎸哥槐鎱廰o
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
        	System.out.println("闁猴拷鐠哄搫鐓傞柣銊ュ缁绘垿宕堕悗姊晈Activirt"+list);
        	timelistnew = gson.fromJson(list,typenew);
            for(int i=0;i<timelistnew.size();i++) {
            	if(timelistnew.get(i).getFlag()==1) {
            		newactime.setBegin_time(timelistnew.get(i).getBegin_time());
            		newactime.setFinish_time(timelistnew.get(i).getFinish_time());
            		pos = i;
            	}
            }
            
            
            //闁绘粍婢樺﹢顏堟儍閸曨剚顦ч梻鍌︽嫹
            DateFormat df= new SimpleDateFormat("yyyy-MM-dd");//閻庝絻顫夊Λ鈺呭嫉閻旇崵绠婚悶娑樻湰閻楃顕ｈ箛鎾愁嚙
            timenow = df.format(new Date());
            
            //闁兼儳鍢茶ぐ鍥绩閻熸澘缍侀柡鍐炬供ctivity闁汇劌瀚鍌炴⒒閿燂拷
            String timeold = ds.findStartAndEnd(acname, Integer.parseInt(userid), timelike);
            System.out.println(timeold);
            Type typeold=new TypeToken<List<Time>>(){}.getType();
            timelistold = gson.fromJson(timeold,typeold);
            System.out.println("閺夆晜鏌ㄥú鏍儍閸曨偄寰撻悗鍦仦濡炲倿姊婚敓锟�"+timelistold.get(pos).getBegin_time());
            System.out.println("閺夆晜鏌ㄥú鏍儍閸曨厾娉㈤柡澶屽枑濡炲倿姊婚敓锟�"+timelistold.get(pos).getFinish_time());
            oldactime.setBegin_time(timelistold.get(pos).getBegin_time());
            oldactime.setFinish_time(timelistold.get(pos).getFinish_time());

            
            //閺夌儐鍓欑�电灂ata
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
    		//闁告帇鍊栭弻鍥绩閻熸澘缍侀柡鍐炬供ctivity闁哄嫷鍨划鏍ㄥ緞閳哄嫮绠烽柡鍕靛灥缁诲啴宕㈤敓锟�
    		if(timenow.equals(timelike)) {
            	//闁哄嫷鍨划鏍ㄥ緞閿燂拷
            	//闁哄洤鐡ㄩ弻濠冪婵犲倶浜柡鍌滄珮ctivity闁告娲樺顖涚┍閳╁啩绱�
            	
            	Contact ct = new Contact(Integer.parseInt(loid),Integer.parseInt(acid));
            	try {
					tDao.addContact(ct, Integer.parseInt(userid));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			System.out.println("Detail:loid"+Integer.parseInt(loid));
    			System.out.println("Detail:acid"+Integer.parseInt(acid));
    			int detailnewid = dDao.findIdbyidandtime(Integer.parseInt(acid), timelike,Integer.parseInt(userid));
    			if(detailnewid>0) {
    				int i = dDao.upDateDetailbyDetailid(Integer.parseInt(userid),timelike+" "+newactime.getBegin_time()+":00", timelike+" "+newactime.getFinish_time()+":00", detailnewid);
    				System.out.println("濞ｅ浂鍠楅弫鑹般亹閹剧偨浜柡鍌滄珮ctivity闁瑰瓨鍔曟慨锟�");
    			}else {
    				dDao.addDetail(Integer.parseInt(userid), Integer.parseInt(acid), Integer.parseInt(loid), timelike+" "+newactime.getBegin_time()+":00", timelike+" "+newactime.getFinish_time()+":00");
    				System.out.println("濠⒀呭仜婵偠銇愰幘鐐戒函闁哄倻娅榗tivity闁瑰瓨鍔曟慨锟�");
    			}
    			//闁哄洤鐡ㄩ弻濠冪婵犲倶浜柡鍐炬供ctivity
    			if(dstart.before(dallstart) || dend.after(dallend)) {
    				System.out.println("闂傚牏鍋炵涵鑸垫綇閹惧啿寮�");
    				out.write("0");
    			}else {
    				//濠碘�冲�归悘澶婎嚕閿熻姤鎱ㄧ�ｎ偅顦ч梻鍌氼嚟濞村宕ラ敓锟�
    				if((dallstart.compareTo(dstart))==0){
                      //濠碘�冲�归悘澶岀磼閹惧瓨灏嗛柡鍐ㄧ埣濡潡鎯勭粙鎸庡��
                      if((dallend.compareTo(dend)==0)){
                    	  Time oldtime = new Time();
                    	  oldtime.setBegin_time(oldactime.getBegin_time());
                    	  oldtime.setFinish_time(oldactime.getBegin_time());
                    	  ds.updatetodaySimple(acname, Integer.parseInt(userid), timelike, oldtime);
                    	  out.write("1");
                      }else{
                      //濠碘�冲�归悘澶岀磼閹惧瓨灏嗛柡鍐ㄧ埣濡寧绋夊鍛��
                    	  Time oldtime = new Time();
                    	  oldtime.setBegin_time(timelike+" "+newactime.getFinish_time()+":00");
                    	  oldtime.setFinish_time(oldactime.getFinish_time());
                    	  ds.updatetodaySimple(acname, Integer.parseInt(userid), timelike, oldtime);
                    	  out.write("1");
                      }
                  }else{
                  //濠碘�冲�归悘澶婎嚕閿熻姤鎱ㄧ�ｎ偅顦ч梻鍌滅節缁楀宕ュ畝瀣缂備焦鎸诲顐﹀籍閸洘锛熼柣鈺冾焾閹拷
                      if((dallend.compareTo(dend)==0)) {
                    	  Time oldtime = new Time();
                    	  oldtime.setBegin_time(oldactime.getBegin_time());
                    	  oldtime.setFinish_time(timelike+" "+newactime.getBegin_time()+":00");
                    	  ds.updatetodaySimple(acname, Integer.parseInt(userid), timelike, oldtime);
                    	  out.write("1");
                      }else{
                       //濠碘�冲�归悘澶婎嚕閿熻姤鎱ㄧ�ｎ偅顦ч梻鍌滅節缁楀宕ュ畝瀣缂備焦鎸诲顐﹀籍閸洘锛熷☉鎾崇Т閹拷
                    	  Time oldtime1 = new Time();
                    	  Time oldtime2 = new Time();
                    	  oldtime1.setBegin_time(oldactime.getBegin_time());
                    	  oldtime1.setFinish_time(timelike+" "+newactime.getBegin_time()+":00");
                    	  oldtime2.setBegin_time(timelike+" "+newactime.getFinish_time()+":00");
                    	  oldtime2.setFinish_time(oldactime.getFinish_time());
                    	  ds.updatetodayMuch(acname, Integer.parseInt(userid), timelistold.get(pos).getBegin_time(), oldtime1,oldtime2);
                    	  out.write("1");
                      }
                  }
    			}	
            }else {
            //濞戞挸绉靛Σ鍛婄婵犲倶浜�
            	//闁哄洤鐡ㄩ弻濠冪閵夈儱顤呴柡鍌滄珮ctivity闁告娲樺顖涚┍閳╁啩绱�
            	
            	Contact ct = new Contact(Integer.parseInt(loid),Integer.parseInt(acid));
            	try {
					tDao.addContact(ct, Integer.parseInt(userid));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
    				System.out.println("闁哄倻澧楅崸濠囧礉閻樺灚鐣盿ctivity闁诡剛绮鍌炴⒒鐎涙ɑ顦ч梻鍌︽嫹"+num);
    				aDao.upDateNum(table,acn.getActivity_name(), timelike, String.valueOf(num));
    				System.out.println("濞ｅ浂鍠楅弫鍏肩閵夈儱顤呴柡鍌滄珮ctivity闁瑰瓨鍔曟慨锟�");
    			}else {
    				String table = userid+"_data";
    				dDao.addDetail(Integer.parseInt(userid), Integer.parseInt(acid), Integer.parseInt(loid), timelike+" "+newactime.getBegin_time()+":00", timelike+" "+newactime.getFinish_time()+":00");
    				Activity acn = acDao.findSingle(Integer.parseInt(userid), Integer.parseInt(acid));
    				long num = (dend.getTime()-dstart.getTime())/60000;
    				System.out.println("闁哄倻澧楅崸濠囧礉閻樺灚鐣盿ctivity闁诡剛绮鍌炴⒒鐎涙ɑ顦ч梻鍌︽嫹"+num);
    				int ndid = aDao.findalldata(table);
    				aDao.addalldata1(table, ndid+1, timelike, acn.getActivity_name(), String.valueOf(num));
    				System.out.println("濠⒀呭仜婵偞绂掗妷銉ヮ枀闁哄倻娅榗tivity闁瑰瓨鍔曟慨锟�");
    			}
    			//闁哄洤鐡ㄩ弻濠冪婵犲倶浜柡鍐炬供ctivity
            	if(dstart.before(dallstart) || dend.after(dallend)) {
            		out.write("0");
    				System.out.println("闂傚牏鍋炵涵鑸垫綇閹惧啿寮�");
    			}else {
    				//濠碘�冲�归悘澶婎嚕閿熻姤鎱ㄧ�ｎ偅顦ч梻鍌氼嚟濞村宕ラ敓锟�
    				if((dallstart.compareTo(dstart))==0){
                      //濠碘�冲�归悘澶岀磼閹惧瓨灏嗛柡鍐ㄧ埣濡潡鎯勭粙鎸庡��
                      if((dallend.compareTo(dend)==0)){
                    	  String table = userid+"_data";
                    	  Time oldtime = new Time();
                    	  oldtime.setBegin_time(oldactime.getBegin_time());
                    	  oldtime.setFinish_time(oldactime.getBegin_time());
                    	  int did = aDao.findataid(table, acname, timelike);
                    	  long sum1 = aDao.finacdata(table,acname, did);
                    	  long sum2 = (dallend.getTime()-dallstart.getTime())/60000;
                    	  long sum = sum1-sum2;
                    	  System.out.println("闁哄倿顣﹂幈銊╁绩閸︻厽鐣遍柡鍐炬供ctivity闁哄啫鐖煎Λ锟�"+sum);
                    	  ds.updatetodaySimple(acname, Integer.parseInt(userid), timelike, oldtime);
                    	  ds.updatebeforeSimple(Integer.parseInt(userid), acname, timelike, String.valueOf(sum));
						  out.write("1");
                      }else{
                      //濠碘�冲�归悘澶岀磼閹惧瓨灏嗛柡鍐ㄧ埣濡寧绋夊鍛��
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
						  out.write("1");
                      }
                  }else{
                  //濠碘�冲�归悘澶婎嚕閿熻姤鎱ㄧ�ｎ偅顦ч梻鍌滅節缁楀宕ュ畝瀣缂備焦鎸诲顐﹀籍閸洘锛熼柣鈺冾焾閹拷
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
                    	  out.write("1");
                      }else{
                       //濠碘�冲�归悘澶婎嚕閿熻姤鎱ㄧ�ｎ偅顦ч梻鍌滅節缁楀宕ュ畝瀣缂備焦鎸诲顐﹀籍閸洘锛熷☉鎾崇Т閹拷
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
    							System.out.println("闁兼儳鍢茶ぐ鍥礆閹殿喗鐣眘um1"+sum1);
//    							long sum2 = (nof1.getTime()-nos1.getTime())/60000;
//    							System.out.println("闁哄倿顣﹂幈銊╁绩閸︻厽鐣遍柡鍐炬供ctivity闁哄啫鐖煎Λ锟�222"+sum2);
    							long sum3 = (dend.getTime()-dstart.getTime())/60000;
    							System.out.println("闁哄倿顣﹂幈銊╁绩閸︻厽鐣遍柡鍐炬供ctivity闁哄啫鐖煎Λ锟�333"+sum3);
    							long sum = sum1-sum3;
    							System.out.println("闁哄倿顣﹂幈銊╁绩閸︻厽鐣遍柡鍐炬供ctivity闁哄啫鐖煎Λ绺祕z"+sum);
    							ds.updatebeforeSimple(Integer.parseInt(userid), acname, timelike, String.valueOf(sum));
    							
//                      	  }catch (ParseException e) {
//    							// TODO Auto-generated catch block
//    							e.printStackTrace();
//                      	  }
                    	  ds.updatetodayMuch(acname, Integer.parseInt(userid), timelistold.get(pos).getBegin_time(), oldtime1,oldtime2);
                    	  out.write("1");
                      }
                  }
    			}	
            	
            }
        }
       
//		//闁哄洤鐡ㄩ弻濠冪閵夈儱顤呴柛妤佹礃濞碱垶寮悧鍫濈ウ
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
//				out.write("濞ｅ浂鍠楅弫鍏肩閵夈儱顤呴柡鍌滄珮ctivity闁瑰瓨鍔曟慨锟�");
//			}else {
//				dDao.addDetail(Integer.parseInt(userid), Integer.parseInt(acid), Integer.parseInt(loid), tnewtime.getBegin_time(), tnewtime.getFinish_time());
//				String table = userid+"_data";
//				int iddata = aDao.findalldata(table);
//				Activity acn = acDao.findSingle(Integer.parseInt(userid), Integer.parseInt(acid));
//				aDao.addalldata(table,iddata+1,timelike,acn.getActivity_name(),num);
//				out.write("婵烇綀顕ф慨鐐寸閵夈儱顤呴柡鍌滄珮ctivity闁瑰瓨鍔曟慨锟�");
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
