package com.example.astrocalculator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextClock;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.viewpager2.widget.ViewPager2;
import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    ViewPager2 viewPager;
    ViewPagerAdapter viewPagerAdapter;
    Fragment sunFragment;
    Fragment moonFragment;

    AstroCalculator.Location location;
    AstroCalculator astroCalculator;
    AstroDateTime astroDateTime;
    long deviceTime = System.currentTimeMillis();
    Date currentDate = Calendar.getInstance().getTime();
    float longitude;
    float latitude;
    float refreshTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        try {
            latitude = Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(this).getString("latitude", "0"));
            longitude = Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(this).getString("longitude", "0"));
            refreshTime = Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(this).getString("refresh", "15"));
        } catch (Exception e) {
            latitude = 0;
            longitude = 0;
            refreshTime = 15;
        }

        updateAstroData();

        viewPager = findViewById(R.id.viewPager);
        if(viewPager != null) {
            viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
            viewPagerAdapter.addFragment(moonFragment = new MoonFragment());
            viewPagerAdapter.addFragment(sunFragment = new SunFragment());
            viewPager.setAdapter(viewPagerAdapter);
        } else {

            sunFragment = new SunFragment();
            moonFragment = new MoonFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, sunFragment)
                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentViews, moonFragment)
                    .commit();
        }

        TextClock clock = findViewById(R.id.textClock);
        clock.setFormat24Hour("MMM dd, yyyy kk:mm:ss");
        clock.setFormat12Hour("MMM dd, yyyy kk:mm:ss");
        clock.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.astroSettings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        viewPager = findViewById(R.id.viewPager);
        if(viewPager != null) {
            viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
            viewPagerAdapter.addFragment(moonFragment = new MoonFragment());
            viewPagerAdapter.addFragment(sunFragment = new SunFragment());
            viewPager.setAdapter(viewPagerAdapter);
        } else {

            sunFragment = new SunFragment();
            moonFragment = new MoonFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, sunFragment)
                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentViews, moonFragment)
                    .commit();
        }
        updateAstroData();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("latitude")) {
            latitude = Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(this).getString("latitude", "0"));
        }

        if (key.equals("longitude")) {
            longitude = Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(this).getString("longitude", "0"));
        }

        if (key.equals("refresh")) {
            refreshTime = Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(this).getString("refresh", "15"));
        }

        updateAstroData();
    }

    public void updateAstroData() {
        this.astroDateTime = new AstroDateTime(Integer.parseInt(new SimpleDateFormat("yyyy", Locale.getDefault()).format(deviceTime)),
                Integer.parseInt(new SimpleDateFormat("MM", Locale.getDefault()).format(deviceTime)),
                Integer.parseInt(new SimpleDateFormat("dd", Locale.getDefault()).format(deviceTime)),
                Integer.parseInt(new SimpleDateFormat("hh", Locale.getDefault()).format(deviceTime)),
                Integer.parseInt(new SimpleDateFormat("mm", Locale.getDefault()).format(deviceTime)),
                Integer.parseInt(new SimpleDateFormat("ss", Locale.getDefault()).format(deviceTime)),
                this.currentDate.getTimezoneOffset(), true);

        this.location = new AstroCalculator.Location(this.latitude, this.longitude);

        this.astroCalculator = new AstroCalculator(this.astroDateTime, this.location);
    }

}