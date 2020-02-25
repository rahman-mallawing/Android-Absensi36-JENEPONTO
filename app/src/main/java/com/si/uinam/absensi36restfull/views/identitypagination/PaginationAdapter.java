package com.si.uinam.absensi36restfull.views.identitypagination;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.si.uinam.absensi36restfull.R;
import com.si.uinam.absensi36restfull.helpers.ApiHelper;
import com.si.uinam.absensi36restfull.helpers.ApiTool;
import com.si.uinam.absensi36restfull.models.HarianGroupModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private PaginationAdapter.OnItemClickCallback itemClickCallback;
    private ArrayList<HarianGroupModel> harianGroupList = new ArrayList<>();
    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private Context context;

    private boolean isLoadingAdded = false;

    public PaginationAdapter(Context context) {
        this.context = context;
        harianGroupList = new ArrayList<>();
    }

    public ArrayList<HarianGroupModel> getHarianIdentity() {
        return harianGroupList;
    }

    public void setHarianIdentity(ArrayList<HarianGroupModel> harianGroupModels) {
        this.harianGroupList = harianGroupModels;
    }

    public void setItemClickCallback(OnItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress_identity, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_data_identity, parent, false);
        viewHolder = new IdentityVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HarianGroupModel harianGroupModel = harianGroupList.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                IdentityVH identityVH = (IdentityVH) holder;
                identityVH.bind(harianGroupModel);
                break;
            case LOADING:
//                Do nothing
                break;
        }
    }

    @Override
    public int getItemCount() {
        return harianGroupList == null ? 0 : harianGroupList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == harianGroupList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    protected class IdentityVH extends RecyclerView.ViewHolder {
        TextView tvStsHadir;
        TextView tvUrutan;
        TextView tvName;
        TextView tvNi;
        CircleImageView imgIdentity;

        public IdentityVH(View itemView) {
            super(itemView);

            imgIdentity = itemView.findViewById(R.id.img_identity);
            tvName = itemView.findViewById(R.id.tv_name);
            tvUrutan = itemView.findViewById(R.id.tv_urutan);
            tvStsHadir = itemView.findViewById(R.id.tv_sts_hadir);
            tvNi = itemView.findViewById(R.id.tv_ni);
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


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickCallback{
        void onItemClicked(HarianGroupModel harianGroupModel);
    }

    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(HarianGroupModel hgm) {
        harianGroupList.add(hgm);
        notifyItemInserted(harianGroupList.size() - 1);
    }

    public void addAll(ArrayList<HarianGroupModel> mcList) {
        for (HarianGroupModel mc : mcList) {
            add(mc);
        }
    }

    public void remove(HarianGroupModel city) {
        int position = harianGroupList.indexOf(city);
        if (position > -1) {
            harianGroupList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new HarianGroupModel());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = harianGroupList.size() - 1;
        HarianGroupModel item = getItem(position);

        if (item != null) {
            harianGroupList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public HarianGroupModel getItem(int position) {
        return harianGroupList.get(position);
    }


}
