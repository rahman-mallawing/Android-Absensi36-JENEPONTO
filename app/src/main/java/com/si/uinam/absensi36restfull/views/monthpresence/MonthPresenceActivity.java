package com.si.uinam.absensi36restfull.views.monthpresence;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.si.uinam.absensi36restfull.models.MonthPresenceModel;
import com.si.uinam.absensi36restfull.services.AuthenticationListener;
import com.si.uinam.absensi36restfull.viewmodels.MonthPresenceViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class MonthPresenceActivity extends AppCompatActivity implements AuthenticationListener {

    public static final String EXTRA_HARIAN_GROUP_MODEL = "Harian_Group_Model";
    CollapsingToolbarLayout collapsingToolbar;
    private MonthPresenceViewModel monthPresenceViewModel;
    private ProgressBar progressBar;
    private MonthPresenceListAdapter monthPresenceListAdapter;
    private RecyclerView rcvPresence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_presence);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        collapsingToolbar = findViewById(R.id.collapsingToolbar);
        collapsingToolbar.setTitle("Squirrel");

        progressBar = findViewById(R.id.presenceProgressBar);
        rcvPresence = findViewById(R.id.rcv_presence);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);

        HarianGroupModel harianGroupExtra = getIntent().getParcelableExtra(MonthPresenceActivity.EXTRA_HARIAN_GROUP_MODEL);
        rcvPresence.setLayoutManager(new LinearLayoutManager(this));
        monthPresenceListAdapter = new MonthPresenceListAdapter();
        monthPresenceListAdapter.setItemClickCallback(new MonthPresenceListAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MonthPresenceModel monthPresenceModel) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.app_name) + monthPresenceModel.getNama(), Toast.LENGTH_SHORT).show();
            }
        });
        monthPresenceListAdapter.notifyDataSetChanged();
        rcvPresence.setAdapter(monthPresenceListAdapter);
        showLoading(true);
        monthPresenceViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MonthPresenceViewModel.class);
        monthPresenceViewModel.getErrorMessage().observe(this, new Observer<String>() {
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
        monthPresenceViewModel.getMonthPresenceList().observe(this, new Observer<ArrayList<MonthPresenceModel>>() {
            @Override
            public void onChanged(ArrayList<MonthPresenceModel> monthPresenceModels) {
                if(monthPresenceModels != null){
                    monthPresenceListAdapter.setMonthPresenceModels(monthPresenceModels);
                    showLoading(false);
                }
            }
        });
        String tgl = ApiTool.getTodayDateString();
        int[] naps = {1000000};
        naps[0] = Integer.valueOf(harianGroupExtra.getNap());
        monthPresenceViewModel.loadMonthPresenceList(this, this, tgl, naps);

    }

    private void showLoading(Boolean state) {
        if(progressBar == null) {
            Log.d("TES-progressBar", "NULL NULL NULL");
            return;
        }
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            rcvPresence.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            rcvPresence.setVisibility(View.VISIBLE);
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
