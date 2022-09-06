package com.buffalo.controlefinancas.util.calendario.data;

import android.graphics.Color;

import com.buffalo.controlefinancas.R;

public class Event {
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mQtd;
    private int mColor = Color.WHITE;

    public Event(int year, int month, int day){
        this.mYear = year;
        this.mMonth = month;
        this.mDay = day;
        this.mColor = Color.WHITE;
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
