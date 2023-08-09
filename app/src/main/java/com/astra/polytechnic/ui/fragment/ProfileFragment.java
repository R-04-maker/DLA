package com.astra.polytechnic.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astra.polytechnic.R;
import com.astra.polytechnic.ViewModel.UserViewModel;
import com.astra.polytechnic.ui.activity.HistoryActivity;
import com.astra.polytechnic.ui.activity.HistoryMemberActivity;
import com.astra.polytechnic.ui.activity.LoginActivity;

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";
    private ConstraintLayout textContact, textCondition, textPrivacy, mShowHistory;
    private TextView mLoginName, mRoleName, mEmail, mNIMProfile, mNotelProfile, mProdi;
    private Button mLogout;
    private CardView mCardView1, mCardViewHistory;
    SharedPreferences pref;
    private String email, role;
    private UserViewModel mUserViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        textContact = view.findViewById(R.id.library);
        textCondition = view.findViewById(R.id.condi);
        textPrivacy = view.findViewById(R.id.privacy);
        mLogout = view.findViewById(R.id.btn_logout);
        mRoleName = view.findViewById(R.id.role_name);
        mLoginName = view.findViewById(R.id.login_layout);
        mEmail = view.findViewById(R.id.profile_frag_name);
        mNIMProfile = view.findViewById(R.id.profile_frag_nomor);
        mNotelProfile = view.findViewById(R.id.profile_frag_noHp);
        mProdi = view.findViewById(R.id.profile_frag_prodi);
        mCardView1 = view.findViewById(R.id.card_1);
        mCardViewHistory = view.findViewById(R.id.card_hist);
        mShowHistory = view.findViewById(R.id.show_history);

        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        pref= getActivity().getSharedPreferences("nomor", getActivity().MODE_PRIVATE);
        mLoginName.setText(pref.getString("nama", null));
        role = pref.getString("id_role",null);
        email = pref.getString("email",null);
        if(role!=null){
            if(!role.equals("ROL01")){
                // kalo misal login sebagai member, defaultnya Librarian
                mRoleName.setText("Member");
            }
            mEmail.setText(pref.getString("email",null));
            mNIMProfile.setText(pref.getString("nomor",null));
            mNotelProfile.setText(pref.getString("no_hp",null));
            mProdi.setText(pref.getString("deskripsi",null));
        }else {
            mRoleName.setVisibility(View.GONE);
            mLogout.setText("LOGIN");
            mCardView1.setVisibility(View.GONE);
            mCardViewHistory.setVisibility(View.GONE);
        }
        mShowHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!role.equals("ROL01")){
                    Intent intent  = new Intent(getActivity(), HistoryMemberActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent  = new Intent(getActivity(), HistoryActivity.class);
                    startActivity(intent);
                }
            }
        });
        textContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog1();
            }
        });
        textCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog2();
            }
        });
        textPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog3();
            }
        });
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.apply();


                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();

                deleteToken();
            }
        });
        return view;
    }

    private void showDialog1() {
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_layout);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
    private void showDialog2(){
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_condition);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
    private void showDialog3(){
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_privacy);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
    private void deleteToken(){
        mUserViewModel.deleteToken(email);
    }
}