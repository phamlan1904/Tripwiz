package com.jz_jec_g01.tripwiz;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jz_jec_g01.tripwiz.ui.guide.GuideFragment;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SearchGuideActivity extends AppCompatActivity implements LocationListener {
//    final String url = "http://10.210.20.161";
    final String url = "http://www.jz.jec.ac.jp/17jzg01";
    final Request request = new Request.Builder().url(url).build();
    final OkHttpClient client = new OkHttpClient.Builder().build();
    private Button serchBtn;
    private TextView selectedArea;
    private TextView selectDays;
    private TextView selectedLang;
    private TextView selectOld;
    private TextView selectGender;
    private TextView selectCountry;
    //現在地取得後　指定の区をデフォルト選択
    private String[] areas = {"足立区", "荒川区", "板橋区", "江戸川区", "大田区", "葛飾区", "北区", "江東区", "品川区", "渋谷区", "新宿区", "杉並区",
            "墨田区", "世田谷区", "台東区", "中央区", "練馬区", "文京区", "港区", "目黒区"};
    private String[] days = {"月","火","水","木","金","土","日"};
    private String[] langs = {"日本語", "英語", "韓国語", "ベトナム語", "スペイン語", "中国語"};
    private String[] old = {"10代","20代","30代","40代","50代"};
    private String[] gender = {"男性","女性"};
    private String[] country = {"日本", "アメリカ", "韓国", "ベトナム", "ペルー", "中国"};
    private LocationManager manager;
    private int managerCnt = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_guide);
        //ボタン取得
        serchBtn = findViewById(R.id.searchBtn);
        selectedArea = findViewById(R.id.selectAreas);
        selectDays = findViewById(R.id.selectDays);
        selectedLang = findViewById(R.id.selectLangs);
        selectOld = findViewById(R.id.selectOld);
        selectGender = findViewById(R.id.selectGender);
        selectCountry = findViewById(R.id.selectCountry);
        //検索クリックアクション
        serchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //textView値をフラグメントに渡す専用変数
                String areas    = selectedArea.getText().toString();
                String days     = selectDays.getText().toString();
                String langs    = selectedLang.getText().toString();
                String olds     = selectOld.getText().toString();
                String genders  = selectGender.getText().toString();
                String countrys = selectCountry.getText().toString();

                final MediaType JSON = MediaType.get("application/json; charset=utf-8");
                String json = "{\"area\":\"" + areas + "\", \"week\":\"" + days + "\", \"language\":\"" + langs + "\", \"" +
                                "age\":\"" + olds + "\", \"gender\":\"" + genders + "\", \"nationality\":\"" + countrys + "\"}";

                RequestBody body = RequestBody.create(json, JSON);

                client.newCall(request).enqueue(new Callback() {
                    final Handler mHandler = new Handler(Looper.getMainLooper());
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                String urlS = url + "/GuideSearch.php";
                                Request request = new Request.Builder()
                                        .url(urlS)
                                        .post(body)
                                        .build();

                                client.newCall(request).enqueue(new Callback() {
                                    @Override
                                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                                    }

                                    @Override
                                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                        mHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    String jsonData = response.body().string();
                                                    Log.d("Json", jsonData);
                                                    JSONArray jArrayGuide = new JSONArray(jsonData);
                                                    //intent 設定
                                                    Intent returnIntent = new Intent();
                                                    int guideId = 0;
                                                    String name = "";
                                                    String profile = "";
                                                    int rating_rate;
                                                    for(int i = 0; i < jArrayGuide.length(); i++) {
                                                        //フラグメントに値を渡す
                                                        guideId = jArrayGuide.getJSONObject(i).getInt("user_id");
                                                        name = jArrayGuide.getJSONObject(i).getString("name");
                                                        profile = jArrayGuide.getJSONObject(i).getString("profile");

                                                    }
                                                    returnIntent.putExtra("key.StringAreas",guideId);  //returndataに１をセット
                                                    returnIntent.putExtra("key.StringDays",name);
                                                    returnIntent.putExtra("key.StringLangs",profile);
                                                    setResult(RESULT_OK, returnIntent);
                                                    finish();
                                                    // Intent intent = new Intent(SearchGuideActivity.this, TamplateActivity.class);
                                                    // intent.putExtra("goto", "GuideFragment");
                                                    // startActivity(intent);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });

        //条件クリックアクション
        selectedArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAreaOnCheckBox();
            }
        });
        selectDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDaysOnCheckBox();
            }
        });
        selectedLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLangsOnCheckBox();
            }
        });
        selectOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOldOnCheckBox();
            }
        });
        selectGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGenderOnCheckBox();
            }
        });
        selectCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCountryOnCheckBox();
            }
        });
        //座標取得
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }
    //条件ダイアログ
    public void addAreaOnCheckBox() {
        final ArrayList<Integer> checkedItems = new ArrayList<Integer>();
        new AlertDialog.Builder(this)
                .setTitle("Area Select")
                .setMultiChoiceItems(areas, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) checkedItems.add(which);
                        else checkedItems.remove((Integer) which);
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ArrayList<String> areaBox = new ArrayList<String>();
                        if(checkedItems.size() <= 3) {
                            StringBuilder sb = new StringBuilder();
                            for (Integer i : checkedItems) {
                                areaBox.add(areas[i]);
                            }
                            Collections.sort(areaBox, Collections.reverseOrder());
                            for(int i = 0; i < areaBox.size(); i++) {
                                if(sb.length() > 0) {
                                    sb.append("|");
                                }
                                sb.append(areaBox.get(i));
                            }
                            String area = sb.toString();
                            Log.d("areaBoxは？", area);
                            selectedArea.setText(area);
                        } else{
                            Toast.makeText(getApplicationContext(), "選択個数が多すぎます。3つにしてください！", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    public void addDaysOnCheckBox() {
        final ArrayList<Integer> checkedItems = new ArrayList<Integer>();
        new AlertDialog.Builder(this)
                .setTitle("Days Select")
                .setMultiChoiceItems(days, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) checkedItems.add(which);
                        else checkedItems.remove((Integer) which);
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ArrayList<String> daysBox = new ArrayList<String>();
                        if(checkedItems.size() <= 3) {
                            StringBuilder sb = new StringBuilder();
                            for (Integer i : checkedItems) {
                                daysBox.add(days[i]);
                            }
                            Collections.sort(daysBox, Collections.reverseOrder());
                            for(int i = 0; i < daysBox.size(); i++) {
                                if(sb.length() > 0) {
                                    sb.append("|");
                                }
                                sb.append(daysBox.get(i));
                            }
                            String days = sb.toString();
                            selectDays.setText(days);
                        } else{
                            Toast.makeText(getApplicationContext(), "選択個数が多すぎます。3つにしてください！", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    public void addLangsOnCheckBox() {
        final ArrayList<Integer> checkedItems = new ArrayList<Integer>();
        new AlertDialog.Builder(this)
                .setTitle("Lang Lelect")
                .setMultiChoiceItems(langs, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) checkedItems.add(which);
                        else checkedItems.remove((Integer) which);
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ArrayList<String> langBox = new ArrayList<String>();
                        if(checkedItems.size() <= 3) {
                            StringBuilder sb = new StringBuilder();
                            for (Integer i : checkedItems) {
                                langBox.add(langs[i]);
                            }
                            Collections.sort(langBox, Collections.reverseOrder());
                            for(int i = 0; i < langBox.size(); i++) {
                                if(sb.length() > 0) {
                                    sb.append("|");
                                }
                                sb.append(langBox.get(i));
                            }
                            String lang = sb.toString();
                            selectedLang.setText(lang);
                        } else {
                            Toast.makeText(getApplicationContext(), "選択個数が多すぎます。3つにしてください！", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    public void addOldOnCheckBox() {
        final ArrayList<Integer> checkedItems = new ArrayList<Integer>();
        new AlertDialog.Builder(this)
                .setTitle("Area Select")
                .setMultiChoiceItems(old, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) checkedItems.add(which);
                        else checkedItems.remove((Integer) which);
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ArrayList<String> oldBox = new ArrayList<String>();
                        if(checkedItems.size() <= 3) {
                            StringBuilder sb = new StringBuilder();
                            for (Integer i : checkedItems) {
                                oldBox.add(old[i]);
                            }
                            Collections.sort(oldBox, Collections.reverseOrder());
                            for(int i = 0; i < oldBox.size(); i++) {
                                if(sb.length() > 0) {
                                    sb.append("|");
                                }
                                sb.append(oldBox.get(i));
                            }
                            String old = sb.toString();
                            selectOld.setText(old);
                        } else {
                            Toast.makeText(getApplicationContext(), "選択個数が多すぎます。3つにしてください！", Toast.LENGTH_LONG).show();
                        }

                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    public void addGenderOnCheckBox() {
        final ArrayList<Integer> checkedItems = new ArrayList<Integer>();
        new AlertDialog.Builder(this)
                .setTitle("Area Select")
                .setMultiChoiceItems(gender, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) checkedItems.add(which);
                        else checkedItems.remove((Integer) which);
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ArrayList<String> genderBox = new ArrayList<String>();
                        if(checkedItems.size() <= 3) {
                            StringBuilder sb = new StringBuilder();
                            for (Integer i : checkedItems) {
                                    genderBox.add(gender[i]);
                            }
                            Collections.sort(genderBox, Collections.reverseOrder());
                            for(int i = 0; i < genderBox.size(); i++) {
                                if(sb.length() > 0) {
                                    sb.append("|");
                                }
                                sb.append(genderBox.get(i));
                            }
                            String gender = sb.toString();
                            selectGender.setText(gender);
                        } else {
                            Toast.makeText(getApplicationContext(), "選択個数が多すぎます。2つにしてください！", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    public void addCountryOnCheckBox()                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          {
        final ArrayList<Integer> checkedItems = new ArrayList<Integer>();
        new AlertDialog.Builder(this)
                .setTitle("Area Select")
                .setMultiChoiceItems(country, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) checkedItems.add(which);
                        else checkedItems.remove((Integer) which);
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ArrayList<String> countryBox = new ArrayList<String>();
                        if(checkedItems.size() <= 3) {
                            StringBuilder sb = new StringBuilder();
                            for (Integer i : checkedItems) {
                                countryBox.add(country[i]);
                            }
                            Collections.sort(countryBox, Collections.reverseOrder());
                            for(int i = 0; i < countryBox.size(); i++) {
                                if(sb.length() > 0) {
                                    sb.append("|");
                                }
                                sb.append(countryBox.get(i));
                            }
                            String country = sb.toString();
                            selectCountry.setText(country);
                        } else {
                            Toast.makeText(getApplicationContext(), "選択個数が多すぎます。3つにしてください！", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    @Override
    protected void onStart() {
        super.onStart();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return;
            }
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, SearchGuideActivity.this);
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 1, SearchGuideActivity.this);
    }
    @Override
    protected void onStop() {
        super.onStop();

        if (manager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            manager.removeUpdates(this);
        }
    }

    public void getAddress(double latitude,double longitute) {
        StringBuffer strAddr = new StringBuffer();
        Geocoder gcoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> lstAddrs = gcoder.getFromLocation(latitude, longitute, 1);
            for (Address addr : lstAddrs) {
                int idx = addr.getMaxAddressLineIndex();
                for (int i = 0; i <= idx; i++) {
                    strAddr.append(addr.getAddressLine(i));
                }
            }
            String newStr = strAddr.toString();
            String[] names = newStr.split(" ");
            String add = names[1];
            //表示ダイアログに区セット
            for (int i = 0; i <= areas.length - 1; i++) {
                if(add.matches(".*"+ areas[i] + ".*")){
                    if (managerCnt == 0) {
                        selectedArea.setText(areas[i]);
                    } else {
                        selectedArea.setText("未選択");
                    }
                }
            }
            Toast.makeText(this, "現住所　"+ names[1], Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    //座標取得
    @Override
    public void onLocationChanged(Location location) {

        String text = "緯度：" + location.getLatitude() + "経度：" + location.getLongitude();
        getAddress(location.getLatitude(),location.getLongitude());
    }


}
