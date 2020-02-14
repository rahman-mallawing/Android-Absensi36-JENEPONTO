package com.si.uinam.absensi36restfull.views.category;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.si.uinam.absensi36restfull.LoginActivity;
import com.si.uinam.absensi36restfull.R;
import com.si.uinam.absensi36restfull.helpers.ApiTool;
import com.si.uinam.absensi36restfull.models.CategoryModel;
import com.si.uinam.absensi36restfull.services.AuthenticationListener;
import com.si.uinam.absensi36restfull.viewmodels.BestCategoryViewModel;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class BestCategoryFragment extends Fragment implements AuthenticationListener {

    private BestCategoryViewModel categoryViewModel;
    private ProgressBar progressBar;
    private BestCategoryListAdapter bestCategoryListAdapter;
    private RecyclerView rcvBestCategory;

    public BestCategoryFragment() {
        // Required empty public constructor
    }

    public static BestCategoryFragment newInstance() {
        Log.d("FRAGMENT-CREATE", "onResume Movie");
        BestCategoryFragment fragment = new BestCategoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        categoryViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(BestCategoryViewModel.class);
        categoryViewModel.getCategoryList().observe(this, new Observer<ArrayList<CategoryModel>>() {
            @Override
            public void onChanged(ArrayList<CategoryModel> categoryModels) {
                if(categoryModels != null){
                    bestCategoryListAdapter.setCategoryList(categoryModels);
                    showLoading(false);
                }
            }
        });
        categoryViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                String msg = "Error: " + s;
                Toast toast = Toast.makeText(getContext(), msg, Toast.LENGTH_LONG);
                View vi = toast.getView();
                vi.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                TextView text = vi.findViewById(android.R.id.message);
                text.setTextColor(Color.WHITE);
                toast.show();
                showLoading(false);
            }
        });
        String tgl = ApiTool.getTodayDateString();
        categoryViewModel.loadBestCategoryList(this, tgl);
        Log.d("TES-onCreate", "onCreateonCreateonCreateonCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_best_category, container, false);


        categoryViewModel =
                ViewModelProviders.of(this).get(BestCategoryViewModel.class);
        View view = inflater.inflate(R.layout.fragment_best_category, container, false);
        progressBar = view.findViewById(R.id.categoryProgressBar);
        rcvBestCategory = view.findViewById(R.id.rcv_best_category);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        Log.d("TES-VIEW-MODEL", "1. assdd Connect internet API");
        rcvBestCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        bestCategoryListAdapter = new BestCategoryListAdapter();
        bestCategoryListAdapter.setItemClickCallback(new BestCategoryListAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(CategoryModel categoryModel) {
                Toast.makeText(getContext(), getResources().getString(R.string.app_name) + categoryModel.getNama(), Toast.LENGTH_SHORT).show();
            }
        });

        bestCategoryListAdapter.notifyDataSetChanged();
        rcvBestCategory.setAdapter(bestCategoryListAdapter);

        //movieViewModel.loadMovieList(getContext());

        showLoading(true);
        return view;
    }

    private void showLoading(Boolean state) {
        if(progressBar == null) {
            Log.d("TES-progressBar", "NULL NULL NULL");
            return;
        }
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            rcvBestCategory.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            rcvBestCategory.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onUserLoggedOut() {
        Log.d("TES-LOGOUT", "onUserLoggedOut");
        //showLoading(false);
        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
        //detailIntent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
        startActivity(loginIntent);
    }
}
