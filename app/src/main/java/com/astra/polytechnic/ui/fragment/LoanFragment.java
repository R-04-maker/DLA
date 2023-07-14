package com.astra.polytechnic.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astra.polytechnic.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoanFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int selectedTab = 1;
    private LinearLayout unconfirmedLayout;
    private LinearLayout confirmedLayout;
    private LinearLayout finishedLayout;

    private TextView unconfirmedTxt;
    private TextView confirmedTxt;
    private TextView finishedTxt;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoanFragment() {
        // Required empty public constructor
    }

    public static LoanFragment newInstance(String param1, String param2) {
        LoanFragment fragment = new LoanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_loan, container, false);
        unconfirmedLayout = view.findViewById(R.id.unconfirmedLayout);
        confirmedLayout = view.findViewById(R.id.confirmedLayout);
        finishedLayout = view.findViewById(R.id.finishedLayout);

        unconfirmedTxt = view.findViewById(R.id.unconfirmedTxt);
        confirmedTxt = view.findViewById(R.id.confirmedTxt);
        finishedTxt = view.findViewById(R.id.finishedTxt);

        getChildFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_loan_tab, UnconfirmedFragment.class, null)
                .commit();

        unconfirmedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedTab != 1){
                    getChildFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_loan_tab, UnconfirmedFragment.class, null)
                            .commit();

                    confirmedLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    finishedLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    unconfirmedLayout.setBackgroundResource(R.drawable.round_back_home);
                    unconfirmedTxt.setVisibility(View.VISIBLE);

                    unconfirmedTxt.setTextColor(getResources().getColor(R.color.tab_layout_on));
                    confirmedTxt.setTextColor(getResources().getColor(R.color.white));
                    finishedTxt.setTextColor(getResources().getColor(R.color.white));

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f,  Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    unconfirmedLayout.startAnimation(scaleAnimation);

                    selectedTab = 1;
                }
            }
        });

        confirmedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedTab != 2){
                    getChildFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_loan_tab, ConfirmedFragment.class, null)
                            .commit();

                    unconfirmedLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    finishedLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    confirmedLayout.setBackgroundResource(R.drawable.round_back_home);
                    confirmedTxt.setVisibility(View.VISIBLE);

                    unconfirmedTxt.setTextColor(getResources().getColor(R.color.white));
                    confirmedTxt.setTextColor(getResources().getColor(R.color.tab_layout_on));
                    finishedTxt.setTextColor(getResources().getColor(R.color.white));

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f,  Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    confirmedLayout.startAnimation(scaleAnimation);

                    selectedTab = 2;
                }
            }
        });

        finishedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedTab != 3){
                    getChildFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_loan_tab, FinishedFragment.class, null)
                            .commit();

                    unconfirmedLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    confirmedLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    finishedLayout.setBackgroundResource(R.drawable.round_back_home);
                    finishedTxt.setVisibility(View.VISIBLE);

                    unconfirmedTxt.setTextColor(getResources().getColor(R.color.white));
                    finishedTxt.setTextColor(getResources().getColor(R.color.tab_layout_on));
                    confirmedTxt.setTextColor(getResources().getColor(R.color.white));

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f,  Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    finishedLayout.startAnimation(scaleAnimation);

                    selectedTab = 3;
                }
            }
        });

        return view;
    }
}