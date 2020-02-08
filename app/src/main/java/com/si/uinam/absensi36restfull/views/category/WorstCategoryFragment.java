package com.si.uinam.absensi36restfull.views.category;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.si.uinam.absensi36restfull.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorstCategoryFragment extends Fragment {


    public WorstCategoryFragment() {
        // Required empty public constructor
    }

    public static WorstCategoryFragment newInstance() {
        Log.d("FRAGMENT-CREATE", "onResume Movie");
        WorstCategoryFragment fragment = new WorstCategoryFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_worst_category, container, false);
    }

}
