package com.rajitha.andro.orientationcalc;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.rajitha.andro.orientationcalc.helpers.StatisticsViewModel;
import com.rajitha.andro.orientationcalc.interfaces.StatisticsCallback;
import com.rajitha.andro.orientationcalc.model.Statistics;
import com.rajitha.andro.orientationcalc.utils.CSVUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements StatisticsCallback {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (findViewById(R.id.statscontainer) != null && findViewById(R.id.recordcontainer) != null) {

            if (savedInstanceState != null) {
                return;
            }

            StatsFragment statsFragment = new StatsFragment();

            RecordFragment recordFragment = RecordFragment.newInstance();

            statsFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.statscontainer, statsFragment)
                    .add(R.id.recordcontainer, recordFragment).commit();
        }

    }

    @Override
    public void onStatisticsReceive(int fileNo, Statistics statistics) {
        File dir = getExternalFilesDir("");

        String csvFile = dir.getAbsolutePath() + fileNo + "abc.csv";
        FileWriter writer = null;
        try {
            writer = new FileWriter(csvFile, true);
            CSVUtils.writeLine(writer, Arrays.asList(String.valueOf(statistics.getAzimuthAngle()),
                    String.valueOf(statistics.getPitchAngle()), String.valueOf(statistics.getRollAngle()),
                    String.valueOf(statistics.getPointsCount()),
                    String.valueOf(statistics.getTimeElapsed())));

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
