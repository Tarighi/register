package com.tarighi.register.Helpers;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class TimingController {


    private OnAPIResponseEventListener mOnAPIResponseEventListener;

    public void setOnAPIResponseEventListener(OnAPIResponseEventListener eventListener) {
        mOnAPIResponseEventListener = eventListener;
    }

    private String cityName = "tehran";
    private String getAPIUrl(String cityName){ return  "https://api.aladhan.com/v1/timingsByCity?city=" + cityName + "&country=iran&method=8";}


    public Timings getTimings(String values) {
        Timings result = new Timings();
        try {
            JSONObject jObj = new JSONObject(values);
            JSONObject dataObj = new JSONObject(jObj.getString("data"));
            JSONObject timingsObj = new JSONObject(dataObj.getString("timings"));

            result.City=this.cityName;
            result.Fajr = timingsObj.getString("Fajr");
            result.Sunrise = timingsObj.getString("Sunrise");
            result.Dhuhr = timingsObj.getString("Dhuhr");
            result.Asr = timingsObj.getString("Asr");
            result.Sunset = timingsObj.getString("Sunset");
            result.Maghrib = timingsObj.getString("Maghrib");
            result.Isha = timingsObj.getString("Isha");
            result.Imsak = timingsObj.getString("Imsak");
            result.Midnight = timingsObj.getString("Midnight");

            if (mOnAPIResponseEventListener != null) {
                mOnAPIResponseEventListener.onAPIResponse(result);
            }
        } catch (Exception e) {
            Log.d("aliT", e.toString());
        }
        return result;
    }

    public void callTimingsAPI(final String cityName) {
        this.cityName = cityName;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(getAPIUrl(cityName));
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("User-Agent", "Mozila/5.0");
                    int responseCode = con.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        getTimings(response.toString());
                    }

                } catch (Exception e) {
                    Log.d("aliT", e.toString());
                }
            }
        }).start();
    }
}
