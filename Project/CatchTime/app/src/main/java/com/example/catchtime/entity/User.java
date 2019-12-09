package com.example.catchtime.entity;

public class User {
    private int user_id;
    private String phone;
    private String password;
    private String username;
    private String register_date;
    private String moto;
    private String image;

    public User() {

    }



    public User(String phone, String password) {
        super();
        this.phone = phone;
        this.password = password;
    }
    public User(int user_id, String phone, String password, String username, String register_date, String moto,
                String image) {
        super();
        this.user_id = user_id;
        this.phone = phone;
        this.password = password;
        this.username = username;
        this.register_date = register_date;
        this.moto = moto;
        this.image = image;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getRegister_date() {
        return register_date;
    }
    public void setRegister_date(String register_date) {
        this.register_date = register_date;
    }
    public String getMoto() {
        return moto;
    }
    public void setMoto(String moto) {
        this.moto = moto;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}
