package com.example.nahimana.imanage;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ramijemli.percentagechartview.PercentageChartView;
import com.ramijemli.percentagechartview.callback.AdaptiveColorProvider;

public class DebitList extends AppCompatActivity {
    PercentageChartView mChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debit_list);

        mChart = findViewById(R.id.percentageId);
        openChart();

        AdaptiveColorProvider colorProvider = new AdaptiveColorProvider() {
            @Override
            public int provideProgressColor(float progress) {
                if (progress <= 25)
                    return Color.rgb(23,3,200);
                else if (progress <= 50)
                    return Color.blue(200);
                else if (progress <= 75)
                    return Color.red(900);
                else return Color.WHITE;
            }
            @Override
            public int provideBackgroundColor(float progress) {
                //This will provide a bg color that is 80% darker than progress color.
                return ColorUtils.blendARGB(provideProgressColor(progress), Color.BLACK, .8f);
            }
            @Override
            public int provideTextColor(float progress) {
                return provideProgressColor(progress);
            }
            @Override
            public int provideBackgroundBarColor(float progress) {
                return ColorUtils.blendARGB(provideProgressColor(progress), Color.BLACK, .5f);
            }
        };
        mChart.setAdaptiveColorProvider(colorProvider);
    }
   private void openChart(){
            mChart.textColor(Color.BLACK)
                    .textSize(24)
                    .typeface(Typeface.DEFAULT)
                    .textShadow(Color.WHITE, 2f, 2f, 2f)
                    .progressColor(Color.RED)
                    .backgroundColor(Color.BLACK)
                    .apply();
    }
}