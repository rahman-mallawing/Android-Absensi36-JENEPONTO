package com.si.uinam.absensi36restfull.views.monthpresence;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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


            tvMasuk.setText(monthPresenceModel.getJamMasuk());
            if(monthPresenceModel.getHadir()==1){
                tvMasuk.setTextColor(ApiTool.getHadirColor());
                tvPulang.setTextColor(ApiTool.getHadirColor());
                ((GradientDrawable) tvTgl.getBackground()).setColor(ApiTool.getHadirColor());
            }else{
                ((GradientDrawable) tvTgl.getBackground()).setColor(Color.LTGRAY);
            }
            tvPulang.setText(monthPresenceModel.getJamPulang());
            tvKet.setText(monthPresenceModel.getKetStsAbsen()+"/"+monthPresenceModel.getKet());
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
            final int color = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
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
