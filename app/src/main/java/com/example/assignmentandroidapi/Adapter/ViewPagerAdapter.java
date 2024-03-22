package com.example.assignmentandroidapi.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.assignmentandroidapi.Fragment.OnBoarDingFragment1;
import com.example.assignmentandroidapi.Fragment.OnBoarDingFragment2;
import com.example.assignmentandroidapi.Fragment.OnBoardDingFragment;
import com.example.assignmentandroidapi.OnBoarding.OnBoarDingActivity;


public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(OnBoarDingActivity fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new OnBoardDingFragment();
            case 1:
                return new OnBoarDingFragment1();
            case 2:
                return new OnBoarDingFragment2();
            default:
                return new OnBoardDingFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
