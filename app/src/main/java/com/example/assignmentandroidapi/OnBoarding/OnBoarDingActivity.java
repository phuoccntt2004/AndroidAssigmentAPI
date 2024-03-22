package com.example.assignmentandroidapi.OnBoarding;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.assignmentandroidapi.Adapter.ViewPagerAdapter;
import com.example.assignmentandroidapi.R;

import me.relex.circleindicator.CircleIndicator3;

public class OnBoarDingActivity extends AppCompatActivity {
    private TextView txtSkip;
    private ViewPager2 viewPager;
    private RelativeLayout layout_bottom;
    private CircleIndicator3 circleIndicator;
    private LinearLayout layout_Next;

    private ViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        Mapping();

        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        // gắn viewPager vào CircleIndicator
        circleIndicator.setViewPager(viewPager);
        viewPagerAdapter.registerAdapterDataObserver(circleIndicator.getAdapterDataObserver());

        txtSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });

        layout_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() < 2) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 2) {
                    txtSkip.setVisibility(View.GONE);
                    layout_bottom.setVisibility(View.GONE);
                } else {
                    txtSkip.setVisibility(View.VISIBLE);
                    layout_bottom.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void Mapping() {
        txtSkip = findViewById(R.id.txtSkip);
        viewPager = findViewById(R.id.view_pager);
        layout_bottom = findViewById(R.id.layout_bottom);
        circleIndicator = findViewById(R.id.circle_Indicator);
        layout_Next = findViewById(R.id.layout_Next);
    }
}