package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.Month;

public class DateUtil {
    public static LocalDate of(int year, Month month){
        return LocalDate.of(year,month,1);
    }
    public static LocalDate parse(String date) {
        String[] dates = date.split("-");
        return LocalDate.of(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2]));
    }
}
