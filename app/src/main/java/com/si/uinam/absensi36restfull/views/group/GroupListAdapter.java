package com.si.uinam.absensi36restfull.views.group;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.si.uinam.absensi36restfull.R;
import com.si.uinam.absensi36restfull.helpers.ApiTool;
import com.si.uinam.absensi36restfull.models.GroupModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.GroupViewHolder> {

    private ArrayList<GroupModel> groupList = new ArrayList<>();
    private OnItemClickCallback itemClickCallback;
    private String tglString;

    public void setGroupList(ArrayList<GroupModel> groupModelArrayList) {
        groupList.clear();
        groupList.addAll(groupModelArrayList);
        notifyDataSetChanged();
        Log.d("TES-VIEW-MODEL", "Notifed adapter: " + groupModelArrayList.size());
    }

    public void setItemClickCallback(OnItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public void setTglString(String tglString) {
        this.tglString = tglString;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_group,parent,false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupListAdapter.GroupViewHolder holder, int position) {
        holder.bind(groupList.get(position));
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {

        TextView tvPersenHadir;
        TextView tvMonthYear;
        TextView tvGroup;
        TextView tvHadir;
        TextView mIcon;

        TextView tvAbsen;
        TextView tvTerlambat;
        TextView tvDinas;
        TextView tvCuti;
        TextView tvIzin;
        TextView tvSakit;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);

            mIcon = itemView.findViewById(R.id.tvIcon);
            tvGroup = itemView.findViewById(R.id.tv_group);
            tvMonthYear = itemView.findViewById(R.id.tv_month_year);
            tvPersenHadir = itemView.findViewById(R.id.tv_persen_hadir);
            tvHadir = itemView.findViewById(R.id.tv_hadir);
            //tvDetails = itemView.findViewById(R.id.tv_details);

            tvAbsen = itemView.findViewById(R.id.tv_absen);
            tvTerlambat = itemView.findViewById(R.id.tv_terlambat);
            tvDinas = itemView.findViewById(R.id.tv_dinas);
            tvCuti = itemView.findViewById(R.id.tv_cuti);
            tvIzin = itemView.findViewById(R.id.tv_izin);
            tvSakit = itemView.findViewById(R.id.tv_sakit);

        }

        public void bind(GroupModel groupModel) {
            int color;
            tvGroup.setText(groupModel.getGrup());
            //txvHadir.setText(String.valueOf(groupModel.getHadir()));
            //
            Log.d("TGL", tglString);

            //Date currentDate = new Date();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            Date date = null;
            try {
                date = format.parse(tglString);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                Log.d("TGL", date.toString());
                String hari = new SimpleDateFormat("EEE").format(cal.getTime());
                String bulan = new SimpleDateFormat("MMM").format(cal.getTime());
                String tahun = new SimpleDateFormat("YYYY").format(cal.getTime());
                tvMonthYear.setText(hari+"-"+bulan+"-"+tahun);
            } catch (ParseException e) {
                tvMonthYear.setText("!Date Err");
                e.printStackTrace();
            };


            String[] result = groupModel.getGrup().split(" ");
            char hrf = 'X';
            if (result.length == 1){
                hrf = result[0].charAt(0);
            }else if (result.length >= 2){
                hrf = result[1].charAt(0);
            }
            int hadir = groupModel.getHadir();
            int jumPegawai = groupModel.getJumlahPegawai();
            Double persenHadir = ((double) hadir)/jumPegawai*100;
            tvPersenHadir.setText((String.format("%.2f", persenHadir))+"%");
            tvHadir.setText("Kehadiran: "+hadir+"\\"+jumPegawai);
            if(persenHadir >= 80){
                tvPersenHadir.setTextColor(ApiTool.getHadirColor());
                color = ApiTool.getHadirColor();
                ((GradientDrawable) mIcon.getBackground()).setColor(ApiTool.getHadirColor());
                //((GradientDrawable) tvPersenHadir.getBackground()).setColor(Color.GREEN);
            } else if (persenHadir >= 70){
                tvPersenHadir.setTextColor(ApiTool.BLUE_SMOOTH);
                color = ApiTool.BLUE_SMOOTH;
                ((GradientDrawable) mIcon.getBackground()).setColor(ApiTool.BLUE_SMOOTH);
                //((GradientDrawable) tvPersenHadir.getBackground()).setColor(Color.BLUE);
            }else if (persenHadir >= 51){
                color = ApiTool.getDinasColor();
                tvPersenHadir.setTextColor(ApiTool.getDinasColor());
                ((GradientDrawable) mIcon.getBackground()).setColor(ApiTool.getDinasColor());
                //((GradientDrawable) tvPersenHadir.getBackground()).setColor(Color.YELLOW);
            }else if (persenHadir >= 1){
                color = ApiTool.getTakColor();
                tvPersenHadir.setTextColor(ApiTool.getTakColor());
                ((GradientDrawable) mIcon.getBackground()).setColor(ApiTool.getTakColor());
                //((GradientDrawable) tvPersenHadir.getBackground()).setColor(Color.RED);
            }else {
                color = Color.GRAY;
                tvPersenHadir.setTextColor(Color.GRAY);
                ((GradientDrawable) mIcon.getBackground()).setColor(Color.LTGRAY);
                //((GradientDrawable) tvPersenHadir.getBackground()).setColor(Color.GRAY);
            }
            String details = "Terlambat: "+ groupModel.getJumlahTerlambat() +", Dinas Luar: "+ groupModel.getDinasLuar() +", Cuti: "+ groupModel.getCuti() +", Izin: "+ groupModel.getIzin();
            //tvDetails.setText(details);
            mIcon.setText(String.valueOf(hrf).toUpperCase());
            Random mRandom = new Random();
            //final int color = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
            //((GradientDrawable) mIcon.getBackground()).setColor(color);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickCallback.onItemClicked(groupList.get(getAdapterPosition()), mIcon.getText().toString(), color);
                }
            });

            tvAbsen.setText(String.valueOf(groupModel.getAbsen()));
            tvTerlambat.setText(String.valueOf(groupModel.getJumlahTerlambat()));
            tvDinas.setText(String.valueOf(groupModel.getDinasLuar()));
            tvCuti.setText(String.valueOf(groupModel.getCuti()));
            tvIzin.setText(String.valueOf(groupModel.getIzin()));
            tvSakit.setText(String.valueOf(groupModel.getLainLain()));

            ((GradientDrawable) tvAbsen.getBackground()).setColor(Color.LTGRAY);
            ((GradientDrawable) tvTerlambat.getBackground()).setColor(Color.LTGRAY);
            ((GradientDrawable) tvDinas.getBackground()).setColor(Color.LTGRAY);
            ((GradientDrawable) tvCuti.getBackground()).setColor(Color.LTGRAY);
            ((GradientDrawable) tvIzin.getBackground()).setColor(Color.LTGRAY);
            ((GradientDrawable) tvSakit.getBackground()).setColor(Color.LTGRAY);


        }
    }

    public interface OnItemClickCallback{
        void onItemClicked(GroupModel groupModel, String identity, int color);
    }
}
