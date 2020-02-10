package com.si.uinam.absensi36restfull.views.home;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
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
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.si.uinam.absensi36restfull.MyMarkerView;
import com.si.uinam.absensi36restfull.R;
import com.si.uinam.absensi36restfull.viewmodels.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {



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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        BarChart chart = root.findViewById(R.id.bar_chart);
        LineChart chartLine = root.findViewById(R.id.chart_line);
        PieChart chartPie = root.findViewById(R.id.chart_pie);

        ArrayList NoOfEmp = new ArrayList();

        NoOfEmp.add(new BarEntry(945f, 0));
        NoOfEmp.add(new BarEntry(1040f, 1));
        NoOfEmp.add(new BarEntry(1133f, 2));
        NoOfEmp.add(new BarEntry(1240f, 3));
        NoOfEmp.add(new BarEntry(1369f, 4));
        NoOfEmp.add(new BarEntry(1487f, 5));
        NoOfEmp.add(new BarEntry(1501f, 6));
        NoOfEmp.add(new BarEntry(1645f, 7));
        NoOfEmp.add(new BarEntry(1578f, 8));
        NoOfEmp.add(new BarEntry(1695f, 9));

        ArrayList year = new ArrayList();

        year.add("2008");
        year.add("2009");
        year.add("2010");
        year.add("2011");
        year.add("2012");
        year.add("2013");
        year.add("2014");
        year.add("2015");
        year.add("2016");
        year.add("2017");

        BarDataSet bardataset = new BarDataSet(NoOfEmp, "No Of Employee");

        int startYear = 1980;
        int endYear = startYear + 8;


        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();
        ArrayList<BarEntry> values3 = new ArrayList<>();
        ArrayList<BarEntry> values4 = new ArrayList<>();

        float randomMultiplier = 10 * 100000f;

        for (int i = startYear; i < endYear; i++) {
            values1.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
            values2.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
            values3.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
            values4.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
        }

        chart.animateY(5000);
        BarDataSet set1, set2, set3, set4;
        set1 = new BarDataSet(values1, "Company A");
        set1.setColor(Color.rgb(104, 241, 175));
        set2 = new BarDataSet(values2, "Company B");
        set2.setColor(Color.rgb(164, 228, 251));
        set3 = new BarDataSet(values3, "Company C");
        set3.setColor(Color.rgb(242, 247, 158));
        set4 = new BarDataSet(values4, "Company D");
        set4.setColor(Color.rgb(255, 102, 0));


        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 30f));
        entries.add(new BarEntry(1f, 80f));
        entries.add(new BarEntry(2f, 60f));
        entries.add(new BarEntry(3f, 50f));
        // gap of 2f
        entries.add(new BarEntry(5f, 70f));
        entries.add(new BarEntry(6f, 60f));
        BarDataSet setCol = new BarDataSet(entries, "BarDataSet");
        setCol.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData data = new BarData(setCol);
        data.setBarWidth(0.9f); // set custom bar width
        chart.setData(data);
        chart.setFitBars(true); // make the x-axis fit exactly all bars
        chart.invalidate(); // refresh


        List<Entry> valsComp1 = new ArrayList<Entry>();
        List<Entry> valsComp2 = new ArrayList<Entry>();
        Entry c1e1;
        Entry c1e2 ;

        for (int i = 1; i < 31; i++) {
            c1e1 = new Entry(i, (float) (Math.random() * 40) ); // 0 == quarter 1
            valsComp1.add(c1e1);
            c1e2 = new Entry(i, (float) (Math.random() * 45) ); // 1 == quarter 2 ...
            valsComp2.add(c1e2);
        }



        LineDataSet setComp1 = new LineDataSet(valsComp1, "Company 1");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        LineDataSet setComp2 = new LineDataSet(valsComp2, "Company 2");
        setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);

        // use the interface ILineDataSet
        List<ILineDataSet> dataSets2 = new ArrayList<ILineDataSet>();
        dataSets2.add(setComp1);
        dataSets2.add(setComp2);
        LineData dataLine = new LineData(dataSets2);
        chartLine.setData(dataLine);
        chartLine.invalidate(); // refresh


        List<PieEntry> entriesPie = new ArrayList<>();
        entriesPie.add(new PieEntry(18.5f, "Green"));
        entriesPie.add(new PieEntry(26.7f, "Yellow"));
        entriesPie.add(new PieEntry(24.0f, "Red"));
        entriesPie.add(new PieEntry(30.8f, "Blue"));
        PieDataSet setPie = new PieDataSet(entriesPie, "Election Results");
        setPie.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData dataPie = new PieData(setPie);
        chartPie.setData(dataPie);
        chartPie.invalidate(); // refresh

        //BarData data = new BarData(set1, set2, set3, set4);
        //BarData data = new BarData(year, bardataset);
        //bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        //chart.setData(data);


        CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(getContext(), gridViewString, gridViewImageId);
        androidGridView=(GridView)root.findViewById(R.id.grid_view_image_text);
        androidGridView.setAdapter(adapterViewAndroid);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                Toast.makeText(getContext(), "GridView Item: " + gridViewString[+i], Toast.LENGTH_LONG).show();
            }
        });




        return root;
    }
}