package com.example.astrocalculator;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SunFragment extends Fragment {

    TextView latitudeSun;
    TextView longitudeSun;
    TextView sunriseTime;
    TextView sunriseAzimuth;
    TextView sunsetTime;
    TextView sunsetAzimuth;
    TextView civilSunrise;
    TextView civilSunset;

    Handler handler = new Handler();
    Runnable runnable = null;

    public SunFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity)getActivity()).updateAstroData();
        View view = inflater.inflate(R.layout.fragment_sun, container, false);
        this.latitudeSun = view.findViewById(R.id.latitudeSun);
        this.latitudeSun.setText("Latitude: " + String.valueOf(((MainActivity)getActivity()).latitude));
        this.longitudeSun = view.findViewById(R.id.longitudeSun);
        this.longitudeSun.setText("Longitude: " + String.valueOf(((MainActivity)getActivity()).longitude));
        this.sunriseTime = view.findViewById(R.id.sunriseTime);
        this.sunriseTime.setText("Sunrise time: " + ((MainActivity)getActivity()).astroCalculator.getSunInfo().getSunrise().toString());
        this.sunriseAzimuth = view.findViewById(R.id.sunriseAzimuth);
        this.sunriseAzimuth.setText("Sunrise azimuth: " + String.valueOf(((MainActivity)getActivity()).astroCalculator.getSunInfo().getAzimuthRise()));
        this.sunsetTime = view.findViewById(R.id.sunsetTime);
        this.sunsetTime.setText("Sunset time: " + ((MainActivity)getActivity()).astroCalculator.getSunInfo().getSunset().toString());
        this.sunsetAzimuth = view.findViewById(R.id.sunsetAzimuth);
        this.sunsetAzimuth.setText("Sunset azimuth: " + String.valueOf(((MainActivity)getActivity()).astroCalculator.getSunInfo().getAzimuthSet()));
        this.civilSunrise = view.findViewById(R.id.civilSunrise);
        this.civilSunrise.setText("Civil sunrise: " + ((MainActivity)getActivity()).astroCalculator.getSunInfo().getTwilightMorning().toString());
        this.civilSunset = view.findViewById(R.id.civilSunset);
        this.civilSunset.setText("Civil sunset: " + ((MainActivity)getActivity()).astroCalculator.getSunInfo().getTwilightEvening().toString());

        runnable = new Runnable() {
            public void run() {
                ((MainActivity)getActivity()).updateAstroData();
                latitudeSun.setText("Latitude: " + String.valueOf(((MainActivity)getActivity()).latitude));
                longitudeSun.setText("Longitude: " + String.valueOf(((MainActivity)getActivity()).longitude));
                sunriseTime.setText("Sunrise time: " + ((MainActivity)getActivity()).astroCalculator.getSunInfo().getSunrise().toString());
                sunriseAzimuth.setText("Sunrise azimuth: " + String.valueOf(((MainActivity)getActivity()).astroCalculator.getSunInfo().getAzimuthRise()));
                sunsetTime.setText("Sunset time: " + ((MainActivity)getActivity()).astroCalculator.getSunInfo().getSunset().toString());
                sunsetAzimuth.setText("Sunset azimuth: " + String.valueOf(((MainActivity)getActivity()).astroCalculator.getSunInfo().getAzimuthSet()));
                civilSunrise.setText("Civil sunrise: " + ((MainActivity)getActivity()).astroCalculator.getSunInfo().getTwilightMorning().toString());
                civilSunset.setText("Civil sunset: " + ((MainActivity)getActivity()).astroCalculator.getSunInfo().getTwilightEvening().toString());

                handler.postDelayed(runnable, Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("refresh", "15")) * 60 * 1000);

            }
        };
        runnable.run();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).updateAstroData();
        this.latitudeSun.setText("Latitude: " + String.valueOf(((MainActivity)getActivity()).latitude));
        this.longitudeSun.setText("Longitude: " + String.valueOf(((MainActivity)getActivity()).longitude));
        this.sunriseTime.setText("Sunrise time: " + ((MainActivity)getActivity()).astroCalculator.getSunInfo().getSunrise().toString());
        this.sunriseAzimuth.setText("Sunrise azimuth: " + String.valueOf(((MainActivity)getActivity()).astroCalculator.getSunInfo().getAzimuthRise()));
        this.sunsetTime.setText("Sunset time: " + ((MainActivity)getActivity()).astroCalculator.getSunInfo().getSunset().toString());
        this.sunsetAzimuth.setText("Sunset azimuth: " + String.valueOf(((MainActivity)getActivity()).astroCalculator.getSunInfo().getAzimuthSet()));
        this.civilSunrise.setText("Civil sunrise: " + ((MainActivity)getActivity()).astroCalculator.getSunInfo().getTwilightMorning().toString());
        this.civilSunset.setText("Civil sunset: " + ((MainActivity)getActivity()).astroCalculator.getSunInfo().getTwilightEvening().toString());
        handler.post(runnable);
    }

    @Override
    public void onPause(){
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


}