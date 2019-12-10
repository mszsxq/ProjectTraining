package com.example.catchtime.entity;

public class Icon {

    private int iconId;
    private String iconPic;
    private String iconColor;


    public Icon() {
    }

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


    }

}
