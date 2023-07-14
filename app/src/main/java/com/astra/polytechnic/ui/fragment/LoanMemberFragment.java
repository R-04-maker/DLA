package com.astra.polytechnic.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.astra.polytechnic.R;

public class LoanMemberFragment extends Fragment {
    private LinearLayout unconfirmedLayout;
    private LinearLayout confirmedLayout;
    private LinearLayout finishedLayout;

    private TextView unconfirmedTxt;
    private TextView confirmedTxt;
    private TextView finishedTxt;
    public LoanMemberFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_loan_member, container, false);

        // Inflate the layout for this fragment
        getChildFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainerView, KoleksiMemberFragment.class, null)
                .commit();
        return view;
    }
}
