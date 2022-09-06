package com.corporacao.capsula.componentes.calendario.data;

public class Event {
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mQtd;
    private int mColor;

    public Event(int year, int month, int day){
        this.mYear = year;
        this.mMonth = month;
        this.mDay = day;
    }

    public Event(int year, int month, int day, int mColor){
        this.mYear = year;
        this.mMonth = month;
        this.mDay = day;
        this.mColor = mColor;
        this.mQtd = mQtd;
    }

    public Event(int year, int month, int day, int mQtd, int mColor){
        this.mYear = year;
        this.mMonth = month;
        this.mDay = day;
        this.mColor = mColor;
        this.mQtd = mQtd;
    }

    public int getMonth(){
        return mMonth;
    }

    public int getYear(){
        return mYear;
    }

    public int getDay(){
        return mDay;
    }

    public int getColor() {
        return mColor;
    }

    public int getQtd() {
        return mQtd;
    }

    public void setQtd(int aQtd) {
        this.mQtd = aQtd;
    }
}
