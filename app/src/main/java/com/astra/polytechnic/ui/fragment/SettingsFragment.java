package com.astra.polytechnic.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.astra.polytechnic.R;
import com.astra.polytechnic.ui.activity.DashboardActivity;
import com.astra.polytechnic.ui.activity.LoginActivity;

public class SettingsFragment extends Fragment {
    private static final String TAG = "Fragment";
    ImageButton mLogOut;
    SharedPreferences pref;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        pref = getActivity().getSharedPreferences("nomor", Context.MODE_PRIVATE);

        mLogOut=view.findViewById(R.id.btnLogout);
        mLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.apply();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}