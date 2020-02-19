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
import com.si.uinam.absensi36restfull.helpers.ApiTool;
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
        //holder.tvUrutan.setText(position+1);
        holder.bind(harianGroupList.get(position));
    }

    @Override
    public int getItemCount() {
        return harianGroupList.size();
    }

    public class IdentityViewHolder extends RecyclerView.ViewHolder {

        TextView tvStsHadir;
        TextView tvUrutan;
        TextView tvName;
        TextView tvNi;
        CircleImageView imgIdentity;
        int urutan = 0;


        public IdentityViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIdentity = itemView.findViewById(R.id.img_identity);
            tvName = itemView.findViewById(R.id.tv_name);
            tvUrutan = itemView.findViewById(R.id.tv_urutan);
            tvStsHadir = itemView.findViewById(R.id.tv_sts_hadir);
            tvNi = itemView.findViewById(R.id.tv_ni);

            //urutan = urutan +1;
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

            //tvUrutan.setText(this.urutan);
            tvName.setText(harianGroupModel.getNama());
            //txvHadir.setText(String.valueOf(groupModel.getHadir()));

            tvNi.setText(harianGroupModel.getNap());

            if(harianGroupModel.getRecordExist() != 1){
                ((GradientDrawable) tvStsHadir.getBackground()).setColor(Color.LTGRAY);
                tvStsHadir.setTextColor(ApiTool.getTakColor());
                tvStsHadir.setText("No Record Yet!");
            } else if(harianGroupModel.getHadir() == 1){
                ((GradientDrawable) tvStsHadir.getBackground()).setColor(ApiTool.getHadirColor());
                tvStsHadir.setTextColor(Color.WHITE);
                tvStsHadir.setText("Hadir");
            } else if (harianGroupModel.getDinasLuar() == 1){
                ((GradientDrawable) tvStsHadir.getBackground()).setColor(ApiTool.getDinasColor());
                tvStsHadir.setTextColor(Color.BLUE);
                tvStsHadir.setText("Dinas Luar");
            }else if (harianGroupModel.getCuti() == 1){
                ((GradientDrawable) tvStsHadir.getBackground()).setColor(ApiTool.getCutiColor());
                tvStsHadir.setTextColor(Color.YELLOW);
                tvStsHadir.setText("Cuti");
            }else if (harianGroupModel.getIzin() == 1){
                ((GradientDrawable) tvStsHadir.getBackground()).setColor(ApiTool.getIzinColor());
                tvStsHadir.setTextColor(Color.RED);
                tvStsHadir.setText("Izin");
            }else if (harianGroupModel.getSakit() == 1){
                ((GradientDrawable) tvStsHadir.getBackground()).setColor(ApiTool.getSakitColor());
                tvStsHadir.setTextColor(Color.RED);
                tvStsHadir.setText("Sakit");
            }else if (harianGroupModel.getAbsen() == 1){
                ((GradientDrawable) tvStsHadir.getBackground()).setColor(ApiTool.getTakColor());
                tvStsHadir.setTextColor(Color.RED);
                tvStsHadir.setText("TAK");
            }else {
                ((GradientDrawable) tvStsHadir.getBackground()).setColor(Color.LTGRAY);
                tvStsHadir.setTextColor(Color.GRAY);
                tvStsHadir.setText("Lain-lain");
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
