package com.si.uinam.absensi36restfull.views.group;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.si.uinam.absensi36restfull.LoginActivity;
import com.si.uinam.absensi36restfull.R;
import com.si.uinam.absensi36restfull.helpers.ApiTool;
import com.si.uinam.absensi36restfull.models.GroupModel;
import com.si.uinam.absensi36restfull.services.AuthenticationListener;
import com.si.uinam.absensi36restfull.viewmodels.GroupViewModel;
import com.si.uinam.absensi36restfull.views.identityparcel.IdentityGroup;
import com.si.uinam.absensi36restfull.views.identitywithpagelib.activity.IdentityWithPageActivity;

import java.util.ArrayList;

public class GroupFragment extends Fragment implements AuthenticationListener {

    private static final int REQUEST_CODE = 100;
    private GroupViewModel groupViewModel;
    private ProgressBar progressBar;
    private GroupListAdapter groupListAdapter;
    private RecyclerView rcvGroup;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        groupViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(GroupViewModel.class);
        groupViewModel.getGroupList().observe(this, new Observer<ArrayList<GroupModel>>() {
            @Override
            public void onChanged(ArrayList<GroupModel> groupModels) {
                if(groupModels != null){
                    groupListAdapter.setGroupList(groupModels);
                    showLoading(false);
                }
            }
        });

        groupViewModel.getErrorMessage().observe(this, new Observer<String>() {
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
        String tgl = ApiTool.getTodayDateString(getActivity());
        //((App)getActivity().getApplication()).setAuthenticationListener(this);
        //((App)context).setAuthenticationListener(this);
        groupViewModel.loadGroupList(getActivity(),this, tgl);
        //groupViewModel.loadGroupList(tgl);
        //groupViewModel.loadGroupList(new Date());
        Log.d("TES-DATE", tgl);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        groupViewModel =
                ViewModelProviders.of(this).get(GroupViewModel.class);
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        progressBar = view.findViewById(R.id.groupProgressBar);
        rcvGroup = view.findViewById(R.id.rcv_group);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);


        Log.d("TES-VIEW-MODEL", "1. assdd Connect internet API");
        rcvGroup.setLayoutManager(new LinearLayoutManager(getContext()));
        groupListAdapter = new GroupListAdapter();
        groupListAdapter.setTglString(ApiTool.getTodayDateString(getActivity()));
        groupListAdapter.setItemClickCallback(new GroupListAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(GroupModel groupModel, String identity, int color) {
                IdentityGroup identityGroup = new IdentityGroup();
                identityGroup.setGROUP_TYPE(IdentityGroup.TYPE.GROUP_IDENTITY);
                identityGroup.setGroup_id(groupModel.getId());
                identityGroup.setInfo(groupModel.getGrup());
                identityGroup.setInitial(identity);
                identityGroup.setColor(color);
                identityGroup.setIdentity(identity);
                Toast.makeText(getContext(), groupModel.getGrup(), Toast.LENGTH_SHORT).show();
                Intent identityIntentPagingLib = new Intent(getActivity(), IdentityWithPageActivity.class);
                identityIntentPagingLib.putExtra(IdentityWithPageActivity.EXTRA_IDENTITY, identityGroup);
                startActivity(identityIntentPagingLib);
            }
        });

        groupListAdapter.notifyDataSetChanged();
        rcvGroup.setAdapter(groupListAdapter);

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
            rcvGroup.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            rcvGroup.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TES-SUKSES", "login sukses");
        if(requestCode == GroupFragment.REQUEST_CODE){
            Log.d("TES-SUKSES", "request code passs");
            if(resultCode == 200){
                Log.d("TES-SUKSES", "");
                String name = data.getStringExtra(LoginActivity.EXTRA_NAME);
                Toast.makeText(getContext(), getResources().getString(R.string.app_name) + name, Toast.LENGTH_SHORT).show();
                String tgl = ApiTool.getTodayDateString(getActivity());
                //((App)getActivity().getApplication()).setAuthenticationListener(this);
                //((App)context).setAuthenticationListener(this);
                groupViewModel.loadGroupList(getActivity(),this, tgl);
            }
        }
    }

    @Override
    public void onUserLoggedOut() {

        Log.d("TES-LOGOUT", "onUserLoggedOut");
        //showLoading(false);
        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        //startActivity(loginIntent);
        startActivityForResult(loginIntent, REQUEST_CODE);
    }
}