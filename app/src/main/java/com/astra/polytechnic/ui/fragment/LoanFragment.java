package com.astra.polytechnic.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astra.polytechnic.R;
import com.astra.polytechnic.ui.activity.SearchActivity;

public class LoanFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int selectedTab = 1;
    private LinearLayout unconfirmedLayout;
    private LinearLayout confirmedLayout;
    private LinearLayout borrowedLayout;

    private TextView unconfirmedTxt, confirmedTxt, finishedTxt;
    private Button mBtnAllTransc;

    public LoanFragment() {
        // Required empty public constructor
    }

    public static LoanFragment newInstance() {
        LoanFragment fragment = new LoanFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_loan, container, false);
        unconfirmedLayout = view.findViewById(R.id.unconfirmedLayout);
        confirmedLayout = view.findViewById(R.id.confirmedLayout);
        borrowedLayout = view.findViewById(R.id.borrowedLayout);

        unconfirmedTxt = view.findViewById(R.id.unconfirmedTxt);
        confirmedTxt = view.findViewById(R.id.confirmedTxt);
        finishedTxt = view.findViewById(R.id.borrowedTxt);

        mBtnAllTransc = view.findViewById(R.id.btn_all_transaction);

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
                    borrowedLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

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
                    borrowedLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

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

        borrowedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedTab != 3){
                    getChildFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_loan_tab, BorrowedFragment.class, null)
                            .commit();

                    unconfirmedLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    confirmedLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    borrowedLayout.setBackgroundResource(R.drawable.round_back_home);
                    finishedTxt.setVisibility(View.VISIBLE);

                    unconfirmedTxt.setTextColor(getResources().getColor(R.color.white));
                    finishedTxt.setTextColor(getResources().getColor(R.color.tab_layout_on));
                    confirmedTxt.setTextColor(getResources().getColor(R.color.white));

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f,  Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    borrowedLayout.startAnimation(scaleAnimation);

                    selectedTab = 3;
                }
            }
        });

        mBtnAllTransc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}