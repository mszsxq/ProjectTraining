package entity;

public class Time {
	private String begin_time;
	private String finish_time;
	private int flog;
	public int getFlag() {
		return flog;
	}
	public void setFlag(int flog) {
		this.flog = flog;
	}
	public String getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}
	public String getFinish_time() {
		return finish_time;
	}
	public void setFinish_time(String finish_time) {
		this.finish_time = finish_time;
	}
	
}
