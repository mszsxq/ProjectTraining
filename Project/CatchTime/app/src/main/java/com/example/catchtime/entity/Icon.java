package com.example.catchtime.entity;

public class Icon {
<<<<<<< HEAD
    private int iconId;
    private String iconPic;
    private String iconColor;
=======
    public int Icon_Id;
    public String Icon_address;
    public String Color;
>>>>>>> 850fe2f302fea9bb2c73c578e12c8222cacc877d

    public Icon() {
    }

<<<<<<< HEAD
    public Icon(int iconId, String iconPic, String iconColor) {
        this.iconId = iconId;
        this.iconPic = iconPic;
        this.iconColor = iconColor;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getIconPic() {
        return iconPic;
    }

    public void setIconPic(String iconPic) {
        this.iconPic = iconPic;
    }

    public String getIconColor() {
        return iconColor;
    }

    public void setIconColor(String iconColor) {
        this.iconColor = iconColor;
    }

    @Override
    public String toString() {
        return "Icon{" +
                "iconId=" + iconId +
                ", iconPic='" + iconPic + '\'' +
                ", iconColor='" + iconColor + '\'' +
                '}';
=======
    public Icon(int icon_Id, String icon_address, String color) {
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
>>>>>>> 850fe2f302fea9bb2c73c578e12c8222cacc877d
    }
}
