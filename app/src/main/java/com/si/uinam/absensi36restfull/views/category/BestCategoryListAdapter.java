package com.si.uinam.absensi36restfull.views.category;

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
import com.si.uinam.absensi36restfull.models.CategoryModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BestCategoryListAdapter extends RecyclerView.Adapter<BestCategoryListAdapter.BestCategoryViewHolder> {

    private ArrayList<CategoryModel> categoryList = new ArrayList<>();
    private OnItemClickCallback itemClickCallback;

    public void setCategoryList(ArrayList<CategoryModel> categoryModelArrayList) {
        categoryList.clear();
        categoryList.addAll(categoryModelArrayList);
        notifyDataSetChanged();
        Log.d("TES-VIEW-MODEL", "Notifed adapter: " + categoryModelArrayList.size());
    }

    public void setItemClickCallback(OnItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    @NonNull
    @Override
    public BestCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_category,parent,false);
        return new BestCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BestCategoryViewHolder holder, int position) {
        holder.bind(categoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class BestCategoryViewHolder extends RecyclerView.ViewHolder {

        TextView txvNama;
        TextView txvNi;
        ImageView imgPoster;


        public BestCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            txvNama = itemView.findViewById(R.id.txv_nama);
            txvNi = itemView.findViewById(R.id.txv_ni);
            imgPoster = itemView.findViewById(R.id.img_poster_cat);

        }

        public void bind(CategoryModel categoryModel) {

            RequestOptions requestOptions = new RequestOptions().override(96, 96);
            requestOptions.placeholder(R.drawable.placeholder);
            requestOptions.error(R.drawable.user);
            String url = ApiHelper.getImgBaseUrl() + categoryModel.getFoto();
            Log.d("RETROFIT-TEST-URL", url);
            Glide.with(itemView.getContext())
                    .load(url)
                    .apply(requestOptions)
                    .into(imgPoster);
            txvNama.setText(categoryModel.getNama());
            txvNi.setText(categoryModel.getNi());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickCallback.onItemClicked(categoryList.get(getAdapterPosition()));
                }
            });
        }

    }

    public interface OnItemClickCallback{
        void onItemClicked(CategoryModel categoryModel);
    }
}
