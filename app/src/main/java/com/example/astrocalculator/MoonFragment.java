package com.example.astrocalculator;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
public class MoonFragment extends Fragment {

    TextView latitudeMoon;
    TextView longitudeMoon;
    TextView moonrise;
    TextView moonset;
    TextView newmoon;
    TextView fullmoon;
    TextView moonphase;
    TextView synodicDay;
    Handler handler = new Handler();
    Runnable runnable= null;

    public MoonFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity)getActivity()).updateAstroData();
        View view = inflater.inflate(R.layout.fragment_moon, container, false);

        this.latitudeMoon = view.findViewById(R.id.latitudeMoon);
        this.latitudeMoon.setText("Latitude: " + String.valueOf(((MainActivity)getActivity()).latitude));
        this.longitudeMoon = view.findViewById(R.id.longitudeMoon);
        this.longitudeMoon.setText("Longitude: " + String.valueOf(((MainActivity)getActivity()).longitude));
        this.moonrise = view.findViewById(R.id.moonrise);
        this.moonrise.setText("Moonrise time: " + String.valueOf(((MainActivity)getActivity()).astroCalculator.getMoonInfo().getMoonrise().toString()));
        this.moonset = view.findViewById(R.id.moonset);
        this.moonset.setText("Moonset time: " + ((MainActivity)getActivity()).astroCalculator.getMoonInfo().getMoonset().toString());
        this.newmoon = view.findViewById(R.id.newmoon);
        this.newmoon.setText("Next new moon: " + ((MainActivity)getActivity()).astroCalculator.getMoonInfo().getNextNewMoon().toString());
        this.fullmoon = view.findViewById(R.id.fullmoon);
        this.fullmoon.setText("Next full moon: " + String.valueOf(((MainActivity)getActivity()).astroCalculator.getMoonInfo().getNextFullMoon()));
        this.moonphase = view.findViewById(R.id.moonphase);
        this.moonphase.setText("Moon phase: " + String.valueOf(((MainActivity)getActivity()).astroCalculator.getMoonInfo().getIllumination()) + "%");
        this.synodicDay = view.findViewById(R.id.synodicDay);
        this.synodicDay.setText("Synodic day: " + ((MainActivity)getActivity()).astroCalculator.getMoonInfo().getAge());

        runnable = new Runnable() {
            public void run() {
                ((MainActivity)getActivity()).updateAstroData();
               latitudeMoon.setText("Latitude: " + String.valueOf(((MainActivity)getActivity()).latitude));
               longitudeMoon.setText("Longitude: " + String.valueOf(((MainActivity)getActivity()).longitude));
               moonrise.setText("Moonrise time: " + String.valueOf(((MainActivity)getActivity()).astroCalculator.getMoonInfo().getMoonrise().toString()));
               moonset.setText("Moonset time: " + ((MainActivity)getActivity()).astroCalculator.getMoonInfo().getMoonset().toString());
               newmoon.setText("Next new moon: " + ((MainActivity)getActivity()).astroCalculator.getMoonInfo().getNextNewMoon().toString());
               fullmoon.setText("Next full moon: " + String.valueOf(((MainActivity)getActivity()).astroCalculator.getMoonInfo().getNextFullMoon()));
               moonphase.setText("Moon phase: " + Math.floor(((MainActivity)getActivity()).astroCalculator.getMoonInfo().getIllumination() * 100) + "%");
               synodicDay.setText("Synodic day: " + ((MainActivity)getActivity()).astroCalculator.getMoonInfo().getAge());

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
        this.latitudeMoon.setText("Latitude: " + String.valueOf(((MainActivity)getActivity()).latitude));
        this.longitudeMoon.setText("Longitude: " + String.valueOf(((MainActivity)getActivity()).longitude));
        this.moonrise.setText("Moonrise time: " + String.valueOf(((MainActivity)getActivity()).astroCalculator.getMoonInfo().getMoonrise().toString()));
        this.moonset.setText("Moonset time: " + ((MainActivity)getActivity()).astroCalculator.getMoonInfo().getMoonset().toString());
        this.newmoon.setText("Next new moon: " + ((MainActivity)getActivity()).astroCalculator.getMoonInfo().getNextNewMoon().toString());
        this.fullmoon.setText("Next full moon: " + String.valueOf(((MainActivity)getActivity()).astroCalculator.getMoonInfo().getNextFullMoon()));
        this.moonphase.setText("Moon phase: " + String.valueOf(((MainActivity)getActivity()).astroCalculator.getMoonInfo().getIllumination()) + "%");
        this.synodicDay.setText("Synodic day: " + ((MainActivity)getActivity()).astroCalculator.getMoonInfo().getAge());

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