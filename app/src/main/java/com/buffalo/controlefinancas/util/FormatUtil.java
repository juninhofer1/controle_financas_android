package com.buffalo.controlefinancas.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtil {

    public static final String dd_MM_yyyy = "dd/MM/yyyy";

    public static String dateFormat(Date aDate, String aFormat){
        SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(aFormat);
        String lDataConvertida = lSimpleDateFormat.format(aDate);
        return lDataConvertida;
    }

    public static Date convertDate(String aData,  String aFormat){
        Date lDataConvertida = null;
        try {
            SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(aFormat);
            lDataConvertida = lSimpleDateFormat.parse(aData);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return lDataConvertida;
    }

}
