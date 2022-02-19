package com.example.astrocalculator.API;
import android.content.Context;
import androidx.preference.PreferenceManager;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class APIConnection {

    public String finalUrl;
    //https://api.openweathermap.org/data/2.5/onecall?lat=50&lon=50&exclude=minutely,hourly,alerts&appid=eca4111eacf18a557ba3c3bf66439030
    //http://api.openweathermap.org/data/2.5/weather?q=Warsaw&appid=eca4111eacf18a557ba3c3bf66439030
    String apiKey = "eca4111eacf18a557ba3c3bf66439030";
    //String urlBase1 = "https://api.openweathermap.org/data/2.5/weather?";
    String urlBase2 = "https://api.openweathermap.org/data/2.5/onecall?";
    //String siteName;
    float longitude;
    float latitude;


    /*public APIConnection(String siteName) {//float latitude, float longitude
        this.siteName = siteName;
        this.finalUrl = urlBase1 + "q=" + siteName + "&appid=" + apiKey ; //"+&units=metric"
    }*/
    public APIConnection(float latitude, float longitude) {//float latitude, float longitude
        this.latitude = latitude;
        this.longitude = longitude;
        this.finalUrl = urlBase2 + "lat=" + latitude + "&lon=" + longitude + "&exclude=minutely,hourly,alerts&appid=" + apiKey + "&units=metric";
    }

}
