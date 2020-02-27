package com.si.uinam.absensi36restfull.views.monthpresence;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.si.uinam.absensi36restfull.R;
import com.si.uinam.absensi36restfull.helpers.ApiHelper;
import com.si.uinam.absensi36restfull.helpers.ApiTool;
import com.si.uinam.absensi36restfull.models.HarianGroupModel;
import com.si.uinam.absensi36restfull.models.MonthPresenceModel;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MonthPresenceListAdapter extends RecyclerView.Adapter<MonthPresenceListAdapter.MonthPresenceViewHolder> {

    private ArrayList<MonthPresenceModel> monthPresenceModels = new ArrayList<>();
    private OnItemClickCallback itemClickCallback;

    public void setMonthPresenceModels(ArrayList<MonthPresenceModel> monthPresenceModels) {
        this.monthPresenceModels.clear();
        this.monthPresenceModels.addAll(monthPresenceModels);
        notifyDataSetChanged();
    }

    public void setItemClickCallback(OnItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    @NonNull
    @Override
    public MonthPresenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_month_presence,parent,false);
        return new MonthPresenceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthPresenceViewHolder holder, int position) {
        holder.bind(monthPresenceModels.get(position));
    }

    @Override
    public int getItemCount() {
        return monthPresenceModels.size();
    }

    public class MonthPresenceViewHolder extends RecyclerView.ViewHolder {

        TextView tvPulang;
        TextView tvHari;
        TextView tvMasuk;
        TextView tvTgl;
        TextView tvKet;

        public MonthPresenceViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTgl = itemView.findViewById(R.id.tv_tgl);
            tvHari = itemView.findViewById(R.id.tv_hari);
            tvPulang = itemView.findViewById(R.id.tv_pulang);
            tvMasuk = itemView.findViewById(R.id.tv_masuk);
            tvKet = itemView.findViewById(R.id.tv_ket);
        }


        public void bind(MonthPresenceModel monthPresenceModel) {
            ((GradientDrawable) tvMasuk.getBackground()).setColor(Color.LTGRAY);
            ((GradientDrawable) tvPulang.getBackground()).setColor(Color.LTGRAY);
            //tvKet.setText(monthPresenceModel.getKetStsAbsen()+"/"+monthPresenceModel.getKet());

            if(monthPresenceModel.getRecordExist()==0){
                tvPulang.setText("!Record");
                tvMasuk.setText("!Record");
                tvKet.setText("Belum ada record absensi");
                tvKet.setTextColor(ApiTool.getTakColor());
                ((GradientDrawable) tvTgl.getBackground()).setColor(Color.LTGRAY);
            }else if(monthPresenceModel.getDay_off()==1){
                tvMasuk.setTextColor(ApiTool.BLUE_SMOOTH);
                tvPulang.setTextColor(ApiTool.BLUE_SMOOTH);
                tvPulang.setText("LIBUR");
                tvMasuk.setText("LIBUR");
                tvKet.setText("LIBUR");
                tvKet.setTextColor(Color.BLACK);
                ((GradientDrawable) tvTgl.getBackground()).setColor(ApiTool.BLUE_SMOOTH);
            }else if(monthPresenceModel.getHadir()==1){
                String terlambat = "";
                String cepatPulang = "";
                tvKet.setText("HADIR");
                tvKet.setTextColor(Color.BLACK);
                if(monthPresenceModel.getJamMasuk()==null){
                    tvMasuk.setTextColor(ApiTool.getSakitColor());
                    tvMasuk.setText("--:--:--");
                    terlambat = "TERLAMBAT";
                    //tvKet.setText("TERLAMBAT");
                    //tvKet.setTextColor(ApiTool.getTakColor());
                }else{
                    if(monthPresenceModel.getMenitTerlambat() > 0){
                        tvMasuk.setTextColor(ApiTool.getSakitColor());
                        tvMasuk.setText(monthPresenceModel.getJamMasuk());
                        terlambat = "TERLAMBAT";
                        //tvKet.setText("TERLAMBAT");
                        //tvKet.setTextColor(ApiTool.getTakColor());
                    }else{
                        tvMasuk.setTextColor(ApiTool.getHadirColor());
                        tvKet.setTextColor(Color.BLACK);
                        tvMasuk.setText(monthPresenceModel.getJamMasuk());
                    }

                }
                if(monthPresenceModel.getJamPulang()==null){
                    tvPulang.setTextColor(ApiTool.getSakitColor());
                    tvPulang.setText("--:--:--");
                    cepatPulang = "CEPAT TULANG";
                    //tvKet.setText("CEPAT TULANG");
                    //tvKet.setTextColor(ApiTool.getTakColor());
                }else{
                    if(monthPresenceModel.getMenitCp() > 0){
                        tvPulang.setTextColor(ApiTool.getSakitColor());
                        tvPulang.setText(monthPresenceModel.getJamPulang());
                        cepatPulang = "CEPAT TULANG";
                        //tvKet.setText("CEPAT TULANG");
                        //tvKet.setTextColor(ApiTool.getTakColor());
                    }else{
                        tvPulang.setTextColor(ApiTool.getHadirColor());
                        tvKet.setTextColor(Color.BLACK);
                        tvPulang.setText(monthPresenceModel.getJamPulang());
                    }

                }
                if(terlambat!="" && cepatPulang != ""){
                    tvKet.setText(terlambat+"/"+cepatPulang);
                    tvKet.setTextColor(ApiTool.getTakColor());
                }else if(terlambat!=""){
                    tvKet.setText(terlambat);
                    tvKet.setTextColor(ApiTool.getTakColor());
                }else if(cepatPulang!=""){
                    tvKet.setText(cepatPulang);
                    tvKet.setTextColor(ApiTool.getTakColor());
                }
                ((GradientDrawable) tvTgl.getBackground()).setColor(ApiTool.getHadirColor());
            }else if(monthPresenceModel.getDinasLuar()==1){

                if(monthPresenceModel.getJamMasuk()==null){
                    tvMasuk.setText("DINAS");
                    tvMasuk.setTextColor(ApiTool.getDinasColor());
                    tvKet.setText("DINAS");
                    tvKet.setTextColor(ApiTool.getDinasColor());
                }else{
                    tvKet.setTextColor(Color.BLACK);
                    tvMasuk.setTextColor(ApiTool.getHadirColor());
                    tvMasuk.setText(monthPresenceModel.getJamMasuk());
                }
                if(monthPresenceModel.getJamPulang()==null){
                    tvPulang.setText("DINAS");
                    tvPulang.setTextColor(ApiTool.getDinasColor());
                    tvKet.setText("DINAS");
                    tvKet.setTextColor(ApiTool.getDinasColor());
                }else{
                    tvKet.setTextColor(Color.BLACK);
                    tvPulang.setTextColor(ApiTool.getHadirColor());
                    tvPulang.setText(monthPresenceModel.getJamPulang());
                }
                ((GradientDrawable) tvTgl.getBackground()).setColor(ApiTool.getDinasColor());
            }else if(monthPresenceModel.getCuti()==1){
                tvMasuk.setTextColor(ApiTool.getCutiColor());
                tvPulang.setTextColor(ApiTool.getCutiColor());
                tvPulang.setText("CUTI");
                tvMasuk.setText("CUTI");
                tvKet.setText("CUTI");
                tvKet.setTextColor(ApiTool.getCutiColor());
                ((GradientDrawable) tvTgl.getBackground()).setColor(ApiTool.getCutiColor());
            }else if(monthPresenceModel.getIzin()==1){
                tvMasuk.setTextColor(ApiTool.getIzinColor());
                tvPulang.setTextColor(ApiTool.getIzinColor());
                tvPulang.setText("IZIN");
                tvMasuk.setText("IZIN");
                tvKet.setText("IZIN");
                tvKet.setTextColor(ApiTool.getIzinColor());
                ((GradientDrawable) tvTgl.getBackground()).setColor(ApiTool.getIzinColor());
            }else if(monthPresenceModel.getSakit()==1){
                tvMasuk.setTextColor(ApiTool.getSakitColor());
                tvPulang.setTextColor(ApiTool.getSakitColor());
                tvPulang.setText("SAKIT");
                tvMasuk.setText("SAKIT");
                tvKet.setText("SAKIT");
                tvKet.setTextColor(ApiTool.getSakitColor());
                ((GradientDrawable) tvTgl.getBackground()).setColor(ApiTool.getSakitColor());
            }else if(monthPresenceModel.getAbsen()==1){
                tvMasuk.setTextColor(ApiTool.getTakColor());
                tvPulang.setTextColor(ApiTool.getTakColor());
                tvPulang.setText("TAK");
                tvMasuk.setText("TAK");
                tvKet.setText("Tanpa Keterangan");
                tvKet.setTextColor(ApiTool.getTakColor());
                ((GradientDrawable) tvTgl.getBackground()).setColor(ApiTool.getTakColor());
            }else if(monthPresenceModel.getLainLain()==1){
                //tvMasuk.setTextColor(ApiTool.());
                //tvPulang.setTextColor(ApiTool.getTakColor());
                tvPulang.setText("LAIN-LAIN");
                tvMasuk.setText("LAIN-LAIN");
                tvKet.setText("LAIN-LAIN");
                tvKet.setTextColor(Color.BLACK);
                ((GradientDrawable) tvTgl.getBackground()).setColor(Color.LTGRAY);
            } else{
                tvPulang.setText("--:--:--");
                tvMasuk.setText("--:--:--");
                tvKet.setText("Tanpa Keterangan");
                tvKet.setTextColor(ApiTool.getTakColor());
                ((GradientDrawable) tvTgl.getBackground()).setColor(Color.LTGRAY);
            }

            Date date = monthPresenceModel.getTgl();
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Makassar"));
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);


            String hari = new SimpleDateFormat("EEE").format(cal.getTime());
            String bulan = new SimpleDateFormat("MMMM").format(cal.getTime());
            String tahun = new SimpleDateFormat("YYYY").format(cal.getTime());

            tvHari.setText(hari +" "+bulan+" "+tahun);


            String lbl = String.valueOf(day)+"/"+String.valueOf(month+1);
            tvTgl.setText(lbl);
            Random mRandom = new Random();
            //final int color = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
            //((GradientDrawable) tvTgl.getBackground()).setColor(Color.LTGRAY);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickCallback.onItemClicked(monthPresenceModels.get(getAdapterPosition()));
                }
            });
        }

    }

    public interface OnItemClickCallback{
        void onItemClicked(MonthPresenceModel monthPresenceModel);
    }
}
