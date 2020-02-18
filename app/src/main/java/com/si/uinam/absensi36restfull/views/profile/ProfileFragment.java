package com.si.uinam.absensi36restfull.views.profile;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.si.uinam.absensi36restfull.R;
import com.si.uinam.absensi36restfull.helpers.ApiHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends DialogFragment {

    private Button btnOk;
    private TextView tvName;
    private TextView tvEmail;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String title) {
        ProfileFragment frag = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnOk = (Button) view.findViewById(R.id.btn_ok);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvEmail = (TextView) view.findViewById(R.id.tv_email);

        tvName.setText(ApiHelper.getApiName(getActivity()));
        tvEmail.setText(ApiHelper.getApiEmail(getActivity()));
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        String title = getArguments().getString("title", "Profile");
        getDialog().setTitle(title);
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

}
