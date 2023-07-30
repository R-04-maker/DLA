package com.astra.polytechnic.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.astra.polytechnic.R;
import com.astra.polytechnic.ui.fragment.HomeMemberFragment;
import com.astra.polytechnic.ui.fragment.BooksCollectionFragment;
import com.astra.polytechnic.ui.fragment.ProfileFragment;

public class DashboardMemberActivity extends AppCompatActivity {

    // number of selected tab, in this project have 3 tabs so value must lie between 1-3.
    // Default value is 1 because first tab is selected by default.
    private int selectedTab = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        final LinearLayout homeLayout = findViewById(R.id.homeLayout);
        final LinearLayout loanLayout = findViewById(R.id.loanlayout);
        final LinearLayout profileLayout = findViewById(R.id.profile_layout);

        final ImageView homeImage = findViewById(R.id.homeImage);
        final ImageView loanImage = findViewById(R.id.loanimage);
        final ImageView profileImage = findViewById(R.id.profile_image);

        final TextView homeTxt = findViewById(R.id.hometxt);
        final TextView loanTxt = findViewById(R.id.txtloan);
        final TextView profileTxt = findViewById(R.id.txtprofile);

        // Set Home Fragment by default
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainter, HomeMemberFragment.class, null)
                .commit();

        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // check if home is already selected or not.
                if(selectedTab != 1){

                    // set home Fragment
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainter, HomeMemberFragment.class, null)
                            .commit();

                    // unselect other tabs expect home tab
                    loanTxt.setVisibility(View.GONE);
                    profileTxt.setVisibility(View.GONE);

                    loanImage.setImageResource(R.drawable.baseline_library_books_24);
                    profileImage.setImageResource(R.drawable.baseline_person_24);

                    loanLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    // Select home tab
                    homeTxt.setVisibility(View.VISIBLE);
                    homeImage.setImageResource(R.drawable.baseline_home_24_active);
                    homeLayout.setBackgroundResource(R.drawable.round_back_home);

                    // Create Animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f,  Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    homeLayout.startAnimation(scaleAnimation);

                    // set 1st tab as selected tab
                    selectedTab = 1;
                }
            }
        });

        loanLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if loan is already selected or not.
                if(selectedTab != 2){

                    // set home Fragment
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainter, BooksCollectionFragment.class, null)
                            .commit();

                    // unselect other tabs expect loan tab
                    homeTxt.setVisibility(View.GONE);
                    profileTxt.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.baseline_home_24);
                    profileImage.setImageResource(R.drawable.baseline_person_24);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    // Select loan tab
                    loanTxt.setVisibility(View.VISIBLE);
                    loanImage.setImageResource(R.drawable.baseline_library_books_24_active);
                    loanLayout.setBackgroundResource(R.drawable.round_back_loan);

                    // Create Animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f,  Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    loanLayout.startAnimation(scaleAnimation);

                    // set 2nd tab as selected tab
                    selectedTab = 2;
                }
            }
        });

        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if Settings is already selected or not.
                if(selectedTab != 3){

                    // set settings Fragment
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainter, ProfileFragment.class, null)
                            .commit();

                    // unselect other tabs expect settings tab
                    loanTxt.setVisibility(View.GONE);
                    homeTxt.setVisibility(View.GONE);

                    loanImage.setImageResource(R.drawable.baseline_library_books_24);
                    homeImage.setImageResource(R.drawable.baseline_home_24);

                    loanLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    // Select settings tab
                    profileTxt.setVisibility(View.VISIBLE);
                    profileImage.setImageResource(R.drawable.baseline_person_24_active);
                    profileLayout.setBackgroundResource(R.drawable.round_back_settings);

                    // Create Animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f,  Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    profileLayout.startAnimation(scaleAnimation);

                    // set 3rd tab as selected tab
                    selectedTab = 3;
                }
            }
        });
    }
}