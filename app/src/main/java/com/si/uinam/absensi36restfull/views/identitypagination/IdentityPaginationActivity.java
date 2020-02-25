package com.si.uinam.absensi36restfull.views.identitypagination;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.si.uinam.absensi36restfull.R;
import com.si.uinam.absensi36restfull.helpers.ApiTool;
import com.si.uinam.absensi36restfull.models.HarianGroupModel;
import com.si.uinam.absensi36restfull.models.PaginationModel;
import com.si.uinam.absensi36restfull.services.AuthenticationListener;
import com.si.uinam.absensi36restfull.views.identity.IdentityGroup;
import com.si.uinam.absensi36restfull.views.identitypagination.viewmodel.PagingIdentityViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class IdentityPaginationActivity extends AppCompatActivity implements AuthenticationListener {

    private static final String TAG = "IdentityPagination";
    public static final String EXTRA_IDENTITY = "extra_identity";
    CollapsingToolbarLayout collapsingToolbar;
    private PagingIdentityViewModel identityViewModel;
    private NestedScrollView nvData;
    private LinearLayout linearLayout;
    private Button btnNoResult;

    PaginationAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    RecyclerView rv;
    ProgressBar progressBar;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;
    private String tgl;
    private Context context;
    private AuthenticationListener authenticationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_pagination);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        collapsingToolbar = findViewById(R.id.collapsingToolbar);
        collapsingToolbar.setTitle("Tes");

        nvData = findViewById(R.id.nv_data);
        linearLayout = findViewById(R.id.ll_no_result);
        btnNoResult = findViewById(R.id.btn_no_result);
        btnNoResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onBackPressed();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.app_name) , Toast.LENGTH_SHORT).show();
            }
        });
        progressBar = findViewById(R.id.identityProgressBar);
        rv = (RecyclerView) findViewById(R.id.rcvp_identity);

        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);

        final IdentityGroup identityGroup = getIntent().getParcelableExtra(IdentityPaginationActivity.EXTRA_IDENTITY);

        getSupportActionBar().setTitle("Pegawai " + identityGroup.getInfo());

        Log.d("RETROFIT-PAGE", "ADA CREATE AKU ss");

        adapter = new PaginationAdapter(this);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);

        rv.setItemAnimator(new DefaultItemAnimator());

        rv.setAdapter(adapter);

        rv.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                Log.d("PAGING-LOADMORE", "load more: ");
                identityViewModel.loadNextPage(context, authenticationListener, tgl, identityGroup, currentPage );
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        showLoading(true);
        identityViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(PagingIdentityViewModel.class);
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
        identityViewModel.getPagingFirstPage().observe(this, new Observer<PaginationModel<HarianGroupModel>>() {
            @Override
            public void onChanged(PaginationModel<HarianGroupModel> harianGroupModelPaginationModel) {
                ArrayList<HarianGroupModel> harianGroupModels = harianGroupModelPaginationModel.getArrayData();
                TOTAL_PAGES = harianGroupModelPaginationModel.getLastPage();
                currentPage = harianGroupModelPaginationModel.getCurrentPage();
                showLoading(false);
                if(harianGroupModels != null){
                    adapter.addAll(harianGroupModels);
                    //identityListAdapter.setHarianGroupList(harianGroupModels);

                    if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
                    else isLastPage = true;
                    if(harianGroupModels.size()==0){
                        linearLayout.setVisibility(View.VISIBLE);
                    }

                }
            }
        });

        identityViewModel.getPagingHarianGrupList().observe(this, new Observer<PaginationModel<HarianGroupModel>>() {
            @Override
            public void onChanged(PaginationModel<HarianGroupModel> harianGroupModelPaginationModel) {
                ArrayList<HarianGroupModel> harianGroupModels = harianGroupModelPaginationModel.getArrayData();
                TOTAL_PAGES = harianGroupModelPaginationModel.getTotal();
                currentPage = harianGroupModelPaginationModel.getCurrentPage();
                showLoading(false);
                if(harianGroupModels != null){
                    adapter.removeLoadingFooter();
                    isLoading = false;
                    adapter.addAll(harianGroupModels);
                    //identityListAdapter.setHarianGroupList(harianGroupModels);
                    if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                    else isLastPage = true;
                    if(harianGroupModels.size()==0){
                        linearLayout.setVisibility(View.VISIBLE);
                    }

                }
            }
        });

        tgl = ApiTool.getTodayDateString(this);
        context = this;
        authenticationListener = this;
        identityViewModel.loadFirstPage(this, this,tgl, identityGroup);
        Log.d("RETROFIT-PAGE", "ADA FIRST AKU ss");

    }




    private void showLoading(Boolean state) {
        linearLayout.setVisibility(View.GONE);
        if(progressBar == null) {
            Log.d("TES-progressBar", "NULL NULL NULL");
            return;
        }
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);
            nvData.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
            nvData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onUserLoggedOut() {

    }
}
