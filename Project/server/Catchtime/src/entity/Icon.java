package entity;

public class Icon {
	public int Icon_Id;
	public String Icon_address;
	public String Color;
	
	
	public Icon() {
		super();
	}
	public Icon(int icon_Id, String icon_address, String color) {
		super();
		Icon_Id = icon_Id;
		Icon_address = icon_address;
		Color = color;
	}
	public int getIcon_Id() {
		return Icon_Id;
	}
	public void setIcon_Id(int icon_Id) {
		Icon_Id = icon_Id;
	}
	public String getIcon_address() {
		return Icon_address;
	}
	public void setIcon_address(String icon_address) {
		Icon_address = icon_address;
	}
	public String getColor() {
		return Color;
	}
	public void setColor(String color) {
		Color = color;
	}
	
	
}
