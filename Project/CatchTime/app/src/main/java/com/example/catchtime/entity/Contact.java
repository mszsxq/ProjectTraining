package com.example.catchtime.entity;

public class Contact {
    private int Location_Id;
    private int Activity_Id;


    public Contact() {
        super();
    }
    public Contact(int location_Id, int activity_Id) {
        super();
        Location_Id = location_Id;
        Activity_Id = activity_Id;
    }
    public int getLocation_Id() {
        return Location_Id;
    }
    public void setLocation_Id(int location_Id) {
        Location_Id = location_Id;
    }
    public int getActivity_Id() {
        return Activity_Id;
    }
    public void setActivity_Id(int activity_Id) {
        Activity_Id = activity_Id;
    }
}
