package com.example.jamespotatao.controlapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;


import java.util.ArrayList;
import java.util.List;

public class SpectraActivity extends AppCompatActivity{

    private static final String TAG = "TAG";
    private LineChart lineChart; //声明图表控件
    MBroad mB2 = null;
    private boolean isfirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lineChart = findViewById(R.id.linechart);
        mB2 = new MBroad();
        IntentFilter filter = new IntentFilter("com.example.jamespotato.controlapp");
        registerReceiver(mB2,filter);
        //handler.post(myRunnable);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mB2);
        super.onDestroy();
    }

    /** private Handler handler = new Handler();

    private Runnable myRunnable = new Runnable() {
        @Override
        public void run() {

            show();
            handler.postDelayed(this,1000);
        }
    };
    **/

    public class MBroad extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String[] x_value = intent.getStringArrayExtra("x_value");
            String[] y_value = intent.getStringArrayExtra("y_value");
            List<String> xDataList = new ArrayList<>();//x轴数据源
            List<Entry> yDataList = new ArrayList<>();//y轴数据数据源
            for (int i = 0; i<x_value.length; i++){
                xDataList.add(x_value[i]);
                float valueY = Float.parseFloat(y_value[i]);
                yDataList.add(new Entry(valueY,i));
            }

            ChartUtil.showChart(SpectraActivity.this,lineChart,xDataList,
                    yDataList,"光之曲线图","波","nm");

            ;

        }
    }

/**
    private void show() {

        List<String> xDataList = new ArrayList<>();//x轴数据源
        List<Entry> yDataList = new ArrayList<>();//y轴数据数据源

        //给上面的X、Y轴数据源做假数据测试
        int range = 100;
        for (int i = 0; i<=2000; i++){
            //x轴显示的数据
            xDataList.add(String.valueOf(i));
            //y轴生成float类型的随机数
            float value = (float)(Math.random()* range) + 3;
            yDataList.add(new Entry(value,i));
        }

        ChartUtil.showChart(this,lineChart,xDataList,
                yDataList,"番薯趋势图","番薯","个");

    }

**/

}
