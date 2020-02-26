package com.si.uinam.absensi36restfull.views.checklogpage.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.si.uinam.absensi36restfull.databinding.ItemChecklogBinding;
import com.si.uinam.absensi36restfull.databinding.NetworkItemBinding;
import com.si.uinam.absensi36restfull.views.checklogpage.model.ChecklogModel;
import com.si.uinam.absensi36restfull.views.identitywithpagelib.utils.NetworkState;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ChecklogPageListAdapter extends PagedListAdapter<ChecklogModel, RecyclerView.ViewHolder> {


    private static final int TYPE_PROGRESS = 0;
    private static final int TYPE_ITEM = 1;

    private Context context;
    private NetworkState networkState;
    private OnItemClickCallback itemClickCallback;

    public ChecklogPageListAdapter(Context context) {
        super(ChecklogModel.DIFF_CALLBACK);
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
            ItemChecklogBinding itemBinding = ItemChecklogBinding.inflate(layoutInflater, parent, false);
            ChecklogItemViewHolder viewHolder = new ChecklogItemViewHolder(itemBinding);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ChecklogItemViewHolder) {
            ((ChecklogItemViewHolder)holder).bindTo(getItem(position));
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


    public class ChecklogItemViewHolder extends RecyclerView.ViewHolder {

        private ItemChecklogBinding binding;
        public ChecklogItemViewHolder(ItemChecklogBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(ChecklogModel checklogModel) {

            String name = checklogModel.getName() + " " + checklogModel.getNrp() + " " + checklogModel.getEmployeeDept() + " " + checklogModel.getDevAlias();

            binding.tvUserid.setText(String.valueOf(checklogModel.getUserId()));
            binding.tvNama.setText(name);
            binding.tvSn.setText(checklogModel.getSn());
            binding.tvTime.setText(checklogModel.getCheckTime());

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
        void onItemClicked(ChecklogModel checklogModel);
    }
}
