package com.adam.pom;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.adam.pom.Fragments.FragmentMain;
import com.adam.pom.Helper.FixedTabsPagerAdapter;
import com.adam.pom.Objects.Cards;
import com.google.android.material.tabs.TabLayout;
import java.util.List;


public class LandingPage extends AppCompatActivity {
    private static final String TAG = LandingPage.class.getSimpleName();

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private TabLayout tabLayout;

    private List<Cards> cards;
    private String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        //bundle cards name and send to FragmentMain.
        FragmentMain fragMain = new FragmentMain();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        fragMain.setArguments(bundle);

        //Sync viewpager with tablayout
        viewPager = findViewById(R.id.view_pager);
        pagerAdapter = new FixedTabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        //Sync tablayout with viewpager.
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }
}