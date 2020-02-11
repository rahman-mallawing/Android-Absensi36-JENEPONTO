package com.si.uinam.absensi36restfull.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ApiTool {
    public static String getTodayDateString() {
        String pattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(pattern);
        String tgl = df.format(new Date());
        return tgl;
    }
}
