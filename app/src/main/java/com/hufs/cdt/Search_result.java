package com.hufs.cdt;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;

import android.app.ProgressDialog;

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public class Search_result extends AppCompatActivity implements OnMapReadyCallback{
    final static int myrequestCode=11;
    private boolean positionFlag;
    LatLng w;
    public static double xandyx=0;
    public static double xandyy=0;
    int a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_completion);

        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.map, mapFragment).commit();
        }


        mapFragment.getMapAsync(this);

    }

    public void thrun(){


        Thread thread= new Thread(new Runnable() {
            private boolean positionFlag;
            private void getPoint(String... addr) {
                geo geo = new geo(Search_result.this, listener);
                geo.execute(addr);
            }

            private geo.OnGeoPointListener listener = new geo.OnGeoPointListener() {
                @Override
                public void onPoint(geo.Point[] p) {
                    int sCnt = 0;
                    for (geo.Point point : p) {

                        if (point.havePoint) sCnt++;
                        final String a= point.toString();
                        double k=  point.x;
                        double d=  point.y;
                        xandyx=point.x;
                        xandyy=point.y;




                    }

                }

                @Override
                public void onProgress(int progress, int max) {

                }
            };
            @Override
            public void run() {
                final EditText editText=(EditText)findViewById(R.id.search_ettx);
                final String c=editText.getText().toString();

                getPoint(
                        c

                );




            }


        });

        thread.start();




    }
    @UiThread
    @Override
    public void onMapReady ( @NonNull final NaverMap naverMap){
        final  Marker marker1 = new Marker();
        Button button =(Button)findViewById(R.id.search_btn3);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                thrun();
                LatLng k = new LatLng((double) xandyy, (double) xandyx);
                marker1.setPosition(k);
                marker1.setMap(naverMap);
                naverMap.moveCamera(CameraUpdate.scrollTo(k));



            }



        });




    }

}
