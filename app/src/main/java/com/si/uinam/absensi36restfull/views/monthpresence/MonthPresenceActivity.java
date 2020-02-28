package com.si.uinam.absensi36restfull.views.monthpresence;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.si.uinam.absensi36restfull.LoginActivity;
import com.si.uinam.absensi36restfull.R;
import com.si.uinam.absensi36restfull.helpers.ApiHelper;
import com.si.uinam.absensi36restfull.helpers.ApiTool;
import com.si.uinam.absensi36restfull.models.HarianGroupModel;
import com.si.uinam.absensi36restfull.models.MonthPresenceModel;
import com.si.uinam.absensi36restfull.services.AuthenticationListener;
import com.si.uinam.absensi36restfull.viewmodels.MonthPresenceViewModel;
import com.si.uinam.absensi36restfull.views.profile.FotoFragment;

import java.util.ArrayList;
import java.util.Objects;

public class MonthPresenceActivity extends AppCompatActivity implements AuthenticationListener {

    public static final String EXTRA_HARIAN_GROUP_MODEL = "Harian_Group_Model";
    CollapsingToolbarLayout collapsingToolbar;
    private MonthPresenceViewModel monthPresenceViewModel;
    private ProgressBar progressBar;
    private MonthPresenceListAdapter monthPresenceListAdapter;
    private RecyclerView rcvPresence;
    private CircleImageView imgIdentity;
    private AppBarLayout appBarLayout;
    private CircleImageView imgBarIdentity;
    private TextView tvIdentity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_presence);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        collapsingToolbar = findViewById(R.id.collapsingToolbar);
        collapsingToolbar.setTitle("Squirrel");

        imgIdentity = findViewById(R.id.img_identity);
        progressBar = findViewById(R.id.presenceProgressBar);
        rcvPresence = findViewById(R.id.rcv_presence);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        appBarLayout = findViewById(R.id.appBarLyt);
        tvIdentity = findViewById(R.id.tv_bar_identity);



        HarianGroupModel harianGroupExtra = getIntent().getParcelableExtra(MonthPresenceActivity.EXTRA_HARIAN_GROUP_MODEL);

        getSupportActionBar().setTitle("Kehadiran " + harianGroupExtra.getNama());
        tvIdentity.setGravity(Gravity.CENTER);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    tvIdentity.setVisibility(View.VISIBLE);
                    tvIdentity.setText(harianGroupExtra.getNama());
                    getSupportActionBar().setTitle("");
                    isShow = true;
                } else if(isShow) {
                    tvIdentity.setVisibility(View.GONE);
                    tvIdentity.setText(harianGroupExtra.getNama());
                    getSupportActionBar().setTitle("Kehadiran " + harianGroupExtra.getNama());
                    isShow = false;
                }
            }
        });



        RequestOptions requestOptions = new RequestOptions().override(100, 100);
        requestOptions.placeholder(R.drawable.placeholder);
        requestOptions.error(R.drawable.user);
        String url = ApiHelper.getImgBaseUrl() + harianGroupExtra.getFoto();
        Log.d("RETROFIT-TEST-URL", url);
        Glide.with(this)
                .load(url)
                .apply(requestOptions)
                .into(imgIdentity);

        imgIdentity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                FotoFragment editNameDialogFragment = FotoFragment.newInstance("Some Title");
                editNameDialogFragment.setUrl(url);
                editNameDialogFragment.show(fm, "fragment_edit_name");
            }
        });

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
        String tgl = ApiTool.getTodayDateString(this);
        int[] naps = {1000000};
        naps[0] = Integer.valueOf(harianGroupExtra.getNap());
        monthPresenceViewModel.loadMonthPresenceList(this, this, tgl, naps);

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
