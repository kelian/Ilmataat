package proovitoo.ilmarakendus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import proovitoo.ilmarakendus.R;
import proovitoo.ilmarakendus.ViewPagerAdapter;
import proovitoo.ilmarakendus.fragments.TabFourthFragment;
import proovitoo.ilmarakendus.fragments.TabThirdFragment;
import proovitoo.ilmarakendus.fragments.TabFirstFragment;
import proovitoo.ilmarakendus.fragments.TabSecondFragment;
import proovitoo.ilmarakendus.weather.Forecast;

/**
 * Created by Kelian on 11/04/2016.
 */
public class FourDayForecastActivity extends AppCompatActivity {
    public static ArrayList<Forecast> forecasts;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        // gets forecast ArrayList from MainActivity
        Intent intent = getIntent();
        forecasts = (ArrayList<Forecast>) intent.getSerializableExtra(MainActivity.EXTRA_MESSAGE);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(final ViewPager viewPager) {

        TabFirstFragment firstFragment = TabFirstFragment.newInstance(forecasts.get(0));
        TabSecondFragment secondFragment = TabSecondFragment.newInstance(forecasts.get(1));
        TabThirdFragment thirdFragment = TabThirdFragment.newInstance(forecasts.get(2));
        TabFourthFragment fourthFragment = TabFourthFragment.newInstance(forecasts.get(3));

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(firstFragment, forecasts.get(0).date);
        adapter.addFragment(secondFragment, forecasts.get(1).date);
        adapter.addFragment(thirdFragment, forecasts.get(2).date);
        adapter.addFragment(fourthFragment, forecasts.get(3).date);
        viewPager.setAdapter(adapter);

        viewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                viewPager.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
