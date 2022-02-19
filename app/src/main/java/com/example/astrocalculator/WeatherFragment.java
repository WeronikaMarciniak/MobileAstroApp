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
import com.example.astrocalculator.API.APIConnection;
import com.example.astrocalculator.weather.WeatherLocation;
import com.google.gson.Gson;

public class WeatherFragment extends Fragment {

    Handler mHandler = new Handler();
    Runnable mTicker = null;
    public WeatherFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        TextView currentWeather = view.findViewById(R.id.currentWeather);
        TextView lat = view.findViewById(R.id.latitude);
        TextView lon = view.findViewById(R.id.longitude);
        TextView temperature = view.findViewById(R.id.temperature);
        TextView pressure = view.findViewById(R.id.pressure);
        TextView clouds = view.findViewById(R.id.clouds);
        TextView description = view.findViewById(R.id.description);
        TextView windSpeed = view.findViewById(R.id.windSpeed);
        TextView humidity = view.findViewById(R.id.humidity);
        TextView visibility = view.findViewById(R.id.visibility);
        
        mTicker = new Runnable() {
            public void run() {
                //String siteName=PreferenceManager.getDefaultSharedPreferences(getContext()).getString("siteName", "Warsaw");
                float latitude = Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("latitude", "0"));
                float longitude = Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("longitude", "0"));
                //APIConnection myApi = new APIConnection(siteName);
                APIConnection apiConnection = new APIConnection(latitude,longitude);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, apiConnection.finalUrl,
                        response -> {
                            PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("data", response).apply();
                            Gson gson = new Gson();
                            WeatherLocation location = gson.fromJson(response, WeatherLocation.class);
                            lat.setText("Latitude:" +Float.toString(latitude));
                            lon.setText("Longitude:" +Float.toString(longitude));
                            description.setText("Description: " + String.valueOf(location.current.weather.get(0).description));
                            temperature.setText("Temperature: " + String.valueOf(location.current.temp) + "C");
                            pressure.setText("Pressure: " + String.valueOf(location.current.pressure));
                            clouds.setText("Clouds: " + String.valueOf(location.current.clouds) + "%");
                            windSpeed.setText("Wind speed: " + String.valueOf(location.current.wind_speed) + " km/h");
                            humidity.setText("Humidity: " + String.valueOf(location.current.humidity) + "%");
                            visibility.setText("Visibility: " + String.valueOf(location.current.visibility) + "m");
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NetworkError || error instanceof AuthFailureError || error instanceof NoConnectionError || error instanceof TimeoutError) {
                            Toast.makeText(getContext(), "No internet connection, showing data saved.", Toast.LENGTH_SHORT).show();
                            String response = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("data", "");
                            Gson gson = new Gson();
                            WeatherLocation location = gson.fromJson(response, WeatherLocation.class);
                            lat.setText("Latitude:" +Float.toString(latitude));
                            lon.setText("Longitude:" +Float.toString(longitude));
                            description.setText("Description: " + String.valueOf(location.current.weather.get(0).description));
                            temperature.setText("Temperature: " + String.valueOf(location.current.temp) + "C");
                            pressure.setText("Pressure: " + String.valueOf(location.current.pressure));
                            clouds.setText("Clouds: " + String.valueOf(location.current.clouds) + "%");
                            windSpeed.setText("Wind speed: " + String.valueOf(location.current.wind_speed) + " km/h");
                            humidity.setText("Humidity: " + String.valueOf(location.current.humidity) + "%");
                            visibility.setText("Visibility: " + String.valueOf(location.current.visibility) + "m");
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
    public void onResume() {
        super.onResume();
        TextView currentWeather = getView().findViewById(R.id.currentWeather);
        TextView lat = getView().findViewById(R.id.latitude);
        TextView lon = getView().findViewById(R.id.longitude);
        TextView temperature = getView().findViewById(R.id.temperature);
        TextView pressure = getView().findViewById(R.id.pressure);
        TextView clouds = getView().findViewById(R.id.clouds);
        TextView description = getView().findViewById(R.id.description);
        TextView windSpeed = getView().findViewById(R.id.windSpeed);
        TextView humidity = getView().findViewById(R.id.humidity);
        TextView visibility = getView().findViewById(R.id.visibility);
       // String siteName=PreferenceManager.getDefaultSharedPreferences(getContext()).getString("siteName", "Warsaw");
        float latitude = Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("latitude", "0"));
        float longitude = Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("longitude", "0"));
        APIConnection apiConnection = new APIConnection(latitude,longitude);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiConnection.finalUrl,
                response -> {
                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("data", response).apply();
                    Gson gson = new Gson();
                    WeatherLocation location = gson.fromJson(response, WeatherLocation.class);
                    lat.setText("Latitude:" +Float.toString(latitude));
                    lon.setText("Longitude:" +Float.toString(longitude));
                    description.setText("Description: " + String.valueOf(location.current.weather.get(0).description));
                    temperature.setText("Temperature: " + String.valueOf(location.current.temp) + "C");
                    pressure.setText("Pressure: " + String.valueOf(location.current.pressure));
                    clouds.setText("Clouds: " + String.valueOf(location.current.clouds) + "%");
                    windSpeed.setText("Wind speed: " + String.valueOf(location.current.wind_speed) + " km/h");
                    humidity.setText("Humidity: " + String.valueOf(location.current.humidity) + "%");
                    visibility.setText("Visibility: " + String.valueOf(location.current.visibility) + "m");
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError || error instanceof AuthFailureError || error instanceof NoConnectionError || error instanceof TimeoutError) {
                    Toast.makeText(getContext(), "No internet connection, showing data saved.", Toast.LENGTH_SHORT).show();
                    String response = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("data", "");
                    Gson gson = new Gson();
                    WeatherLocation location = gson.fromJson(response, WeatherLocation.class);
                    lat.setText("Latitude: "+Float.toString(latitude));
                    lon.setText("Longitude: "+Float.toString(longitude));
                    description.setText("Description: " + String.valueOf(location.current.weather.get(0).description));
                    temperature.setText("Temperature: " + String.valueOf(location.current.temp) + "C");
                    pressure.setText("Pressure: " + String.valueOf(location.current.pressure));
                    clouds.setText("Clouds: " + String.valueOf(location.current.clouds) + "%");
                    windSpeed.setText("Wind speed: " + String.valueOf(location.current.wind_speed) + " km/h");
                    humidity.setText("Humidity: " + String.valueOf(location.current.humidity) + "%");
                    visibility.setText("Visibility: " + String.valueOf(location.current.visibility) + "m");
                }
            }
        });

        queue.add(stringRequest);
        mHandler.post(mTicker);
    }

    @Override
    public void onPause(){
        super.onPause();
        mHandler.removeCallbacks(mTicker);
    }
}
