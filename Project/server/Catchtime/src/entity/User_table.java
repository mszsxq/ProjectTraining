package entity;

public class User_table {
	private int user_id;
	private String location_table_name;
	private String activity_table_name;
	private String connection_table_name;
	private String detaildata_table_name;
	private String newplace_table_name;
	private String dayrecord_table_name;
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}


	public String getLocation_table_name() {
		return location_table_name;
	}


	public void setLocation_table_name(String location_table_name) {
		this.location_table_name = location_table_name;
	}


	public String getActivity_table_name() {
		return activity_table_name;
	}


	public void setActivity_table_name(String activity_table_name) {
		this.activity_table_name = activity_table_name;
	}


	public String getConnection_table_name() {
		return connection_table_name;
	}


	public void setConnection_table_name(String connection_table_name) {
		this.connection_table_name = connection_table_name;
	}


	public String getDetaildata_table_name() {
		return detaildata_table_name;
	}


	public void setDetaildata_table_name(String detaildata_table_name) {
		this.detaildata_table_name = detaildata_table_name;
	}


	public String getNewplace_table_name() {
		return newplace_table_name;
	}


	public void setNewplace_table_name(String newplace_table_name) {
		this.newplace_table_name = newplace_table_name;
	}


	public String getDayrecord_table_name() {
		return dayrecord_table_name;
	}


	public void setDayrecord_table_name(String dayrecord_table_name) {
		this.dayrecord_table_name = dayrecord_table_name;
	}
	
	public User_table(int user_id, String location_table_name, String activity_table_name, String connection_table_name,
			String detaildata_table_name, String newplace_table_name, String dayrecord_table_name) {
		super();
		this.user_id = user_id;
		this.location_table_name = location_table_name;
		this.activity_table_name = activity_table_name;
		this.connection_table_name = connection_table_name;
		this.detaildata_table_name = detaildata_table_name;
		this.newplace_table_name = newplace_table_name;
		this.dayrecord_table_name = dayrecord_table_name;
	}
	public User_table() {
		
	}

}
