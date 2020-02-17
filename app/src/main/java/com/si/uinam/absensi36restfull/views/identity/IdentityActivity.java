package com.si.uinam.absensi36restfull.views.identity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.si.uinam.absensi36restfull.LoginActivity;
import com.si.uinam.absensi36restfull.R;
import com.si.uinam.absensi36restfull.helpers.ApiTool;
import com.si.uinam.absensi36restfull.models.HarianGroupModel;
import com.si.uinam.absensi36restfull.services.AuthenticationListener;
import com.si.uinam.absensi36restfull.viewmodels.identity.IdentityViewModel;
import com.si.uinam.absensi36restfull.views.monthpresence.MonthPresenceActivity;

import java.util.ArrayList;
import java.util.Objects;

public class IdentityActivity extends AppCompatActivity implements AuthenticationListener {

    public static final String EXTRA_IDENTITY = "extra_identity";
    CollapsingToolbarLayout collapsingToolbar;
    private IdentityViewModel identityViewModel;
    private ProgressBar progressBar;
    private IdentityListAdapter identityListAdapter;
    private RecyclerView rcvIdentity;
    private NestedScrollView nvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        collapsingToolbar = findViewById(R.id.collapsingToolbar);
        collapsingToolbar.setTitle("Squirrel");

        nvData = findViewById(R.id.nv_data);
        progressBar = findViewById(R.id.identityProgressBar);
        rcvIdentity = findViewById(R.id.rcv_identity);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);

        IdentityGroup identityGroup = getIntent().getParcelableExtra(IdentityActivity.EXTRA_IDENTITY);

        getSupportActionBar().setTitle("Pegawai " + identityGroup.getInfo());

        Log.d("identityGroup", identityGroup.getInfo()+" : "+ identityGroup.getGroup_id());
        rcvIdentity.setLayoutManager(new LinearLayoutManager(this));
        identityListAdapter = new IdentityListAdapter();
        identityListAdapter.setItemClickCallback(new IdentityListAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(HarianGroupModel harianGroupModel) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.app_name) + harianGroupModel.getGrup(), Toast.LENGTH_SHORT).show();
                Intent presenceIntent = new Intent(IdentityActivity.this, MonthPresenceActivity.class);
                presenceIntent.putExtra(MonthPresenceActivity.EXTRA_HARIAN_GROUP_MODEL, harianGroupModel);
                startActivity(presenceIntent);
            }
        });
        identityListAdapter.notifyDataSetChanged();
        rcvIdentity.setAdapter(identityListAdapter);
        showLoading(true);
        identityViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(IdentityViewModel.class);
        identityViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                String msg = "Error: " + s;
                Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
                View vi = toast.getView();
                vi.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                TextView text = vi.findViewById(android.R.id.message);
                text.setTextColor(Color.WHITE);
                toast.show();
                showLoading(false);
            }
        });
        identityViewModel.getHarianGrupList().observe(this, new Observer<ArrayList<HarianGroupModel>>() {
            @Override
            public void onChanged(ArrayList<HarianGroupModel> harianGroupModels) {
                if(harianGroupModels != null){
                    identityListAdapter.setHarianGroupList(harianGroupModels);
                    showLoading(false);
                }
            }
        });

        String tgl = ApiTool.getTodayDateString();
        if(identityGroup.getGROUP_TYPE()==IdentityGroup.TYPE.GROUP_IDENTITY){
            identityViewModel.loadHarianGrupList(this, this, identityGroup.getGroup_id(), tgl);
        }else if(identityGroup.getGROUP_TYPE()==IdentityGroup.TYPE.PRESENCE_IDENTITY){
            identityViewModel.loadHarianAbsenList(this, this, identityGroup.getSts_kehadiran(), tgl);
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void showLoading(Boolean state) {
        if(progressBar == null) {
            Log.d("TES-progressBar", "NULL NULL NULL");
            return;
        }
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            rcvIdentity.setVisibility(View.GONE);
            nvData.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            rcvIdentity.setVisibility(View.VISIBLE);
            nvData.setVisibility(View.VISIBLE);
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
