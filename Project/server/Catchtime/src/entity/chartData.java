package entity;

public class chartData {
    private String name;
    private int id;
    private String time;
    private String icon;
    private String color;

    public chartData(){}
    public chartData(String name, String time,String icon,String color,int id) {
        this.name = name;
        this.time = time;
        this.icon=icon;
        this.color=color;
        this.id=id;
    }

    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
