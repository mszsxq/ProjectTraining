package detail.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import activity.dao.ActivityDao;
import contact.dao.ContactDao;
import data.dao.DataDao;
import detail.dao.DetailDao;
import entity.Activity;
import entity.Time;

public class DetailService {
	public String findStartAndEnd(String acname,int userid,String time) {
		ActivityDao dao = new ActivityDao();
		List<Time> listtime = new ArrayList<>();
		Activity ac = dao.findSingleforname(userid, acname);
		DetailDao dao2 = new DetailDao();
		listtime = dao2.findTimeListbyidandtime(ac.getActivity_id(), time, userid);
		Gson gson = new Gson();
		String timeac = gson.toJson(listtime);
		return timeac;
	}
	public void updatetodaySimple(String acname,int userid,String time,Time timeold) {
		ActivityDao dao = new ActivityDao();
		Activity ac = dao.findSingleforname(userid, acname);
		DetailDao dDao = new DetailDao();
		int detailid = dDao.findIdbyidandtime(ac.getActivity_id(), time,userid);
		//����detail��
		dDao.upDateDetailbyDetailid(userid, timeold.getBegin_time(), timeold.getFinish_time(), detailid);
		System.out.println("�޸Ľ��վ�activity�ɹ�");
	}
	public void updatetodayMuch(String acname,int userid,String time,Time timeold1,Time timeold2) {
		ActivityDao dao = new ActivityDao();
		Activity ac = dao.findSingleforname(userid, acname);
		DetailDao dDao = new DetailDao();
		ContactDao cDao = new ContactDao();
		int detailid = dDao.findIdbyidandextime(ac.getActivity_id(), time,userid);
		int loid;
		try {
			loid = cDao.queryContactLocation(ac.getActivity_id(), userid);
			//����detail��
			System.out.println("timeold.finish"+timeold1.getFinish_time());
			dDao.upDateDetailbyDetailid(userid, timeold1.getBegin_time(), timeold1.getFinish_time(), detailid);
			dDao.addDetail(userid,ac.getActivity_id(),loid, timeold2.getBegin_time(), timeold2.getFinish_time());
			System.out.println("���½��ն����ɳɹ�");
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void updatebeforeSimple(int userid,String acname,String time,String sum) {
		ActivityDao dao = new ActivityDao();
		Activity ac = dao.findSingleforname(userid, acname);
		DetailDao dDao = new DetailDao();
//		int detailid = dDao.findIdbyidandtime(ac.getActivity_id(), timelike,Integer.parseInt(userid));
//		//����detail��
//		dDao.upDateDetailbyDetailid(Integer.parseInt(userid), timefinnal.getBegin_time(), timefinnal.getFinish_time(), detailid);
		//����data��
		DataDao ddDao = new DataDao();
		String tablename = userid+"_data";
		int dataid = ddDao.findataid(tablename, acname, time);
		ddDao.upDateNum(tablename,acname, time, sum);
		System.out.println("������ǰ��activity�ɹ�");
	}
	public String detailActivity(int id,int activity_id) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DetailDao detailDao = new DetailDao();
		String activity_data = null;
		List<Time> time = new ArrayList<>();
		List<Long> times = new ArrayList<>();
		time = detailDao.findDetails(id,activity_id);
		Long timeall=(long) 0;
		for(int i = 0;i<time.size();i++) {
			String finish = time.get(i).getFinish_time();
			String begin = time.get(i).getBegin_time();
			try {
				Date date1 = df.parse(finish);
				Date date2 = df.parse(begin);
				times.add((date1.getTime()-date2.getTime())/60000);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		for(int j = 0;j<times.size();j++) {
			timeall = timeall+times.get(j);
		}
		activity_data = timeall+"";
		return activity_data;
	}
	public List<String> markActivity() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DetailDao detailDao = new DetailDao();
		String activity_data = null;
		List<Time> marktime = new ArrayList<>();
		List<String> times = new ArrayList<>();
		marktime = detailDao.findDetail();
		for(int i = 0;i<marktime.size();i++) {
			String finish = marktime.get(i).getFinish_time();
			String begin1 = marktime.get(i).getBegin_time();
			int j = i+1;
			String begin = null;
			if(j==marktime.size()) {
				begin = df.format(new Date());
			}else {
				begin = marktime.get(j).getBegin_time();
			}
			try {
				Date date1 = df.parse(finish);
				Date date2 = df.parse(begin);
				times.add((date2.getTime()-date1.getTime())/60000+"");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return times;
	}
}
