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
}
