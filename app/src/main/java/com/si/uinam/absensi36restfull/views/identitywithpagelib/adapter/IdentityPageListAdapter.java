package com.si.uinam.absensi36restfull.views.identitywithpagelib.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.si.uinam.absensi36restfull.R;
import com.si.uinam.absensi36restfull.databinding.ItemIdentityBinding;
import com.si.uinam.absensi36restfull.databinding.NetworkItemBinding;
import com.si.uinam.absensi36restfull.helpers.ApiHelper;
import com.si.uinam.absensi36restfull.helpers.ApiTool;
import com.si.uinam.absensi36restfull.models.HarianGroupModel;
import com.si.uinam.absensi36restfull.views.identitywithpagelib.utils.NetworkState;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;


public class IdentityPageListAdapter extends PagedListAdapter<HarianGroupModel, RecyclerView.ViewHolder> {

    private static final int TYPE_PROGRESS = 0;
    private static final int TYPE_ITEM = 1;

    private Context context;
    private NetworkState networkState;
    private OnItemClickCallback itemClickCallback;

    public IdentityPageListAdapter(Context context) {
        super(HarianGroupModel.DIFF_CALLBACK);
        this.context = context;
    }

    public void setItemClickCallback(OnItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(viewType == TYPE_PROGRESS) {
            NetworkItemBinding headerBinding = NetworkItemBinding.inflate(layoutInflater, parent, false);
            NetworkStateItemViewHolder viewHolder = new NetworkStateItemViewHolder(headerBinding);

            return viewHolder;

        } else {
            ItemIdentityBinding itemBinding = ItemIdentityBinding.inflate(layoutInflater, parent, false);
            IdentityItemViewHolder viewHolder = new IdentityItemViewHolder(itemBinding);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof IdentityItemViewHolder) {
            ((IdentityItemViewHolder)holder).bindTo(getItem(position));
        } else {
            ((NetworkStateItemViewHolder) holder).bindView(networkState);
        }
    }


    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.LOADED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return TYPE_PROGRESS;
        } else {
            return TYPE_ITEM;
        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }


    public class IdentityItemViewHolder extends RecyclerView.ViewHolder {

        private ItemIdentityBinding binding;
        public IdentityItemViewHolder(ItemIdentityBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(HarianGroupModel harianGroupModel) {


            RequestOptions requestOptions = new RequestOptions().override(40, 40);
            requestOptions.placeholder(R.drawable.placeholder);
            requestOptions.error(R.drawable.user);
            String url = ApiHelper.getImgBaseUrl() + harianGroupModel.getFoto();
            Log.d("RETROFIT-TEST-URL", url);
            Glide.with(itemView.getContext())
                    .load(url)
                    .apply(requestOptions)
                    .into(binding.imgIdentity);

            //tvUrutan.setText(this.urutan);
            binding.tvName.setText(harianGroupModel.getNama());
            //txvHadir.setText(String.valueOf(groupModel.getHadir()));

            binding.tvNi.setText(harianGroupModel.getNap());

            if(harianGroupModel.getRecordExist() != 1){
                ((GradientDrawable) binding.tvStsHadir.getBackground()).setColor(Color.LTGRAY);
                binding.tvStsHadir.setTextColor(ApiTool.getTakColor());
                binding.tvStsHadir.setText("No Record Yet!");
            } else if(harianGroupModel.getHadir() == 1){
                ((GradientDrawable) binding.tvStsHadir.getBackground()).setColor(ApiTool.getHadirColor());
                binding.tvStsHadir.setTextColor(Color.WHITE);
                binding.tvStsHadir.setText("Hadir");
            } else if (harianGroupModel.getDinasLuar() == 1){
                ((GradientDrawable) binding.tvStsHadir.getBackground()).setColor(ApiTool.getDinasColor());
                binding.tvStsHadir.setTextColor(Color.WHITE);
                binding.tvStsHadir.setText("Dinas Luar");
            }else if (harianGroupModel.getCuti() == 1){
                ((GradientDrawable) binding.tvStsHadir.getBackground()).setColor(ApiTool.getCutiColor());
                binding.tvStsHadir.setTextColor(Color.WHITE);
                binding.tvStsHadir.setText("Cuti");
            }else if (harianGroupModel.getIzin() == 1){
                ((GradientDrawable) binding.tvStsHadir.getBackground()).setColor(ApiTool.getIzinColor());
                binding.tvStsHadir.setTextColor(Color.WHITE);
                binding.tvStsHadir.setText("Izin");
            }else if (harianGroupModel.getSakit() == 1){
                ((GradientDrawable) binding.tvStsHadir.getBackground()).setColor(ApiTool.getSakitColor());
                binding.tvStsHadir.setTextColor(Color.WHITE);
                binding.tvStsHadir.setText("Sakit");
            }else if (harianGroupModel.getAbsen() == 1){
                ((GradientDrawable) binding.tvStsHadir.getBackground()).setColor(ApiTool.getTakColor());
                binding.tvStsHadir.setTextColor(Color.WHITE);
                binding.tvStsHadir.setText("Tanpa Keterangan");
            }else {
                ((GradientDrawable) binding.tvStsHadir.getBackground()).setColor(Color.LTGRAY);
                binding.tvStsHadir.setTextColor(Color.GRAY);
                binding.tvStsHadir.setText("Lain-lain");
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickCallback.onItemClicked(getItem(getAdapterPosition()));
                }
            });

        }
    }


    public class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {

        private NetworkItemBinding binding;
        public NetworkStateItemViewHolder(NetworkItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(NetworkState networkState) {
            if (networkState != null && networkState.getStatus() == NetworkState.Status.RUNNING) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.progressBar.setVisibility(View.GONE);
            }

            if (networkState != null && networkState.getStatus() == NetworkState.Status.FAILED) {
                binding.errorMsg.setVisibility(View.VISIBLE);
                binding.errorMsg.setText(networkState.getMsg());
            } else {
                binding.errorMsg.setVisibility(View.GONE);
            }
        }
    }

    public interface OnItemClickCallback{
        void onItemClicked(HarianGroupModel harianGroupModel);
    }
}
