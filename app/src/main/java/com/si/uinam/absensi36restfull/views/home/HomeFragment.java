package com.si.uinam.absensi36restfull.views.home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import de.hdodenhof.circleimageview.CircleImageView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import com.si.uinam.absensi36restfull.LoginActivity;
import com.si.uinam.absensi36restfull.MyMarkerView;
import com.si.uinam.absensi36restfull.R;
import com.si.uinam.absensi36restfull.helpers.ApiHelper;
import com.si.uinam.absensi36restfull.helpers.ApiTool;
import com.si.uinam.absensi36restfull.models.StaBulananTahunModel;
import com.si.uinam.absensi36restfull.models.StaHarianBulanModel;
import com.si.uinam.absensi36restfull.models.StatistikModel;
import com.si.uinam.absensi36restfull.services.App;
import com.si.uinam.absensi36restfull.services.AuthenticationListener;
import com.si.uinam.absensi36restfull.viewmodels.home.HomeViewModel;
import com.si.uinam.absensi36restfull.views.checklogpage.ChecklogActivity;
import com.si.uinam.absensi36restfull.views.identity.IdentityActivity;
import com.si.uinam.absensi36restfull.views.identity.IdentityGroup;
import com.si.uinam.absensi36restfull.views.identitypagination.IdentityPaginationActivity;
import com.si.uinam.absensi36restfull.views.identitywithpagelib.activity.IdentityWithPageActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements AuthenticationListener, View.OnClickListener {

    private static final int REQUEST_CODE = 100;
    private HomeViewModel homeViewModel;
    private ProgressBar homeProgressBar;
    private ScrollView svContainer;
    private TextView tvPegawai, tvHadir, tvDinas, tvTak, tvCuti, tvIzin, tvSakit, tvTerlambat, tvCp, tvGroup;
    private PieChart chartPie;
    private LineChart chartLine;
    private BarChart chart;
    private CircleImageView cvTakBtn, cvHadirBtn, cvDinasBtn, cvCutiBtn, cvIzinBtn, cvSakitBtn, cvLainLainBtn, cvChecklogBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(HomeViewModel.class);
        homeViewModel.getStatistikHarian().observe(this, new Observer<StatistikModel>() {
            @Override
            public void onChanged(StatistikModel statistikModel) {
                fillStatistik(statistikModel);
                fillPieChart(statistikModel);
                showLoading(false);
            }
        });
        homeViewModel.getStaBulananTahunList().observe(this, new Observer<ArrayList<StaBulananTahunModel>>() {
            @Override
            public void onChanged(ArrayList<StaBulananTahunModel> staBulananTahunModels) {
                fillBarChart(staBulananTahunModels);
                showLoading(false);
            }
        });
        homeViewModel.getStaHarianBulanList().observe(this, new Observer<ArrayList<StaHarianBulanModel>>() {
            @Override
            public void onChanged(ArrayList<StaHarianBulanModel> staHarianBulanModels) {
                fillLineChart(staHarianBulanModels);
                showLoading(false);
            }
        });
        homeViewModel.getErrorMessage().observe(this, new Observer<String>() {
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
        Log.d("LOG-USER","CEK LOGIN:home fragment activity");
        if(!ApiHelper.isLogged(getActivity())){
            onUserLoggedOut();
        }else {
            homeViewModel.loadStatistik(getActivity(),this, ApiTool.getTodayDateString(getActivity()));
        }

        //chartLine = findViewById(R.id.chart);
//


    }

    private void fillStatistik(StatistikModel statistikModel) {
        tvPegawai.setText(String.valueOf(statistikModel.getJumlahPegawai()));
        tvHadir.setText(String.valueOf(statistikModel.getHadir()));
        if(statistikModel.getHadir()>0){
            tvHadir.setTextColor(getResources().getColor(R.color.colorHadir));
        }
        tvDinas.setText(String.valueOf(statistikModel.getDinasLuar()));
        if(statistikModel.getDinasLuar()>0){
            tvDinas.setTextColor(getResources().getColor(R.color.colorDinas));
        }
        tvTak.setText(String.valueOf(statistikModel.getAbsen()));
        if(statistikModel.getAbsen()>0){
            tvTak.setTextColor(getResources().getColor(R.color.colorTAK));
        }
        tvCuti.setText(String.valueOf(statistikModel.getCuti()));
        if(statistikModel.getCuti()>0){
            tvCuti.setTextColor(getResources().getColor(R.color.colorCuti));
        }
        tvIzin.setText(String.valueOf(statistikModel.getIzin()));
        if(statistikModel.getIzin()>0){
            tvIzin.setTextColor(getResources().getColor(R.color.colorIzin));
        }
        tvSakit.setText(String.valueOf(statistikModel.getSakit()));
        if(statistikModel.getSakit()>0){
            tvSakit.setTextColor(getResources().getColor(R.color.colorSakit));
        }
        tvTerlambat.setText(String.valueOf(statistikModel.getJumlahTerlambat()));
        if(statistikModel.getJumlahTerlambat()>0){
            tvTerlambat.setTextColor(getResources().getColor(R.color.colorTAK));
        }
        tvCp.setText(String.valueOf(statistikModel.getJumlahCp()));
        if(statistikModel.getJumlahCp()>0){
            tvCp.setTextColor(getResources().getColor(R.color.colorTAK));
        }
        tvGroup.setText(String.valueOf(statistikModel.getJumlahGrup()));

        ((GradientDrawable) tvPegawai.getBackground()).setColor(Color.LTGRAY);
        ((GradientDrawable) tvHadir.getBackground()).setColor(Color.LTGRAY);
        ((GradientDrawable) tvTak.getBackground()).setColor(Color.LTGRAY);
        ((GradientDrawable) tvTerlambat.getBackground()).setColor(Color.LTGRAY);
        ((GradientDrawable) tvDinas.getBackground()).setColor(Color.LTGRAY);
        ((GradientDrawable) tvCuti.getBackground()).setColor(Color.LTGRAY);
        ((GradientDrawable) tvIzin.getBackground()).setColor(Color.LTGRAY);
        ((GradientDrawable) tvSakit.getBackground()).setColor(Color.LTGRAY);
        ((GradientDrawable) tvCp.getBackground()).setColor(Color.LTGRAY);
        ((GradientDrawable) tvGroup.getBackground()).setColor(Color.LTGRAY);
    }

    private void setVIewStatistikBackground(){
        ((GradientDrawable) tvPegawai.getBackground()).setColor(Color.LTGRAY);
        ((GradientDrawable) tvHadir.getBackground()).setColor(Color.LTGRAY);
        ((GradientDrawable) tvTak.getBackground()).setColor(Color.LTGRAY);
        ((GradientDrawable) tvTerlambat.getBackground()).setColor(Color.LTGRAY);
        ((GradientDrawable) tvDinas.getBackground()).setColor(Color.LTGRAY);
        ((GradientDrawable) tvCuti.getBackground()).setColor(Color.LTGRAY);
        ((GradientDrawable) tvIzin.getBackground()).setColor(Color.LTGRAY);
        ((GradientDrawable) tvSakit.getBackground()).setColor(Color.LTGRAY);
        ((GradientDrawable) tvCp.getBackground()).setColor(Color.LTGRAY);
        ((GradientDrawable) tvGroup.getBackground()).setColor(Color.LTGRAY);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        svContainer = root.findViewById(R.id.sv_mnu);
        tvPegawai = root.findViewById(R.id.tv_pegawai);
        tvHadir = root.findViewById(R.id.tv_hadir);
        tvDinas = root.findViewById(R.id.tv_dinas);
        tvTak = root.findViewById(R.id.tv_tak);
        tvCuti = root.findViewById(R.id.tv_cuti);
        tvIzin = root.findViewById(R.id.tv_izin);
        tvSakit = root.findViewById(R.id.tv_sakit);
        tvTerlambat = root.findViewById(R.id.tv_terlambat);
        tvCp = root.findViewById(R.id.tv_cp);
        tvGroup = root.findViewById(R.id.tv_group);

        setVIewStatistikBackground();

        cvTakBtn = root.findViewById(R.id.cv_mnu_tak);
        cvTakBtn.setOnClickListener(this);
        cvHadirBtn = root.findViewById(R.id.cv_mnu_hadir);
        cvHadirBtn.setOnClickListener(this);

        cvDinasBtn = root.findViewById(R.id.cv_mnu_dinas);
        cvDinasBtn.setOnClickListener(this);

        cvCutiBtn = root.findViewById(R.id.cv_mnu_cuti);
        cvCutiBtn.setOnClickListener(this);

        cvIzinBtn = root.findViewById(R.id.cv_mnu_izin);
        cvIzinBtn.setOnClickListener(this);

        cvSakitBtn = root.findViewById(R.id.cv_mnu_sakit);
        cvSakitBtn.setOnClickListener(this);

        cvLainLainBtn = root.findViewById(R.id.cv_mnu_lain_lain);
        cvLainLainBtn.setOnClickListener(this);

        cvChecklogBtn = root.findViewById(R.id.cv_mnu_checklog);
        cvChecklogBtn.setOnClickListener(this);


        homeProgressBar = root.findViewById(R.id.homeProgressBar);
        chart = root.findViewById(R.id.bar_chart);
        chartLine = root.findViewById(R.id.chart_line);
        chartPie = root.findViewById(R.id.chart_pie);


        return root;
    }

    public void renderData(ArrayList<StaHarianBulanModel> staHarianBulanModels) {
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        XAxis xAxis = chartLine.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setAxisMaximum(31f);
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawLimitLinesBehindData(true);

        float jumPegawai = (float)staHarianBulanModels.get(0).getJumlahPegawai();

        LimitLine ll1 = new LimitLine(jumPegawai, "Maximum Limit");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);

        LimitLine ll2 = new LimitLine((float)0.2f*jumPegawai, "Minimum Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);

        YAxis leftAxis = chartLine.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.setAxisMaximum(jumPegawai+50f);
        leftAxis.setAxisMinimum(-(float)0.3f*jumPegawai);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawLimitLinesBehindData(false);

        chartLine.getAxisRight().setEnabled(false);
        setData(staHarianBulanModels);
    }

    private void setData(ArrayList<StaHarianBulanModel> staHarianBulanModels) {

        ArrayList<Entry> values = new ArrayList<>();
        Entry c1e1;
        for(StaHarianBulanModel item : staHarianBulanModels){
            c1e1 = new Entry(item.getDayOfMonth(), (float) item.getHadir());
            values.add(c1e1);
        }
        /*
        values.add(new Entry(1, 50));
        values.add(new Entry(2, 100));
        values.add(new Entry(3, 80));
        values.add(new Entry(4, 120));
        values.add(new Entry(5, 110));
        values.add(new Entry(7, 150));
        values.add(new Entry(8, 250));
        values.add(new Entry(9, 190));
         */

        LineDataSet set1;
        if (chartLine.getData() != null &&
                chartLine.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chartLine.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chartLine.getData().notifyDataChanged();
            chartLine.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "Jumlah hadir per hari");
            set1.setDrawIcons(false);
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.DKGRAY);
            set1.setCircleColor(Color.DKGRAY);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);
            Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.fade_blue);
            //set1.setFillDrawable(drawable);
            set1.setFillColor(Color.LTGRAY);
            if (Utils.getSDKInt() >= 18) {
                 drawable = ContextCompat.getDrawable(getActivity(), R.drawable.fade_blue);
                set1.setFillDrawable(drawable);
            } else {
                //set1.setFillColor(Color.DKGRAY);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);

            Description dc = chartLine.getDescription();
            dc.setText("Kehadiran harian per bulan");
            dc.setTextAlign(Paint.Align.CENTER);
            int width = chartLine.getWidth(); int height = chartLine.getHeight();
            dc.setPosition(width/2,height-100);
            //chartLine.setDescription(dc);
            chartLine.setData(data);
            chartLine.invalidate();
        }
    }


    private void fillBarChart(ArrayList<StaBulananTahunModel> staBulananTahunModels){

        float barWidth;
        float barSpace;
        float groupSpace;

        barWidth = 0.3f;
        barSpace = 0f;
        groupSpace = 0.2f;
        chart.setDescription(null);
        chart.setPinchZoom(false);
        chart.setScaleEnabled(false);
        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);


        int groupCount = 12;

        ArrayList xVals = new ArrayList();

        xVals.add("Jan");
        xVals.add("Feb");
        xVals.add("Mar");
        xVals.add("Apr");
        xVals.add("May");
        xVals.add("Jun");
        xVals.add("Jul");
        xVals.add("Aug");
        xVals.add("Sep");
        xVals.add("Oct");
        xVals.add("Nov");
        xVals.add("Dec");

        ArrayList yVals1 = new ArrayList();
        ArrayList yVals2 = new ArrayList();
        ArrayList yVals3 = new ArrayList();

        for(int i=0; i<staBulananTahunModels.size(); i++){
            int jumPeg = staBulananTahunModels.get(i).getJumlahPegawai();
            int jumHari = staBulananTahunModels.get(i).getJumlahHari();
            //yVals1.add(new BarEntry(i, (float) jumPeg));
            yVals2.add(new BarEntry(i, (float) staBulananTahunModels.get(i).getHadirXPegawai()/jumHari));
            yVals3.add(new BarEntry(i, (float) staBulananTahunModels.get(i).getAbsenXPegawai()/jumHari));
        }

        BarDataSet set1, set2, set3;
        //set1 = new BarDataSet(yVals1, "JumPegawai");
        //set1.setColor(Color.BLUE);
        set2 = new BarDataSet(yVals2, "Rata-rataHadir");
        set2.setColor(Color.rgb(0, 155, 0));
        set3 = new BarDataSet(yVals3, "Rata-rataAbsen");
        set3.setColor(Color.RED);
        BarData data = new BarData(set2, set3);
        data.setValueFormatter(new LargeValueFormatter());
        chart.setData(data);
        chart.getBarData().setBarWidth(barWidth);
        chart.getXAxis().setAxisMinimum(0);
        chart.getXAxis().setAxisMaximum(0 + chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        //chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xVals));
        chart.groupBars(0, groupSpace, barSpace);
        chart.getData().setHighlightEnabled(false);
        chart.invalidate();


        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        l.setYOffset(20f);
        l.setXOffset(0f);
        l.setYEntrySpace(0f);
        l.setTextSize(6f);


        //X-axis
        XAxis xAxis = chart.getXAxis();
        //xAxis.setGranularity(1f);
        xAxis.setLabelRotationAngle(+90);
        xAxis.setTextSize(6f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(xVals.size());
        //xAxis.setGranularity(1f);
        //xAxis.setAxisMaximum(13);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
        Log.d("BAR",String.valueOf(chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount));
//Y-axis
        chart.getAxisRight().setEnabled(false);
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(true);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f);


    }

    private void fillLineChart(ArrayList<StaHarianBulanModel> staHarianBulanModels){

        chartLine.setTouchEnabled(false);
        chartLine.setPinchZoom(true);
        chartLine.setScaleEnabled(false);

        MyMarkerView mv = new MyMarkerView(getActivity().getApplicationContext(), R.layout.custom_marker_view);
        mv.setChartView(chartLine);
        chartLine.setMarker(mv);

        renderData(staHarianBulanModels);
    }

    private void fillPieChart(StatistikModel stm){
        float hadir, absen, dinas, cuti, izin, terlambat, total;
        hadir = stm.getHadir();
        absen = stm.getAbsen();
        dinas = stm.getDinasLuar();
        cuti = stm.getCuti();
        izin = stm.getIzin();
        terlambat = stm.getJumlahTerlambat();
        total = hadir+absen+dinas+cuti+izin+terlambat;
        PieEntry pe = new PieEntry((hadir/total*100), "Hadir");
        Log.d("TOTAL", Float.toString(total));

        //List<PieEntry> entriesPie = new ArrayList<>();
        ArrayList<PieEntry> entriesPie = new ArrayList<>();
        entriesPie.add(new PieEntry((absen/total*100), "TAK"));
        entriesPie.add(new PieEntry((cuti/total*100), "Cuti"));
        entriesPie.add(new PieEntry((terlambat/total*100), "Terlambat"));
        entriesPie.add(new PieEntry((dinas/total*100), "Dinas"));
        entriesPie.add(pe);
        entriesPie.add(new PieEntry((izin/total*100), "Izin"));
        PieDataSet setPie = new PieDataSet(entriesPie, "Kehadiran");
        setPie.setColors(ColorTemplate.COLORFUL_COLORS);
        //setPie.set
        PieData dataPie = new PieData();
        dataPie.addDataSet(setPie);
        dataPie.setValueTextColor(Color.WHITE);
        dataPie.setValueFormatter(new PercentFormatter());
        chartPie.setDescription(null);
        chartPie.setData(dataPie);
        chartPie.invalidate();
    }

    private void showLoading(Boolean state) {
        if(homeProgressBar == null) {
            Log.d("TES-progressBar", "NULL NULL NULL");
            return;
        }
        if (state) {
            homeProgressBar.setVisibility(View.VISIBLE);
            svContainer.setVisibility(View.GONE);
        } else {
            homeProgressBar.setVisibility(View.GONE);
            svContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onUserLoggedOut() {
        Log.d("TES-LOGOUT", "onUserLoggedOut");
        //showLoading(false);
        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //startActivity(loginIntent);
        startActivityForResult(loginIntent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TES-SUKSES", "login sukses");
        if(requestCode == HomeFragment.REQUEST_CODE){
            Log.d("TES-SUKSES", "request code passs");
            if(resultCode == 200){
                Log.d("TES-SUKSES", "");
                String name = data.getStringExtra(LoginActivity.EXTRA_NAME);
                Toast.makeText(getContext(), getResources().getString(R.string.app_name) + name, Toast.LENGTH_SHORT).show();
                this.homeViewModel.loadStatistik(getActivity(),this, ApiTool.getTodayDateString(getActivity()));
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cv_mnu_tak:
                loadIdentityPagingLib(0, "TAK", "T", ApiTool.getTakColor());
                break;
            case R.id.cv_mnu_hadir:
                loadIdentityPagingLib(1, "HADIR", "H", ApiTool.getHadirColor());
                break;
            case R.id.cv_mnu_dinas:
                loadIdentityPagingLib(2, "DINAS", "D", ApiTool.getDinasColor());
                break;
            case R.id.cv_mnu_cuti:
                loadIdentityPagingLib(3, "CUTI", "C", ApiTool.getCutiColor());
                break;
            case R.id.cv_mnu_izin:
                loadIdentityPagingLib(4, "IZIN", "I", ApiTool.getIzinColor());
                break;
            case R.id.cv_mnu_sakit:
                loadIdentityPagingLib(5, "SAKIT", "S", ApiTool.getSakitColor());
                break;
            case R.id.cv_mnu_lain_lain:
                loadIdentityPagingLib(6, "LAIN-LAIN", "L", Color.LTGRAY);
                break;
            case R.id.cv_mnu_checklog:
                loadChecklogActivity();
                break;

        }
    }

    private void loadChecklogActivity(){
        Toast.makeText(getContext(), getResources().getString(R.string.app_name), Toast.LENGTH_SHORT).show();
        Intent checklogIntentPaging = new Intent(getActivity(), ChecklogActivity.class);
        startActivity(checklogIntentPaging);
    }

    private void loadIdentityPagingLib(int stsKehadiran, String info, String identity, int color){
        Log.d("TYPE-GROUP", "STATUS: "+stsKehadiran);
        IdentityGroup identityGroup = new IdentityGroup();
        identityGroup.setGROUP_TYPE(IdentityGroup.TYPE.PRESENCE_IDENTITY);
        identityGroup.setSts_kehadiran(stsKehadiran);
        identityGroup.setInitial(identity);
        identityGroup.setColor(color);
        identityGroup.setInfo(info);
        identityGroup.setIdentity(identity);
        Toast.makeText(getContext(), getResources().getString(R.string.app_name) + info, Toast.LENGTH_SHORT).show();
        Intent identityIntentPagingLib = new Intent(getActivity(), IdentityWithPageActivity.class);
        identityIntentPagingLib.putExtra(IdentityWithPageActivity.EXTRA_IDENTITY, identityGroup);
        startActivity(identityIntentPagingLib);
    }
}