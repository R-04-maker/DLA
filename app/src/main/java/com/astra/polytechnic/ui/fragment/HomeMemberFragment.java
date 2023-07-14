package com.astra.polytechnic.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astra.polytechnic.R;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class HomeMemberFragment extends Fragment {

    private ImageSlider mImageSlider;
    private List<SlideModel> mSlideModelList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_member, container, false);

        mImageSlider = view.findViewById(R.id.slider);

        mSlideModelList.add(new SlideModel(R.drawable.banner, ScaleTypes.FIT));
        mSlideModelList.add(new SlideModel("https://bit.ly/2YoJ77H", ScaleTypes.FIT));

        return view;
    }
}