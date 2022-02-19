package com.example.astrocalculator;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.astrocalculator.weather.WeatherLocation;
import com.google.gson.Gson;
import com.example.astrocalculator.API.APIConnection;
import java.util.Date;

public class ForecastFragment extends Fragment {
    Handler mHandler = new Handler();
    Runnable mTicker = null;
    public ForecastFragment() { }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        TextView forecast = view.findViewById(R.id.weatherForecast);
        TextView day1 = view.findViewById(R.id.day1);
        TextView day2 = view.findViewById(R.id.day2);
        TextView day3 = view.findViewById(R.id.day3);
        TextView day4 = view.findViewById(R.id.day4);
        TextView day5 = view.findViewById(R.id.day5);

        mTicker = new Runnable() {
            public void run() {
                //String siteName=PreferenceManager.getDefaultSharedPreferences(getContext()).getString("siteName", "Warsaw");
                float latitude = Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("latitude", "0"));
                float longitude = Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("longitude", "0"));
                APIConnection apiConnection = new APIConnection(latitude,longitude);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, apiConnection.finalUrl,
                        response -> {
                            PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("data", response).apply();
                            Date date = new Date();
                            Gson gson = new Gson();
                            WeatherLocation location = gson.fromJson(response, WeatherLocation.class);
                            date.setTime((long) location.daily.get(0).dt*1000);
                            day1.setText(date.toString().substring(0,10) + ": " + String.valueOf(location.daily.get(0).temp.day) + " C" +", " + location.daily.get(0).weather.get(0).description);
                            date.setTime((long) location.daily.get(1).dt*1000);
                            day2.setText(date.toString().substring(0,10) + ": " + String.valueOf(location.daily.get(1).temp.day) + " C" +", " + location.daily.get(1).weather.get(0).description);
                            date.setTime((long) location.daily.get(2).dt*1000);
                            day3.setText(date.toString().substring(0,10) + ": " + String.valueOf(location.daily.get(2).temp.day) + " C" +", " + location.daily.get(2).weather.get(0).description);
                            date.setTime((long) location.daily.get(3).dt*1000);
                            day4.setText(date.toString().substring(0,10) + ": " + String.valueOf(location.daily.get(3).temp.day) + " C" +", " + location.daily.get(3).weather.get(0).description);
                            date.setTime((long) location.daily.get(4).dt*1000);
                            day5.setText(date.toString().substring(0,10) + ": " + String.valueOf(location.daily.get(4).temp.day) + " C" +", " + location.daily.get(4).weather.get(0).description);
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NetworkError || error instanceof AuthFailureError || error instanceof NoConnectionError || error instanceof TimeoutError) {
                            Toast.makeText(getContext(), "No internet connection, showing data saved.", Toast.LENGTH_SHORT).show();
                            String response = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("data", "");
                            Date date = new Date();
                            Gson gson = new Gson();
                            WeatherLocation location = gson.fromJson(response, WeatherLocation.class);
                            date.setTime((long) location.daily.get(0).dt*1000);
                            day1.setText(date.toString().substring(0,10) + ": " + String.valueOf(location.daily.get(0).temp.day) + " C" +", " + location.daily.get(0).weather.get(0).description);
                            date.setTime((long) location.daily.get(1).dt*1000);
                            day2.setText(date.toString().substring(0,10) + ": " + String.valueOf(location.daily.get(1).temp.day) + " C" +", " + location.daily.get(1).weather.get(0).description);
                            date.setTime((long) location.daily.get(2).dt*1000);
                            day3.setText(date.toString().substring(0,10) + ": " + String.valueOf(location.daily.get(2).temp.day) + " C" +", " + location.daily.get(2).weather.get(0).description);
                            date.setTime((long) location.daily.get(3).dt*1000);
                            day4.setText(date.toString().substring(0,10) + ": " + String.valueOf(location.daily.get(3).temp.day) + " C" +", " + location.daily.get(3).weather.get(0).description);
                            date.setTime((long) location.daily.get(4).dt*1000);
                            day5.setText(date.toString().substring(0,10) + ": " + String.valueOf(location.daily.get(4).temp.day) + " C" +", " + location.daily.get(4).weather.get(0).description);
                        }
                    }
                });

                queue.add(stringRequest);
                mHandler.postDelayed(mTicker, Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("refresh", "15")) * 60 * 1000);

            }
        };
        mTicker.run();
        return view;
    }

    @Override
    public void onPause(){
        super.onPause();
        mHandler.removeCallbacks(mTicker);
    }
    @Override
    public void onResume() {
        super.onResume();
        TextView forecast = getView().findViewById(R.id.weatherForecast);
        TextView day1 = getView().findViewById(R.id.day1);
        TextView day2 = getView().findViewById(R.id.day2);
        TextView day3 = getView().findViewById(R.id.day3);
        TextView day4 = getView().findViewById(R.id.day4);
        TextView day5 = getView().findViewById(R.id.day5);

        mTicker = new Runnable() {
            public void run() {
                //String siteName=PreferenceManager.getDefaultSharedPreferences(getContext()).getString("siteName", "Warsaw");
                float latitude = Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("latitude", "0"));
                float longitude = Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("longitude", "0"));
                APIConnection apiConnection = new APIConnection(latitude,longitude);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, apiConnection.finalUrl,
                        response -> {
                            PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("data", response).apply();
                            Date date = new Date();
                            Gson gson = new Gson();
                            WeatherLocation location = gson.fromJson(response, WeatherLocation.class);
                            date.setTime((long) location.daily.get(0).dt*1000);
                            day1.setText(date.toString().substring(0,10) + ": " + String.valueOf(location.daily.get(0).temp.day) + " C" +", " + location.daily.get(0).weather.get(0).description);
                            date.setTime((long) location.daily.get(1).dt*1000);
                            day2.setText(date.toString().substring(0,10) + ": " + String.valueOf(location.daily.get(1).temp.day) + " C" +", " + location.daily.get(1).weather.get(0).description);
                            date.setTime((long) location.daily.get(2).dt*1000);
                            day3.setText(date.toString().substring(0,10) + ": " + String.valueOf(location.daily.get(2).temp.day) + " C" +", " + location.daily.get(2).weather.get(0).description);
                            date.setTime((long) location.daily.get(3).dt*1000);
                            day4.setText(date.toString().substring(0,10) + ": " + String.valueOf(location.daily.get(3).temp.day) + " C" +", " + location.daily.get(3).weather.get(0).description);
                            date.setTime((long) location.daily.get(4).dt*1000);
                            day5.setText(date.toString().substring(0,10) + ": " + String.valueOf(location.daily.get(4).temp.day) + " C" +", " + location.daily.get(4).weather.get(0).description);
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NetworkError || error instanceof AuthFailureError || error instanceof NoConnectionError || error instanceof TimeoutError) {
                            Toast.makeText(getContext(), "No internet connection, showing data saved.", Toast.LENGTH_SHORT).show();
                            String response = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("data", "");
                            Date date = new Date();
                            Gson gson = new Gson();
                            WeatherLocation location = gson.fromJson(response, WeatherLocation.class);
                            date.setTime((long) location.daily.get(0).dt*1000);
                            day1.setText(date.toString().substring(0,10) + ": " + String.valueOf(location.daily.get(0).temp.day) + " C" +", " + location.daily.get(0).weather.get(0).description);
                            date.setTime((long) location.daily.get(1).dt*1000);
                            day2.setText(date.toString().substring(0,10) + ": " + String.valueOf(location.daily.get(1).temp.day) + " C" +", " + location.daily.get(1).weather.get(0).description);
                            date.setTime((long) location.daily.get(2).dt*1000);
                            day3.setText(date.toString().substring(0,10) + ": " + String.valueOf(location.daily.get(2).temp.day) + " C" +", " + location.daily.get(2).weather.get(0).description);
                            date.setTime((long) location.daily.get(3).dt*1000);
                            day4.setText(date.toString().substring(0,10) + ": " + String.valueOf(location.daily.get(3).temp.day) + " C" +", " + location.daily.get(3).weather.get(0).description);
                            date.setTime((long) location.daily.get(4).dt*1000);
                            day5.setText(date.toString().substring(0,10) + ": " + String.valueOf(location.daily.get(4).temp.day) + " C" +", " + location.daily.get(4).weather.get(0).description);
                        }
                    }
                });

                queue.add(stringRequest);
                mHandler.post(mTicker);

            }
        };
    }
}