package com.si.uinam.absensi36restfull.views.profile;


import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.si.uinam.absensi36restfull.R;
import com.si.uinam.absensi36restfull.helpers.ApiHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class FotoFragment extends DialogFragment {

    public static final String EXTRA_URL_FOTO = "extra_url_foto";
    private ImageView imgFoto;
    private String url;

    public FotoFragment() {
        // Required empty public constructor
    }

    public static FotoFragment newInstance(String title) {
        FotoFragment frag = new FotoFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_foto, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgFoto = (ImageView) view.findViewById(R.id.img_foto);
        imgFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        RequestOptions requestOptions = new RequestOptions().override(800, 1024);
        requestOptions.placeholder(R.drawable.placeholder);
        requestOptions.error(R.drawable.user);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        Log.d("RETROFIT-TEST-URL", url);
        Glide.with(this)
                .load(url)
                .apply(requestOptions)
                .into(imgFoto);
        String title = getArguments().getString("title", "Foto Profile");
        getDialog().setTitle(title);
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}
