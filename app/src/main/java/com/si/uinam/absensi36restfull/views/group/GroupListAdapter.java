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
import com.si.uinam.absensi36restfull.models.GroupModel;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.GroupViewHolder> {

    private ArrayList<GroupModel> groupList = new ArrayList<>();
    private OnItemClickCallback itemClickCallback;

    public void setGroupList(ArrayList<GroupModel> groupModelArrayList) {
        groupList.clear();
        groupList.addAll(groupModelArrayList);
        notifyDataSetChanged();
        Log.d("TES-VIEW-MODEL", "Notifed adapter: " + groupModelArrayList.size());
    }

    public void setItemClickCallback(OnItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
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
        TextView tvDetails;
        TextView mIcon;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);

            mIcon = itemView.findViewById(R.id.tvIcon);
            tvGroup = itemView.findViewById(R.id.tv_group);
            tvMonthYear = itemView.findViewById(R.id.tv_month_year);
            tvPersenHadir = itemView.findViewById(R.id.tv_persen_hadir);
            tvHadir = itemView.findViewById(R.id.tv_hadir);
            tvDetails = itemView.findViewById(R.id.tv_details);

        }

        public void bind(GroupModel groupModel) {

            tvGroup.setText(groupModel.getGrup());
            //txvHadir.setText(String.valueOf(groupModel.getHadir()));
            //
            Date currentDate = new Date();
            Calendar cal = Calendar.getInstance();
            String hari = new SimpleDateFormat("EEE").format(cal.getTime());
            String bulan = new SimpleDateFormat("MMM").format(cal.getTime());
            String tahun = new SimpleDateFormat("YYYY").format(cal.getTime());
            tvMonthYear.setText(hari+"-"+bulan+"-"+tahun);
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
                tvPersenHadir.setTextColor(Color.parseColor("#006400"));
                //((GradientDrawable) tvPersenHadir.getBackground()).setColor(Color.GREEN);
            } else if (persenHadir >= 70){
                tvPersenHadir.setTextColor(Color.BLUE);
                //((GradientDrawable) tvPersenHadir.getBackground()).setColor(Color.BLUE);
            }else if (persenHadir >= 51){
                tvPersenHadir.setTextColor(Color.YELLOW);
                //((GradientDrawable) tvPersenHadir.getBackground()).setColor(Color.YELLOW);
            }else if (persenHadir >= 5){
                tvPersenHadir.setTextColor(Color.RED);
                //((GradientDrawable) tvPersenHadir.getBackground()).setColor(Color.RED);
            }else {
                tvPersenHadir.setTextColor(Color.GRAY);
                //((GradientDrawable) tvPersenHadir.getBackground()).setColor(Color.GRAY);
            }
            String details = "Terlambat: "+ groupModel.getJumlahTerlambat() +", Dinas Luar: "+ groupModel.getDinasLuar() +", Cuti: "+ groupModel.getCuti() +", Izin: "+ groupModel.getIzin() +", Lain-lain: "+", Cepat Pulang: "+ groupModel.getJumlahCp() + groupModel.getLainLain();
            tvDetails.setText(details);
            mIcon.setText(String.valueOf(hrf).toUpperCase());
            Random mRandom = new Random();
            final int color = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
            ((GradientDrawable) mIcon.getBackground()).setColor(color);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickCallback.onItemClicked(groupList.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnItemClickCallback{
        void onItemClicked(GroupModel groupModel);
    }
}
