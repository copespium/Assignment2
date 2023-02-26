package com.example.assignment2;

public class Attempt {

    private String area, dateTime;
    private int point;

    public Attempt(){
    }

    public Attempt(String area, int point, String dateTime){
        this.area = area;
        this.point = point;
        this.dateTime = dateTime;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "Attempt{" +
                "area='" + area + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", point=" + point +
                '}';
    }
}
