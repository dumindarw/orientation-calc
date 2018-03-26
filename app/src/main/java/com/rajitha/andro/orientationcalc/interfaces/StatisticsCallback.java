package com.rajitha.andro.orientationcalc.interfaces;

import com.rajitha.andro.orientationcalc.model.Statistics;

/**
 * Created by duminda on 3/23/18.
 */

public interface StatisticsCallback{

    void onStatisticsReceive(int fileNumber, Statistics statistics);
}
