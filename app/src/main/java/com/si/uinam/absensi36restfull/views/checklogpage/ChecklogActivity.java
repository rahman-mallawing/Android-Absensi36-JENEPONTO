package com.si.uinam.absensi36restfull.views.checklogpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.si.uinam.absensi36restfull.LoginActivity;
import com.si.uinam.absensi36restfull.R;
import com.si.uinam.absensi36restfull.databinding.ChecklogBinding;
import com.si.uinam.absensi36restfull.helpers.ApiTool;
import com.si.uinam.absensi36restfull.services.AuthenticationListener;
import com.si.uinam.absensi36restfull.views.checklogpage.adapter.ChecklogPageListAdapter;
import com.si.uinam.absensi36restfull.views.checklogpage.model.ChecklogModel;
import com.si.uinam.absensi36restfull.views.checklogpage.viewmodel.ChecklogViewModel;
import com.si.uinam.absensi36restfull.views.identitywithpagelib.AppController;
import com.si.uinam.absensi36restfull.views.identitywithpagelib.utils.NetworkState;

import java.util.Objects;

public class ChecklogActivity extends AppCompatActivity implements AuthenticationListener {

    private ChecklogBinding binding;
    private ChecklogViewModel checklogViewModel;
    private ChecklogPageListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_checklog);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_checklog);

        String tgl = ApiTool.getTodayDateString(this);

        getSupportActionBar().setTitle("Checklog: " + tgl);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        binding.btnNoResult.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.app_name) , Toast.LENGTH_SHORT).show();
            onSupportNavigateUp();
        });

        binding.checklogProgressBar.setVisibility(View.VISIBLE);
        binding.checklogProgressBar.setIndeterminate(true);

        AppController appController = AppController.create(this, this);
        appController.setTgl(tgl);

        checklogViewModel = new ChecklogViewModel(appController);
        binding.rcvChecklog.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new ChecklogPageListAdapter(getApplicationContext());

        checklogViewModel.getChecklogLiveData().observe(this, pageList -> {
            Log.d("TES-HASIL", pageList.toString());

            adapter.submitList(pageList);
        });

        checklogViewModel.getNetworkState().observe(this, networkState -> {
            if(networkState == NetworkState.LOADED){
                showLoading(false);
            }else if (networkState == NetworkState.EMPTY_LOADED){
                showLoading(false);
                binding.tvNoResult.setText("Tanggal: " + tgl + " tidak ada log.");
                binding.llChecklogNoResult.setVisibility(View.VISIBLE);
            }
            adapter.setNetworkState(networkState);
        });

        adapter.setItemClickCallback(new ChecklogPageListAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(ChecklogModel checklogModel) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.app_name) + checklogModel.getCheckTime(), Toast.LENGTH_SHORT).show();

            }
        });

        binding.rcvChecklog.setAdapter(adapter);
        showLoading(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void showLoading(Boolean state) {
        binding.llChecklogNoResult.setVisibility(View.GONE);
        if(binding.checklogProgressBar == null) {
            Log.d("TES-progressBar", "NULL NULL NULL");
            return;
        }
        if (state) {
            binding.checklogProgressBar.setVisibility(View.VISIBLE);
            binding.rcvChecklog.setVisibility(View.GONE);
            binding.llData.setVisibility(View.GONE);
        } else {
            binding.checklogProgressBar.setVisibility(View.GONE);
            binding.rcvChecklog.setVisibility(View.VISIBLE);
            binding.llData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onUserLoggedOut() {
        Log.d("TES-LOGOUT", "onUserLoggedOut");
        //showLoading(false);
        Intent loginIntent = new Intent(this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        //detailIntent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
        startActivity(loginIntent);
    }
}
