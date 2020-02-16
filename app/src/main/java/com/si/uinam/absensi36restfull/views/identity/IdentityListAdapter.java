package com.si.uinam.absensi36restfull.views.identity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.si.uinam.absensi36restfull.R;
import com.si.uinam.absensi36restfull.helpers.ApiHelper;
import com.si.uinam.absensi36restfull.models.HarianGroupModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class IdentityListAdapter extends RecyclerView.Adapter<IdentityListAdapter.IdentityViewHolder> {

    private ArrayList<HarianGroupModel> harianGroupList = new ArrayList<>();
    private OnItemClickCallback itemClickCallback;

    public void setHarianGroupList(ArrayList<HarianGroupModel> harianGroupList) {
        this.harianGroupList.clear();
        this.harianGroupList.addAll(harianGroupList);
        notifyDataSetChanged();
    }

    public void setItemClickCallback(OnItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    @NonNull
    @Override
    public IdentityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_identity,parent,false);
        return new IdentityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IdentityViewHolder holder, int position) {
        holder.bind(harianGroupList.get(position));
    }

    @Override
    public int getItemCount() {
        return harianGroupList.size();
    }

    public class IdentityViewHolder extends RecyclerView.ViewHolder {

        TextView tvPersenHadir;
        TextView tvMonthYear;
        TextView tvName;
        TextView tvHadir;
        TextView tvDetails;
        CircleImageView imgIdentity;


        public IdentityViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIdentity = itemView.findViewById(R.id.img_identity);
            tvName = itemView.findViewById(R.id.tv_name);
            tvMonthYear = itemView.findViewById(R.id.tv_month_year);
            tvPersenHadir = itemView.findViewById(R.id.tv_persen_hadir);
            tvHadir = itemView.findViewById(R.id.tv_hadir);
            tvDetails = itemView.findViewById(R.id.tv_details);
        }

        public void bind(HarianGroupModel harianGroupModel) {

            RequestOptions requestOptions = new RequestOptions().override(40, 40);
            requestOptions.placeholder(R.drawable.placeholder);
            requestOptions.error(R.drawable.user);
            String url = ApiHelper.getImgBaseUrl() + harianGroupModel.getFoto();
            Log.d("RETROFIT-TEST-URL", url);
            Glide.with(itemView.getContext())
                    .load(url)
                    .apply(requestOptions)
                    .into(imgIdentity);

            tvName.setText(harianGroupModel.getNama());
            //txvHadir.setText(String.valueOf(groupModel.getHadir()));

            tvDetails.setText(harianGroupModel.getNap());

            if(harianGroupModel.getHadir() == 1){
                tvPersenHadir.setTextColor(Color.parseColor("#006400"));
                tvHadir.setText("Hadir");
            } else if (harianGroupModel.getDinasLuar() == 1){
                tvPersenHadir.setTextColor(Color.BLUE);
                tvHadir.setText("Dinas Luar");
            }else if (harianGroupModel.getCuti() == 1){
                tvPersenHadir.setTextColor(Color.YELLOW);
                tvHadir.setText("Cuti");
            }else if (harianGroupModel.getIzin() == 1){
                tvPersenHadir.setTextColor(Color.RED);
                tvHadir.setText("Izin");
            }else if (harianGroupModel.getSakit() == 1){
                tvPersenHadir.setTextColor(Color.RED);
                tvHadir.setText("Sakit");
            }else if (harianGroupModel.getAbsen() == 1){
                tvPersenHadir.setTextColor(Color.RED);
                tvHadir.setText("TAK");
            }else {
                tvPersenHadir.setTextColor(Color.GRAY);
                tvHadir.setText("Lain-lain");
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickCallback.onItemClicked(harianGroupList.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnItemClickCallback{
        void onItemClicked(HarianGroupModel harianGroupModel);
    }
}
