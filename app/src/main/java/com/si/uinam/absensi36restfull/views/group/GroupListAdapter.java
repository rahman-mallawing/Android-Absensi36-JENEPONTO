package com.si.uinam.absensi36restfull.views.group;

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

import java.util.ArrayList;

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

        TextView txvHadir;
        TextView txvAbsen;
        TextView txvGroup;
        ImageView imgPoster;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);

            txvGroup = itemView.findViewById(R.id.txv_group);
            txvHadir = itemView.findViewById(R.id.txv_hadir1);
            txvAbsen = itemView.findViewById(R.id.txv_absen1);
            imgPoster = itemView.findViewById(R.id.img_poster);

        }

        public void bind(GroupModel groupModel) {

            txvGroup.setText(groupModel.getGrup());
            txvHadir.setText(String.valueOf(groupModel.getHadir()));
            txvAbsen.setText(String.valueOf(groupModel.getAbsen()));
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
