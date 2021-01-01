package com.jz_jec_g01.tripwiz.feedbacks;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jz_jec_g01.tripwiz.R;
import com.jz_jec_g01.tripwiz.SignupActivity;
import com.jz_jec_g01.tripwiz.TamplateActivity;
import com.jz_jec_g01.tripwiz.chats.MessageActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.facebook.FacebookSdk.getApplicationContext;

public class FeedbackActivity extends AppCompatActivity {
    private Button returnBtn;
    private Button entryBtn;
    private LocationManager manager;
    private TextView textView;
    private TextView locationText;
    private RecyclerView recyclerView = null;
    private EditText guide_name_text;
    private String UserName ="名前を入力してください";

    private static final String[] names = {
            "katakuriko", "mai",
            "miki", "nagi", "saya",
            "toko"
    };
    private static final Integer[] photos = {
            R.drawable.katakuriko, R.drawable.mai, R.drawable.miki,
            R.drawable.nagi, R.drawable.saya, R.drawable.toko,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toast.makeText(getApplicationContext(), "Activity Restart しました", Toast.LENGTH_SHORT).show();

        //デフォルト名前セット取得
        Intent intent = getIntent();
        UserName = intent.getStringExtra("TestFeedBack");
        Log.d("受け取りテスト1 ", "onCreate: " + UserName);
        recyclerView = findViewById(R.id.recycler_view);
        // レイアウトマネージャを設定(ここで縦方向の標準リストであることを指定)
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imagesList();
        returnBtn = findViewById(R.id.buttonReturn);
        entryBtn = findViewById(R.id.buttonEntry);
        returnBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        entryBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showMyDialog();
            }
        });

    }
    public void imagesList (){
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(rLayoutManager);


        // 配列をArrayListにコピー
        List<String> itemNames = new ArrayList<String>(Arrays.asList(names));
        List<Integer> itemImages = new ArrayList<Integer>(Arrays.asList(photos));

        // specify an adapter (see also next example)
        RecyclerView.Adapter rAdapter = new com.jz_jec_g01.tripwiz.feedbacks.FeedbackAdapter(itemImages, itemNames);
        recyclerView.setAdapter(rAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL); // ここで横方向に設定
        recyclerView.setLayoutManager(manager);

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));

    }
    // ダイアログが生成された時に呼ばれるメソッド ※必須
    //ダイアログを作る。
    public void showMyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View dialog_view = inflater.inflate(R.layout.my_dialog, null);
        guide_name_text = dialog_view.findViewById(R.id.guide_name_text);
        guide_name_text.setText(UserName);
        Log.d("受け取りテスト ", "onCreate: " + guide_name_text);

        RatingBar ratingBar = dialog_view.findViewById(R.id.ratingBar_title_text);
        ratingBar.setIsIndicator(false);
        ratingBar.setMax(5);
        //レイアウトファイルからビューを取得

        //レイアウト、題名、OKボタンとキャンセルボタンをつけてダイアログ作成
        builder.setView(dialog_view)
                .setTitle("ガイド評価")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                 Intent intent= new Intent(FeedbackActivity.this, FeedbackActivity.class);
                                 startActivity(intent);
                            }
                        })
                .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        /*キャンセルされたときの処理*/
                    }
                });

        AlertDialog myDialog = builder.create();
        myDialog.setCanceledOnTouchOutside(false);
        //ダイアログ画面外をタッチされても消えないようにする。
        myDialog.show();
        //ダイアログ表示
    }
}
