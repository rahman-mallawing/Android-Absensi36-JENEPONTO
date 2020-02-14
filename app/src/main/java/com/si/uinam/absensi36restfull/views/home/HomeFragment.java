package com.si.uinam.absensi36restfull.views.home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
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
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import com.si.uinam.absensi36restfull.LoginActivity;
import com.si.uinam.absensi36restfull.MyMarkerView;
import com.si.uinam.absensi36restfull.R;
import com.si.uinam.absensi36restfull.helpers.ApiTool;
import com.si.uinam.absensi36restfull.models.StaBulananTahunModel;
import com.si.uinam.absensi36restfull.models.StatistikModel;
import com.si.uinam.absensi36restfull.services.AuthenticationListener;
import com.si.uinam.absensi36restfull.viewmodels.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements AuthenticationListener {



    //gerid
    GridView androidGridView;

    String[] gridViewString = {
            "Alram", "Android", "Mobile", "Website", "Profile", "WordPress",
            "Alram", "Android", "Mobile", "Website", "Profile", "WordPress",
            "Alram", "Android", "Mobile", "Website", "Profile", "WordPress",

    } ;
    int[] gridViewImageId = {
            R.drawable.rounded_drawable, R.drawable.user, R.drawable.placeholder, R.drawable.rounded_drawable, R.drawable.user, R.drawable.placeholder,
            R.drawable.user, R.drawable.placeholder, R.drawable.rounded_drawable, R.drawable.user, R.drawable.placeholder, R.drawable.rounded_drawable,
            R.drawable.placeholder, R.drawable.rounded_drawable, R.drawable.user, R.drawable.placeholder, R.drawable.rounded_drawable, R.drawable.user,

    };

    private HomeViewModel homeViewModel;
    private ProgressBar homeProgressBar;
    private ScrollView svContainer;
    private TextView tvPegawai, tvHadir, tvDinas, tvTak;
    private PieChart chartPie;
    private LineChart chartLine;
    private BarChart chart;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(HomeViewModel.class);
        homeViewModel.getStatistikHarian().observe(this, new Observer<StatistikModel>() {
            @Override
            public void onChanged(StatistikModel statistikModel) {
                tvPegawai.setText(String.valueOf(statistikModel.getJumlahPegawai()));
                tvHadir.setText(String.valueOf(statistikModel.getHadir()));
                tvDinas.setText(String.valueOf(statistikModel.getDinasLuar()));
                tvTak.setText(String.valueOf(statistikModel.getAbsen()));
                fillPieChart(statistikModel);
                //fillLineChart();
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
        homeViewModel.loadStatistik(this, ApiTool.getTodayDateString());
        //chartLine = findViewById(R.id.chart);
//


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
        homeProgressBar = root.findViewById(R.id.homeProgressBar);
        chart = root.findViewById(R.id.bar_chart);
        chartLine = root.findViewById(R.id.chart_line);
        chartPie = root.findViewById(R.id.chart_pie);


        chartLine.setTouchEnabled(true);
        chartLine.setPinchZoom(true);
        chart.setScaleEnabled(false);

        MyMarkerView mv = new MyMarkerView(getActivity().getApplicationContext(), R.layout.custom_marker_view);
        mv.setChartView(chartLine);
        chartLine.setMarker(mv);

        renderData();
        return root;
    }

    public void renderData() {
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        XAxis xAxis = chartLine.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setAxisMaximum(10f);
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawLimitLinesBehindData(true);

        LimitLine ll1 = new LimitLine(215f, "Maximum Limit");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);

        LimitLine ll2 = new LimitLine(70f, "Minimum Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);

        YAxis leftAxis = chartLine.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.setAxisMaximum(350f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawLimitLinesBehindData(false);

        chartLine.getAxisRight().setEnabled(false);
        setData();
    }

    private void setData() {

        ArrayList<Entry> values = new ArrayList<>();
        values.add(new Entry(1, 50));
        values.add(new Entry(2, 100));
        values.add(new Entry(3, 80));
        values.add(new Entry(4, 120));
        values.add(new Entry(5, 110));
        values.add(new Entry(7, 150));
        values.add(new Entry(8, 250));
        values.add(new Entry(9, 190));

        LineDataSet set1;
        if (chartLine.getData() != null &&
                chartLine.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chartLine.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chartLine.getData().notifyDataChanged();
            chartLine.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "Sample Data");
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
                //Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.fade_blue);
                //set1.setFillDrawable(drawable);
            } else {
                //set1.setFillColor(Color.DKGRAY);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            chartLine.setData(data);
        }
    }


    private void fillBarChart(ArrayList<StaBulananTahunModel> staBulananTahunModels){

        float barWidth;
        float barSpace;
        float groupSpace;

        barWidth = 0.3f;
        barSpace = 0f;
        groupSpace = 0.4f;
        chart.setDescription(null);
        chart.setPinchZoom(false);
        chart.setScaleEnabled(false);
        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);


        int groupCount = 6;

        ArrayList xVals = new ArrayList();

        xVals.add("Jan");
        xVals.add("Feb");
        xVals.add("Mar");
        xVals.add("Apr");
        xVals.add("May");
        xVals.add("Jun");

        ArrayList yVals1 = new ArrayList();
        ArrayList yVals2 = new ArrayList();

        yVals1.add(new BarEntry(1, (float) 12));
        yVals2.add(new BarEntry(1, (float) 2));
        yVals1.add(new BarEntry(2, (float) 3));
        yVals2.add(new BarEntry(2, (float) 14));
        yVals1.add(new BarEntry(3, (float) 5));
        yVals2.add(new BarEntry(3, (float) 6));
        yVals1.add(new BarEntry(4, (float) 7));
        yVals2.add(new BarEntry(4, (float) 8));
        yVals1.add(new BarEntry(5, (float) 9));
        yVals2.add(new BarEntry(5, (float) 10));
        yVals1.add(new BarEntry(6, (float) 11));
        yVals2.add(new BarEntry(6, (float) 12));


        BarDataSet set1, set2;
        set1 = new BarDataSet(yVals1, "A");
        set1.setColor(Color.BLUE);
        set2 = new BarDataSet(yVals2, "B");
        set2.setColor(Color.rgb(0, 155, 0));
        BarData data = new BarData(set1, set2);
        data.setValueFormatter(new LargeValueFormatter());
        chart.setData(data);
        chart.getBarData().setBarWidth(barWidth);
        chart.getXAxis().setAxisMinimum(0);
        chart.getXAxis().setAxisMaximum(0 + chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
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
        l.setTextSize(8f);


        //X-axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(6);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
//Y-axis
        chart.getAxisRight().setEnabled(false);
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(true);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f);


    }

    private void fillLineChart(){
        List<Entry> valsComp1 = new ArrayList<Entry>();
        Entry c1e1;

        for (int i = 1; i < 31; i++) {
            c1e1 = new Entry(i, (float) (Math.random() * 40) ); // 0 == quarter 1
            valsComp1.add(c1e1);
        }



        LineDataSet setComp1 = new LineDataSet(valsComp1, "Kehadiran Harian");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);

        // use the interface ILineDataSet
        List<ILineDataSet> dataSets2 = new ArrayList<ILineDataSet>();
        dataSets2.add(setComp1);
        LineData dataLine = new LineData(dataSets2);
        chartLine.setData(dataLine);
        chartLine.invalidate(); // refresh
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
        //detailIntent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
        startActivity(loginIntent);
    }
}