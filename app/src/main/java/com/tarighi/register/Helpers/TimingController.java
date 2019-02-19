package com.tarighi.register.Helpers;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;
import com.tarighi.register.aladhan.TimingsByCityClass;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import cz.msebera.android.httpclient.Header;

public class TimingController {


    private OnAPIResponseEventListener mOnAPIResponseEventListener;

    public void setOnAPIResponseEventListener(OnAPIResponseEventListener eventListener) {
        mOnAPIResponseEventListener = eventListener;
    }

    private String cityName = "tehran";
    private String getAPIUrl(String cityName){ return  "https://api.aladhan.com/v1/timingsByCity?city=" + cityName + "&country=iran&method=8";}


    public Timings getTimings(String values) {

        Gson gson=new Gson();


        TimingsByCityClass timingsByCity=gson.fromJson(values,TimingsByCityClass.class);


        Timings result = new Timings();
        try {


            result.City=this.cityName;
            result.Fajr = timingsByCity.getData().getTimings().getFajr();
            result.Sunrise = timingsByCity.getData().getTimings().getSunrise();
            result.Dhuhr = timingsByCity.getData().getTimings().getDhuhr();
            result.Asr = timingsByCity.getData().getTimings().getAsr();
            result.Sunset = timingsByCity.getData().getTimings().getSunset();
            result.Maghrib = timingsByCity.getData().getTimings().getMaghrib();
            result.Isha = timingsByCity.getData().getTimings().getIsha();
            result.Imsak = timingsByCity.getData().getTimings().getImsak();
            result.Midnight = timingsByCity.getData().getTimings().getMidnight();

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

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(getAPIUrl(cityName),new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                getTimings(response.toString());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("aliT",Integer.toString( statusCode));
            }
        });

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    URL url = new URL(getAPIUrl(cityName));
//                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                    con.setRequestMethod("GET();
//                    con.setRequestProperty("User-Agent", "Mozila/5.0();
//                    int responseCode = con.getResponseCode();
//                    if (responseCode == HttpURLConnection.HTTP_OK) {
//                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//                        String inputLine;
//                        StringBuffer response = new StringBuffer();
//                        while ((inputLine = in.readLine()) != null) {
//                            response.append(inputLine);
//                        }
//                        getTimings(response.toString());
//                    }
//
//                } catch (Exception e) {
//                    Log.d("aliT", e.toString());
//                }
//            }
//        }).start();
    }
}
