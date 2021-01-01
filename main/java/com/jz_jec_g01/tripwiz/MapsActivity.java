package com.jz_jec_g01.tripwiz;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MapsActivity extends AppCompatActivity{
    String myArea = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        test0();
        //test1();
    }
    public void onLocationChanged(Location location) {
        myArea = "緯度：" + location.getLatitude() + "経度：" + location.getLongitude();
    }

    // 地名を入れて経路を検索
    private void test0(){
        // 起点
        String start = "";

        // 目的地
        String destination = "";

        // 移動手段：電車:r, 車:d, 歩き:w
        String[] dir = {"r", "d", "w"};

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setClassName("com.google.android.apps.maps",
                "com.google.android.maps.MapsActivity");
        String location = "熊谷";
        // 出発地, 目的地, 交通手段
        String str = String.format(Locale.US,
                "http://maps.google.com/maps?saddr="+ myArea + "&daddr=" + location +"&dirflg=%s",
                start, destination, dir[1]);

        intent.setData(Uri.parse(str));
        startActivity(intent);

    }
}