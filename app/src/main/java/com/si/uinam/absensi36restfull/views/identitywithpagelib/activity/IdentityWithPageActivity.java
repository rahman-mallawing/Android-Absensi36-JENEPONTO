package com.si.uinam.absensi36restfull.views.identitywithpagelib.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.si.uinam.absensi36restfull.LoginActivity;
import com.si.uinam.absensi36restfull.R;
import com.si.uinam.absensi36restfull.databinding.IdentityActivityBinding;
import com.si.uinam.absensi36restfull.helpers.ApiTool;
import com.si.uinam.absensi36restfull.models.HarianGroupModel;
import com.si.uinam.absensi36restfull.services.AuthenticationListener;
import com.si.uinam.absensi36restfull.views.identityparcel.IdentityGroup;
import com.si.uinam.absensi36restfull.views.identitywithpagelib.AppController;
import com.si.uinam.absensi36restfull.views.identitywithpagelib.adapter.IdentityPageListAdapter;
import com.si.uinam.absensi36restfull.views.identitywithpagelib.utils.NetworkState;
import com.si.uinam.absensi36restfull.views.identitywithpagelib.viewmodel.IdentityPageViewModel;
import com.si.uinam.absensi36restfull.views.monthpresence.MonthPresenceActivity;

import java.util.Objects;

//private IdentityActivityBinding;

public class IdentityWithPageActivity extends AppCompatActivity implements AuthenticationListener {

    public static final String EXTRA_IDENTITY = "extra_identity";
    private IdentityActivityBinding binding;
    private IdentityPageViewModel identityPageViewModel;
    private IdentityPageListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_identity_with_page);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_identity_with_page);

        IdentityGroup identityGroup = getIntent().getParcelableExtra(IdentityWithPageActivity.EXTRA_IDENTITY);

        getSupportActionBar().setTitle("Pegawai " + identityGroup.getInfo());
        binding.tvBarInfo.setText(identityGroup.getInfo());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ((GradientDrawable) binding.tvIdentity.getBackground()).setColor(identityGroup.getColor());
        binding.tvIdentity.setText(identityGroup.getInitial());

        binding.appBarLyt.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    binding.tvBarInfo.setVisibility(View.VISIBLE);
                    getSupportActionBar().setTitle(identityGroup.getInfo());
                    isShow = true;
                } else if(isShow) {
                    binding.tvBarInfo.setVisibility(View.GONE);
                    getSupportActionBar().setTitle("Pegawai " + identityGroup.getInfo());
                    isShow = false;
                }
            }
        });

        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        binding.btnNoResult.setOnClickListener(view -> {
            //Toast.makeText(getApplicationContext(), getResources().getString(R.string.app_name) , Toast.LENGTH_SHORT).show();
            onSupportNavigateUp();
        });

        binding.identityProgressBar.setVisibility(View.VISIBLE);
        binding.identityProgressBar.setIndeterminate(true);

        AppController appController = AppController.create(this, this);
        appController.setIdentityGroup(identityGroup);
        appController.setTgl(ApiTool.getTodayDateString(this));

        identityPageViewModel = new IdentityPageViewModel(appController);
        binding.rcvIdentity.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new IdentityPageListAdapter(getApplicationContext());

        identityPageViewModel.getArticleLiveData().observe(this, pageList -> {
            Log.d("TES-HASIL", pageList.toString());
            if(pageList.isEmpty()){
                //binding.llNoResult.setVisibility(View.VISIBLE);
            }
            adapter.submitList(pageList);
        });

        identityPageViewModel.getNetworkState().observe(this, networkState -> {

            if(networkState == NetworkState.LOADED){
                showLoading(false);
            }else if (networkState == NetworkState.EMPTY_LOADED){
                showLoading(false);
                binding.llNoResult.setVisibility(View.VISIBLE);
            }
            adapter.setNetworkState(networkState);
        });

        adapter.setItemClickCallback(new IdentityPageListAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(HarianGroupModel harianGroupModel) {
                Toast.makeText(getApplicationContext(), harianGroupModel.getGrup(), Toast.LENGTH_SHORT).show();
                Intent presenceIntent = new Intent(IdentityWithPageActivity.this, MonthPresenceActivity.class);
                presenceIntent.putExtra(MonthPresenceActivity.EXTRA_HARIAN_GROUP_MODEL, harianGroupModel);
                startActivity(presenceIntent);
            }
        });

        binding.rcvIdentity.setAdapter(adapter);
        showLoading(true);
        //binding.llNoResult.setVisibility(View.GONE);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void showLoading(Boolean state) {
        binding.llNoResult.setVisibility(View.GONE);
        if(binding.identityProgressBar == null) {
            Log.d("TES-progressBar", "NULL NULL NULL");
            return;
        }
        if (state) {
            binding.identityProgressBar.setVisibility(View.VISIBLE);
            binding.rcvIdentity.setVisibility(View.GONE);
            binding.clData.setVisibility(View.GONE);
        } else {
            binding.identityProgressBar.setVisibility(View.GONE);
            binding.rcvIdentity.setVisibility(View.VISIBLE);
            binding.clData.setVisibility(View.VISIBLE);
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
