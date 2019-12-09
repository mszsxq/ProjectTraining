package com.example.catchtime.entity;

public class All_data {
    private int data_id;
    private String activity_name;
    private String activity_data;
    private String data;
    public All_data(int data_id, String activity_name, String activity_data, String data) {
        super();
        this.data_id = data_id;
        this.activity_name = activity_name;
        this.activity_data = activity_data;
        this.data = data;
    }
    public All_data() {}

    public int getData_id() {
        return data_id;
    }
    public void setData_id(int data_id) {
        this.data_id = data_id;
    }
    public String getActivity_name() {
        return activity_name;
    }
    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }
    public String getActivity_data() {
        return activity_data;
    }
    public void setActivity_data(String activity_data) {
        this.activity_data = activity_data;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "All_data{" +
                "data_id=" + data_id +
                ", activity_name='" + activity_name + '\'' +
                ", activity_data='" + activity_data + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}

