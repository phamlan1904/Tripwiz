package com.jz_jec_g01.tripwiz;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.LinearLayoutManager;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class TimeLineActivity extends AppCompatActivity {

    private static final String[] names = {
            "tokyo", "tokyo"
    };

    // それぞれの画像ファイルをdrawableに入れます
    // ArrayListにコピーするためintからInteger型にしました
    private static final Integer[] photos = {
            R.drawable.tokyo, R.drawable.tokyo,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        RecyclerView recyclerView = findViewById(R.id.recyclerViewTimeLine);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(this);

//        recyclerView.setLayoutManager(rLayoutManager);


        // 配列をArrayListにコピー
        List<String> itemNames = new ArrayList<String>(Arrays.asList(names));
        List<Integer> itemImages = new ArrayList<Integer>(Arrays.asList(photos));

        for(int i=0; i<itemNames.size() ;i++ ){
            String str = String.format(Locale.ENGLISH, itemNames.get(i));
        }

        // specify an adapter (see also next example)
//        RecyclerView.Adapter rAdapter = new GuideAdapter(itemImages, itemNames);
//        recyclerView.setAdapter(rAdapter);
    }
}
