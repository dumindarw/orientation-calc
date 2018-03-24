package com.rajitha.andro.orientationcalc;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rajitha.andro.orientationcalc.helpers.StatisticsViewModel;
import com.rajitha.andro.orientationcalc.model.Statistics;

import java.util.concurrent.TimeUnit;


public class StatsFragment extends Fragment {

    private TextView azimuthTxt;
    private TextView rollTxt;
    private TextView pitchTxt;
    private TextView timeElpsTxt;
    private TextView pointsCountTxt;

    private OnFragmentInteractionListener mListener;

    public StatsFragment() {
        // Required empty public constructor
    }


    public static StatsFragment newInstance(String param1, String param2) {
        StatsFragment fragment = new StatsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stats, container, false);


        azimuthTxt = (TextView) view.findViewById(R.id.azimuthTxt);
        rollTxt = (TextView) view.findViewById(R.id.rollTxt);
        pitchTxt = (TextView) view.findViewById(R.id.pitchTxt);
        timeElpsTxt = (TextView) view.findViewById(R.id.timeElpsTxt);
        pointsCountTxt = (TextView) view.findViewById(R.id.pointsCountTxt);

        StatisticsViewModel statisticsViewModel = ViewModelProviders.
                of(getActivity()).
                get(StatisticsViewModel.class);

        statisticsViewModel.getStatistics().observe(this, new Observer<Statistics>() {
            @Override
            public void onChanged(@Nullable Statistics statistics) {
                azimuthTxt.setText(String.valueOf(statistics.getAzimuthAngle()));
                rollTxt.setText(String.valueOf(statistics.getRollAngle()));
                pitchTxt.setText(String.valueOf(statistics.getPitchAngle()));

                timeElpsTxt.setText(String.format("%d Min, %d Sec",
                        TimeUnit.MILLISECONDS.toMinutes(statistics.getTimeElapsed()),
                        TimeUnit.MILLISECONDS.toSeconds(statistics.getTimeElapsed()) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(statistics.getTimeElapsed()))
                ));
                pointsCountTxt.setText(String.valueOf(statistics.getPointsCount()));

            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            //mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
