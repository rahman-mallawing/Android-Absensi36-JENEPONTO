package com.si.uinam.absensi36restfull.views.search;

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
import com.si.uinam.absensi36restfull.models.IdentityModel;
import com.si.uinam.absensi36restfull.models.PaginationModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.SearchViewHolder> {

    private ArrayList<IdentityModel> identityModelList = new ArrayList<>();
    private OnItemClickCallback itemClickCallback;

    public void setIdentityList(ArrayList<IdentityModel> identityList) {
        this.identityModelList.clear();
        this.identityModelList.addAll(identityList);
        notifyDataSetChanged();
    }

    public void setItemClickCallback(OnItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_search,parent,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.bind(identityModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return identityModelList.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView tvNama;
        TextView tvNi;
        CircleImageView imgPoster;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tv_name);
            tvNi = itemView.findViewById(R.id.tv_ni);
            imgPoster = itemView.findViewById(R.id.img_search);

        }

        public void bind(IdentityModel identityModel) {

            RequestOptions requestOptions = new RequestOptions().override(50, 50);
            requestOptions.placeholder(R.drawable.placeholder);
            requestOptions.error(R.drawable.user);
            String url = ApiHelper.getImgBaseUrl() + identityModel.getFoto();
            Log.d("RETROFIT-TEST-URL", url);
            Glide.with(itemView.getContext())
                    .load(url)
                    .apply(requestOptions)
                    .into(imgPoster);
            tvNama.setText(identityModel.getName());
            tvNi.setText(identityModel.getNi());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickCallback.onItemClicked(identityModelList.get(getAdapterPosition()));
                }
            });
        }

    }

    public interface OnItemClickCallback{
        void onItemClicked(IdentityModel identityModel);
    }

}
