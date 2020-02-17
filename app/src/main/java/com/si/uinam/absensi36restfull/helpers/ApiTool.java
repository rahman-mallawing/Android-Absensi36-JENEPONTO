package com.si.uinam.absensi36restfull.helpers;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ApiTool {

    public static int RED_DARK = Color.rgb(255, 87, 51);
    public static int RED_LIGHTCORAL = Color.rgb(240, 128, 128);
    public static int RED_INDIANRED = Color.rgb(205, 92, 92);
    public static int MAROON = Color.rgb(128, 0, 0);
    public static int OLIVE = Color.rgb(128, 128, 0);
    public static int GREEN_SMOOTH = Color.rgb(137, 197, 107);
    public static int BLUE_SMOOTH = Color.rgb(47, 161, 237);
    public static int YELLOW = Color.rgb(255, 211, 5);
    public static int YELLOW_CUTI = Color.rgb(255, 104, 1);
    public static int BROWN = Color.rgb(176, 100, 51);

    public static String getTodayDateString(Context context) {
        if(ApiHelper.isTanggal(context)){
            String msg = "Peringatan: Tanggal bukan waktu real";
            Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            View vi = toast.getView();
            vi.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            TextView text = vi.findViewById(android.R.id.message);
            toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
            text.setTextColor(Color.WHITE);
            //text.setHeight(100dp);
            toast.show();
            return ApiHelper.getTanggal(context);
        }
        String pattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(pattern);
        String tgl = df.format(new Date());
        return tgl;
    }

    public static void runLogin() {
        String pattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(pattern);
        String tgl = df.format(new Date());
    }

    public static int getTakColor() {
        return RED_INDIANRED;
    }

    public static int getHadirColor() {
        return GREEN_SMOOTH;
    }

    public static int getDinasColor() {
        return BLUE_SMOOTH;
    }

    public static int getCutiColor() {
        return YELLOW_CUTI;
    }

    public static int getIzinColor() {
        return OLIVE;
    }

    public static int getSakitColor() {
        return BROWN;
    }

}
