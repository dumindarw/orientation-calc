package com.rajitha.andro.orientationcalc;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.rajitha.andro.orientationcalc.helpers.StatisticsViewModel;
import com.rajitha.andro.orientationcalc.interfaces.StatisticsCallback;
import com.rajitha.andro.orientationcalc.model.Statistics;


public class RecordFragment extends Fragment implements View.OnClickListener{

    private OnFragmentInteractionListener mListener;
    private StatisticsCallback mStatCallbacks;

    private Button startBtn;
    private Button stopBtn;

    private int fileNo;

    private AsyncTask jobTask;

    public RecordFragment() {
        // Required empty public constructor
    }

    public static RecordFragment newInstance() {
        RecordFragment fragment = new RecordFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_record, container, false);
        startBtn = v.findViewById(R.id.startBtn);
        stopBtn = v.findViewById(R.id.stopBtn);


        fileNo = 0;
        startBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.startBtn:{
                startSaveJob();
            }
            break;
            case R.id.stopBtn:{
                stopSaveJob();
            }
            break;
        }
    }

    private void startSaveJob(){

        jobTask = new CSVJobExecutor(getActivity(), fileNo).execute();
        fileNo++;
        Toast.makeText(getActivity(),"Started...", Toast.LENGTH_SHORT).show();
    }

    private void stopSaveJob(){
        jobTask.cancel(true);

       /* File dir = getActivity().getExternalFilesDir("");

        String csvFile = dir.getAbsolutePath() + fileNo + "abc.csv";

        File file = new File(csvFile);*/

        Toast.makeText(getActivity(),"Stopped...", Toast.LENGTH_SHORT).show();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mStatCallbacks = (StatisticsCallback) context;
        } catch (ClassCastException ex) {

            mStatCallbacks = null;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class CSVJobExecutor extends AsyncTask<Void,Void,Void>  {

        Context mContext;
        int mFileNo;

        CSVJobExecutor(Context ctx, int fileNo){
            mContext = ctx;
            mFileNo = fileNo;
        }
        @Override
        protected Void doInBackground(Void... params) {



            StatisticsViewModel statisticsViewModel = ViewModelProviders.
                    of(getActivity()).
                    get(StatisticsViewModel.class);

            statisticsViewModel.getStatistics().observe( getActivity(), new Observer<Statistics>() {
                @Override
                public void onChanged(@Nullable Statistics statistics) {

                    if (mStatCallbacks != null) {
                        mStatCallbacks.onStatisticsReceive(mFileNo, statistics);
                    }
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void params) {
            super.onPostExecute(params);
        }
    }
}
