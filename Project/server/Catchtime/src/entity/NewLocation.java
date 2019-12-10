package entity;

public class NewLocation {
	private int Default_Location_Id;
	private String Default_Location_Name;
	private int Number;
	private double Longitude;
	private double Latitude;
	private int Range;
	
	public NewLocation() {
		super();
	}

	public NewLocation(int default_Location_Id, String default_Location_Name, int number, double longitude,
			double latitude, int range) {
		super();
		Default_Location_Id = default_Location_Id;
		Default_Location_Name = default_Location_Name;
		Number = number;
		Longitude = longitude;
		Latitude = latitude;
		Range = range;
	}

	public int getDefault_Location_Id() {
		return Default_Location_Id;
	}

	public void setDefault_Location_Id(int default_Location_Id) {
		Default_Location_Id = default_Location_Id;
	}

	public String getDefault_Location_Name() {
		return Default_Location_Name;
	}

	public void setDefault_Location_Name(String default_Location_Name) {
		Default_Location_Name = default_Location_Name;
	}

	public int getNumber() {
		return Number;
	}

	public void setNumber(int number) {
		Number = number;
	}

	public double getLongitude() {
		return Longitude;
	}

	public void setLongitude(double longitude) {
		Longitude = longitude;
	}

	public double getLatitude() {
		return Latitude;
	}

	public void setLatitude(double latitude) {
		Latitude = latitude;
	}

	public int getRange() {
		return Range;
	}

	public void setRange(int range) {
		Range = range;
	}
	
}
