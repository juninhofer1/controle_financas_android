package com.corporacao.capsula.componentes.calendario.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.comporacao.capsula.domain.util.FormatUtil;

import java.util.Calendar;

public class Day implements Parcelable{

    private int mYear;
    private int mMonth;
    private int mDay;
    private int mQtdEv;

    public Day(int year, int month, int day){
        this.mYear = year;
        this.mMonth = month;
        this.mDay = day;
        mQtdEv = 0;
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



    public Day(Parcel in){
        int[] data = new int[3];
        in.readIntArray(data);
        this.mYear = data[0];
        this.mMonth = data[1];
        this.mYear = data[2];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(new int[] {this.mYear,
                this.mMonth,
                this.mDay});
    }
    public static final Creator CREATOR = new Creator() {
        public Day createFromParcel(Parcel in) {
            return new Day(in);
        }

        public Day[] newArray(int size) {
            return new Day[size];
        }
    };

    public int getQtdEv() {
        return mQtdEv;
    }

    public void setQtdEv(int aQtdEv) {
        this.mQtdEv = aQtdEv;
    }

    public Calendar getCalendar() {
        Calendar lCalendar = Calendar.getInstance();
        lCalendar.set(Calendar.DAY_OF_MONTH, mDay);
        lCalendar.set(Calendar.MONTH, mMonth);
        lCalendar.set(Calendar.YEAR, mYear);
        return lCalendar;
    }

    @Override
    public String toString() {
        return FormatUtil.dateFormat(getCalendar().getTime(), FormatUtil.dd_MM_yyyy);
    }
}
