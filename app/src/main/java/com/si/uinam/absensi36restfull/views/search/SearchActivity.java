package com.si.uinam.absensi36restfull.views.search;

import androidx.appcompat.app.AppCompatActivity;
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

import com.si.uinam.absensi36restfull.LoginActivity;
import com.si.uinam.absensi36restfull.R;
import com.si.uinam.absensi36restfull.models.HarianGroupModel;
import com.si.uinam.absensi36restfull.models.IdentityModel;
import com.si.uinam.absensi36restfull.models.PaginationModel;
import com.si.uinam.absensi36restfull.services.AuthenticationListener;
import com.si.uinam.absensi36restfull.viewmodels.search.SearchViewModel;
import com.si.uinam.absensi36restfull.views.monthpresence.MonthPresenceActivity;

public class SearchActivity extends AppCompatActivity implements AuthenticationListener {

    public static final String EXTRA_SEARCH = "SEARCH_QUERY";
    private SearchViewModel searchViewModel;
    private ProgressBar progressBar;
    private SearchListAdapter searchListAdapter;
    private RecyclerView rcvSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        progressBar = findViewById(R.id.searchProgressBar);
        rcvSearch = findViewById(R.id.rcv_search);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);

        String query = getIntent().getStringExtra(SearchActivity.EXTRA_SEARCH);
        rcvSearch.setLayoutManager(new LinearLayoutManager(this));
        searchListAdapter = new SearchListAdapter();
        searchListAdapter.setItemClickCallback(new SearchListAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(IdentityModel identityModel) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.app_name) + identityModel.getName(), Toast.LENGTH_SHORT).show();
                HarianGroupModel harianGroupModel = new HarianGroupModel();
                harianGroupModel.setNap(identityModel.getNap());
                harianGroupModel.setNama(identityModel.getName());
                harianGroupModel.setFoto(identityModel.getFoto());
                harianGroupModel.setGroupId(identityModel.getGrupId());
                Intent presenceIntent = new Intent(SearchActivity.this, MonthPresenceActivity.class);
                presenceIntent.putExtra(MonthPresenceActivity.EXTRA_HARIAN_GROUP_MODEL, harianGroupModel);
                startActivity(presenceIntent);
            }
        });
        searchListAdapter.notifyDataSetChanged();
        rcvSearch.setAdapter(searchListAdapter);
        showLoading(true);
        searchViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SearchViewModel.class);
        searchViewModel.getErrorMessage().observe(this, new Observer<String>() {
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
        searchViewModel.getIdentityPaginationData().observe(this, new Observer<PaginationModel<IdentityModel>>() {
            @Override
            public void onChanged(PaginationModel<IdentityModel> identityModelPaginationModel) {
                Toast toast = Toast.makeText(getApplicationContext(), identityModelPaginationModel.getPath(), Toast.LENGTH_LONG);
                Log.d("TES-SEARCH", identityModelPaginationModel.getPath());
                if(identityModelPaginationModel != null){
                    searchListAdapter.setIdentityList(identityModelPaginationModel.getArrayData());
                    Log.d("TES-SEARCH", identityModelPaginationModel.getArrayData().toString());
                    showLoading(false);
                }
            }
        });

        searchViewModel.loadPaginationIdentityData(this, this, query, 1);
    }

    private void showLoading(Boolean state) {
        if(progressBar == null) {
            Log.d("TES-progressBar", "NULL NULL NULL");
            return;
        }
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            rcvSearch.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            rcvSearch.setVisibility(View.VISIBLE);
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
