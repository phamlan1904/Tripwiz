package com.jz_jec_g01.tripwiz.ui.myPage;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.NetworkOnMainThreadException;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.app.AlertDialog;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.jz_jec_g01.tripwiz.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.jz_jec_g01.tripwiz.model.User;
import com.jz_jec_g01.tripwiz.chats.ChatActivity;
import com.jz_jec_g01.tripwiz.feedbacks.FeedbackActivity;
import com.jz_jec_g01.tripwiz.feedbacks.FeedbackAdapter;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MyPageFragment extends Fragment {
    private static final int READ_REQUEST_CODE = 0;
    private String TAG = "com.toridge.okhttptest.MyPageFragment";
    final private int ID_REQUEST_READ_EXTERNAL_STORAGE = 99;
//    final String url = "http://10.210.20.161";
    final String url = "http://www.jz.jec.ac.jp/17jzg01";
    final Request request = new Request.Builder().url(url).build();
    final OkHttpClient client = new OkHttpClient.Builder().build();
    private View v;
    private LinearLayout selectLnagsBox;
    private LinearLayout selectAreaBox;
    private TextView entTextName;
    private ImageView myProfile;
    private ImageView myNationalFlag;
    private TextView editSelectedLang;
    private TextView editSelectedArea;
    private TextView editSelectedJob;
    private TextView entSelectedJob;
    private TextView entSelectLang;
    private TextView entSelectArea;
    private TextView textViewRate;
    private TextView entTextProfile;
    private TextView dayTitle;
    private EditText editTextProfile;
    private EditText editTexteName;
    //曜日ボタン取得（入力）
    private Button mon_day_btn;
    private Button tues_day_btn;
    private Button wed_day_btn;
    private Button thurs_day_btn;
    private Button fri_day_btn;
    private Button saturs_day_btn;
    private Button sun_day_btn;
    //曜日ボタン取得（出力）
    private Button entMondayBtn;
    private Button entTueDayBtn;
    private Button entWedDayBtn;
    private Button entThursDayBtn;
    private Button entFriDayBtn;
    private Button entSatuDayBtn;
    private Button entSunDayBtn;
    private Button btnEditProfile;
    private Button btnEntryProfile;
    private Button feedbackBtn;
    private TableRow entDayTable;
    private TableRow editDayTable;
    private TableLayout dayTableLayout;
    private String[] jobs = {"学生", "会社員", "専業主婦", "その他"};
    private String[] langs = {"日本語", "英語", "韓国語", "ベトナム語", "スペイン語", "中国語"};
    private String[] areas = {"足立区", "荒川区", "板橋区", "江戸川区", "大田区", "葛飾区", "北区", "江東区", "品川区", "渋谷区", "新宿区", "杉並区",
            "墨田区", "世田谷区", "台東区", "中央区", "練馬区", "文京区", "港区", "目黒区"};
    private String[] days = {"月","火","水","木","金","土","日"};
    private Switch switchUser;
    private RatingBar ratingBar;
    private User user;

    private Activity mActivity = null;
    int dayBtn = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_my_page, container, false);
        entTextName = v.findViewById(R.id.textViewUserName);
        myProfile = v.findViewById(R.id.imageViewUser);
        myNationalFlag = v.findViewById(R.id.imageViewNationalFlag);
        editTexteName = v.findViewById(R.id.editTexteName);
        //ガイド切り替え
        switchUser = v.findViewById(R.id.switchUser);
        //言語とエリアと曜日テーブル
        selectAreaBox = v.findViewById(R.id.selectAreasBox);
        selectLnagsBox = v.findViewById(R.id.selectLangsBox);
        dayTableLayout = v.findViewById(R.id.editDayTableLayout);
        dayTitle = v.findViewById(R.id.textViewDayOfTheWeek);
        //評価レート
        textViewRate = v.findViewById(R.id.textViewRate);
        ratingBar = v.findViewById(R.id.ratingBar);
        //言語とエリア取得
        //入力系
        editSelectedJob = v.findViewById(R.id.edit_selectJob);
        editSelectedLang = v.findViewById(R.id.edit_selectLang);
        editSelectedArea = v.findViewById(R.id.edit_selectArea);
        //出力系
        entSelectedJob =v.findViewById(R.id.entry_selectJob);
        entSelectLang = v.findViewById(R.id.entry_selectLang);
        entSelectArea = v.findViewById(R.id.entry_selectArea);
        //曜日ボタン取得（入力）
        mon_day_btn = v.findViewById(R.id.mon_day);
        tues_day_btn = v.findViewById(R.id.tues_day);
        wed_day_btn = v.findViewById(R.id.wed_day);
        thurs_day_btn = v.findViewById(R.id.thurs_day);
        fri_day_btn = v.findViewById(R.id.fri_day);
        saturs_day_btn = v.findViewById(R.id.saturs_day);
        sun_day_btn = v.findViewById(R.id.sun_day);
        //曜日ボタン取得（出力）
        entMondayBtn = v.findViewById(R.id.entry_mon_day);
        entTueDayBtn = v.findViewById(R.id.entry_tues_day);
        entWedDayBtn = v.findViewById(R.id.entry_wed_day);
        entThursDayBtn = v.findViewById(R.id.entry_thurs_day);
        entFriDayBtn = v.findViewById(R.id.entry_fri_day);
        entSatuDayBtn = v.findViewById(R.id.entry_saturs_day);
        entSunDayBtn = v.findViewById(R.id.entry_sun_day);
        //日付テーブル
        editDayTable = v.findViewById(R.id.edit_day_table);
        entDayTable = v.findViewById(R.id.entry_day_table);
        //プロフィールボタン取得buttonVisibleProfile
        btnEditProfile = v.findViewById(R.id.buttonVisibleProfile);
        btnEntryProfile = v.findViewById(R.id.buttonGoneProfile);

        //自己紹介text
        editTextProfile = v.findViewById(R.id.editTextProfile);
        entTextProfile = v.findViewById(R.id.PrTextProfile);
        //フィードバック画面遷移

        //ガイドユーザー切り替え
        // switchButtonのオンオフが切り替わった時の処理を設定
        switchUser.setOnCheckedChangeListener(
            new CompoundButton.OnCheckedChangeListener(){
                public void onCheckedChanged(CompoundButton comButton, boolean isChecked){
                    // 表示する文字列をスイッチのオンオフで変える
                    // オフからオン
                    if(isChecked) {
                        selectAreaBox.setVisibility(View.VISIBLE);
                        dayTableLayout.setVisibility(View.VISIBLE);
                        entDayTable.setVisibility(View.VISIBLE);
                        editDayTable.setVisibility(View.GONE);
                        dayTitle.setVisibility(View.VISIBLE);
                        user.setGuideStatus(1);
                    }
                    // オンからオフ
                    else {
                        switchUser.setChecked(false);
                        selectAreaBox.setVisibility(View.GONE);
                        dayTableLayout.setVisibility(View.GONE);
                        entDayTable.setVisibility(View.GONE);
                        editDayTable.setVisibility(View.GONE);
                        dayTitle.setVisibility(View.GONE);
                        user.setGuideStatus(0);
                    }
                }
            }
        );
        btnEditProfile.setOnClickListener(new Visibilitys());
        btnEntryProfile.setOnClickListener(new Visibilitys());
        //国籍と言語とエリア選択
        editSelectedJob.setOnClickListener(new btnselecteds());
        editSelectedLang.setOnClickListener(new btnselecteds());
        editSelectedArea.setOnClickListener(new btnselecteds());
        //曜日選択
        mon_day_btn.setOnClickListener(new BtnAddAlermClickListener());
        tues_day_btn.setOnClickListener(new BtnAddAlermClickListener());
        wed_day_btn.setOnClickListener(new BtnAddAlermClickListener());
        thurs_day_btn.setOnClickListener(new BtnAddAlermClickListener());
        fri_day_btn.setOnClickListener(new BtnAddAlermClickListener());
        saturs_day_btn.setOnClickListener(new BtnAddAlermClickListener());
        sun_day_btn.setOnClickListener(new BtnAddAlermClickListener());

//        feedbackBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), FeedbackActivity.class);
//                v.getContext().startActivity(intent);
//            }
//        });

        //データの受け取り（TamplateActivityからの）
        Bundle bundle = getArguments();
        Log.d("Bundle", String.valueOf(bundle));
        if(bundle != null) {
            user = (User) bundle.getSerializable("User");
            Log.d("userId", Integer.toString(user.getUserId()));
            Log.d("userName",user.getName());
        }

        //プロフィール情報取得
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        String json = "{\"name\":\"" + user.getName() + "\"}";

        RequestBody body = RequestBody.create(json, JSON);
        client.newCall(request).enqueue(new Callback() {
            final Handler mHandler = new Handler(Looper.getMainLooper());

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("データベース接続", "接続失敗");
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("データベース接続", "接続成功");

                        String urlS = url + "/Information_User.php";
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
                                            JSONArray jArrayUser = new JSONArray(jsonData);
                                            ArrayList<String> weekList = new ArrayList<>();
                                            for(int i = 0; i < jArrayUser.length(); i++) {
                                                entTextName.setText(user.getName());
                                                user.setGender(jArrayUser.getJSONObject(i).getInt("gender"));
                                                user.setJob(jArrayUser.getJSONObject(i).getString("job"));
                                                user.setNationality(jArrayUser.getJSONObject(i).getString("nationality"));
                                                user.setIntroduction(jArrayUser.getJSONObject(i).getString("introduction"));
                                                user.setProfile(jArrayUser.getJSONObject(i).getString("profile"));
                                                user.setGuideStatus(jArrayUser.getJSONObject(i).getInt("guide_status"));
                                                user.setRating(jArrayUser.getJSONObject(i).getDouble("rating_rate"));
                                                user.setArea(jArrayUser.getJSONObject(i).getString("information_area"));
                                                user.setWeek(jArrayUser.getJSONObject(i).getString("information_week"));

                                                if(user.getArea() == null) {
                                                    entSelectArea.setText("未選択");
                                                    user.setArea("未選択");
                                                }

                                                if(user.getJob() == null) {
                                                    entSelectedJob.setText("未選択");
                                                    user.setJob("未選択");
                                                }
                                                if(user.getWeek() == null) {
                                                    String nowWeek[] = user.getWeek().split(", ");
                                                    for(i = 0; i < nowWeek.length; i++) {
                                                        Log.d("日付情報", nowWeek[i]);
                                                        switch (nowWeek[i]) {
                                                            case "月":
                                                                entMondayBtn.setText("×");
                                                                break;
                                                            case "火":
                                                                entTueDayBtn.setText("×");
                                                                break;
                                                            case "水":
                                                                entWedDayBtn.setText("×");
                                                                break;
                                                            case "木":
                                                                entThursDayBtn.setText("×");
                                                                break;
                                                            case "金":
                                                                entFriDayBtn.setText("×");
                                                                break;
                                                            case "土":
                                                                entSatuDayBtn.setText("×");
                                                                break;
                                                            case "日":
                                                                entSunDayBtn.setText("×");
                                                                break;
                                                        }
                                                    }
                                                }
                                            }
                                            if(user.getUserId() > 0) {
                                                String json = "{\"user_id\":\"" + user.getUserId() + "\"}";
                                                RequestBody body = RequestBody.create(json, JSON);
                                                String langUrl = url + "/Info_Languages.php";

                                                Request request = new Request.Builder()
                                                        .url(langUrl)
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
                                                                    JSONArray jArrayLang = new JSONArray(jsonData);
                                                                    ArrayList<String> langList = new ArrayList<>();
                                                                    for(int i = 0; i < jArrayLang.length(); i++) {
                                                                        langList.add(jArrayLang.getJSONObject(i).getString("language"));
                                                                    }
                                                                    if(langList != null) {
                                                                        StringBuilder sb = new StringBuilder();
                                                                        for(int i = 0; i <langList.size(); i++) {
                                                                            if(i > 0) {
                                                                                sb.append(", ");
                                                                            }
                                                                            sb.append(langList.get(i));
                                                                        }
                                                                        String lang = sb.toString();
                                                                        Log.d("langは？", lang);
                                                                        user.setUse_languages(lang);
                                                                        entSelectLang.setText(user.getUse_languages());
                                                                    }

                                                                } catch(NetworkOnMainThreadException e) {
                                                                    e.printStackTrace();
                                                                } catch (IOException e) {
                                                                    e.printStackTrace();
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }
                                                                response.body().close();
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                            if(!user.getProfile().equals("")) {
                                                String imgUrl = url + "/image/userImage/" + user.getProfile();
                                                Log.d("URL", imgUrl);

                                                Request request = new Request.Builder()
                                                        .url(imgUrl)
                                                        .get()
                                                        .build();

                                                client.newCall(request).enqueue(new Callback() {
                                                    @Override
                                                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                                                    }

                                                    @Override
                                                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                                        if(response.isSuccessful()) {
                                                            final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                                                            mHandler.post(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    try {
                                                                        myProfile.setImageBitmap(bitmap);
                                                                    } catch(NetworkOnMainThreadException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }
                                                            });
                                                            response.body().close();
                                                        }
                                                    }
                                                });
                                            }
                                            if(!user.getNationality().equals("")) {
                                                String json = "{\"nationality\":\"" + user.getNationality() + "\"}";
                                                String urlS = url + "/NationalityFlagSearch.php";
                                                RequestBody body = RequestBody.create(json, JSON);

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
                                                                    JSONArray jArray = new JSONArray(jsonData);
                                                                    String nationalFlag;
                                                                    for(int i = 0; i < jArray.length(); i++) {
                                                                        nationalFlag = jArray.getJSONObject(i).getString("country_flag");
                                                                        if(!nationalFlag.equals("")) {
                                                                            String imgFlagUrl = url + "/image/nationality/" + nationalFlag;

                                                                            Request request = new Request.Builder()
                                                                                    .url(imgFlagUrl)
                                                                                    .get()
                                                                                    .build();

                                                                            client.newCall(request).enqueue(new Callback() {
                                                                                @Override
                                                                                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                                                                                }

                                                                                @Override
                                                                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                                                                    if(response.isSuccessful()) {
                                                                                        final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                                                                                        mHandler.post(new Runnable() {
                                                                                            @Override
                                                                                            public void run() {
                                                                                                try {
                                                                                                    myNationalFlag.setImageBitmap(bitmap);
                                                                                                } catch(NetworkOnMainThreadException e) {
                                                                                                    e.printStackTrace();
                                                                                                }
                                                                                                response.body().close();
                                                                                            }
                                                                                        });
                                                                                    }
                                                                                }
                                                                            });
                                                                        }
                                                                    }
                                                                } catch (IOException e) {
                                                                    e.printStackTrace();
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                } catch (NetworkOnMainThreadException e) {
                                                                    e.printStackTrace();
                                                                }
                                                                response.body().close();
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                            setRatingBar();
                                            entTextProfile.setText(user.getIntroduction());
                                            if(user.getGuideStatus() == 1) {
                                                switchUser.setChecked(true);
                                            }
                                            response.body().close();
                                        } catch(IOException e) {
                                            e.printStackTrace();
                                        } catch(JSONException e) {
                                            e.printStackTrace();
                                        } catch (NetworkOnMainThreadException e) {
                                            e.printStackTrace();
                                        }
                                        response.body().close();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
        return v;
    }

    public void setRatingBar() {
        double rating = user.getRating();
        textViewRate.setText(String.valueOf(rating));
        Log.d("" + rating, "setRatingBar: ");
        // 星の数を5に設定
        ratingBar.setNumStars(5);
        // レートの変更を可能にする
//        ratingBar.setIsIndicator(false);
        // レートが加減される時のステップ幅を0.3に設定
//        ratingBar.setStepSize((float) 0.3);
        // レートの設定
        ratingBar.setRating((float) rating);
    }

    private class btnselecteds implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.edit_selectLang) {
                addLangsOnCheckBox();
                Log.d("言語ダイアログ表示" , "onClick: ");
            }else if (v.getId() == R.id.edit_selectArea){
                addAreaOnCheckBox();
                Log.d("エリアダイアログ表示" , "onClick: ");
            }else if (v.getId() == R.id.edit_selectJob)
                addJobOnCheckBox();
            Log.d("職業ダイアログ表示" , "onClick: ");
        }
    }
    //エリアスピナーダイアログ表示用
    public void addAreaOnCheckBox() {
        final ArrayList<Integer> checkedItems = new ArrayList<Integer>();
        new AlertDialog.Builder(getActivity())
                .setTitle("エリア　選択")
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
                                    sb.append(", ");
                                }
                                sb.append(areaBox.get(i));
                            }
                            String area = sb.toString();
                            Log.d("areaBoxは？", area);
                            editSelectedArea.setText(area);
                            user.setArea(area);
                        } else{
                            Toast.makeText(getApplicationContext(), "選択個数が多すぎます。3つにしてください！", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    //ダイアログ選択個数判定未完成　01/27　実装予定
    //言語スピナーダイアログ表示用
    public void addLangsOnCheckBox() {
        final ArrayList<Integer> checkedItems = new ArrayList<Integer>();
        new AlertDialog.Builder(getActivity())
                .setTitle("Lang Select")
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
                                    sb.append(", ");
                                }
                                sb.append(langBox.get(i));
                            }
                            String lang = sb.toString();
                            editSelectedLang.setText(lang);
                            user.setUse_languages(lang);
                        } else{
                            Toast.makeText(getApplicationContext(), "選択個数が多すぎます。3つまでにしてください！", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    //職種スピナーダイアログ表示用
    public void addJobOnCheckBox() {
        final ArrayList<Integer> checkedItems = new ArrayList<Integer>();
        new AlertDialog.Builder(getActivity())
        .setTitle("職種選択")
        .setSingleChoiceItems(jobs, -1, new DialogInterface.OnClickListener() {
            // アイテム選択時の挙動
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkedItems.clear();
                checkedItems.add(which);
            }
        })
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            // Yesが押された時の挙動
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!checkedItems.isEmpty()) {
                    String jobBox = "";
                    for (Integer i : checkedItems) {
                        jobBox = jobs[i];
                    }
                    editSelectedJob.setText(jobBox);
                    user.setJob(jobBox);
                } else {
                    editSelectedJob.setText(user.getJob());
                }
            }
        })
        .setNegativeButton("Cancel", null)
        .show();
    }

    private class BtnAddAlermClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mon_day_btn.performClick();
            tues_day_btn.performClick();
            wed_day_btn.performClick();
            tues_day_btn.performClick();
            fri_day_btn.performClick();
            saturs_day_btn.performClick();
            sun_day_btn.performClick();
            /**
             * color and Text decisions
             */
            switch (v.getId()) {
                case R.id.mon_day:
                    if (mon_day_btn.getText().equals("◯")) {
                        mon_day_btn.setText("×");
                        entMondayBtn.setText("×");
                        /**色指定**/
                        mon_day_btn.setTextColor(Color.RED);
                        days[0] = "";
                    } else if (mon_day_btn.getText().equals("×")) {
                        mon_day_btn.setText("◯");
                        entMondayBtn.setText("◯");
                        /**色指定**/
                        mon_day_btn.setTextColor(Color.GREEN);
                        days[0] = "月";
                    }
                    break;
                case R.id.tues_day:
                    if (tues_day_btn.getText().equals("◯")) {
                        tues_day_btn.setText("×");
                        entTueDayBtn.setText("×");
                        /**色指定**/
                        tues_day_btn.setTextColor(Color.RED);
                        days[1] = "";
                    } else if (tues_day_btn.getText().equals("×")) {
                        tues_day_btn.setText("◯");
                        entTueDayBtn.setText("◯");
                        /**色指定**/
                        mon_day_btn.setTextColor(Color.GREEN);
                        days[1] = "火";
                    }
                    break;
                case R.id.wed_day:
                    if (wed_day_btn.getText().equals("◯")) {
                        wed_day_btn.setText("×");
                        entWedDayBtn.setText("×");
                        /**色指定**/
                        wed_day_btn.setTextColor(Color.RED);
                        days[2] = "";
                    } else if (wed_day_btn.getText().equals("×")) {
                        wed_day_btn.setText("◯");
                        entWedDayBtn.setText("◯");
                        /**色指定**/
                        mon_day_btn.setTextColor(Color.GREEN);
                        days[2] = "水";
                    }
                    break;
                case R.id.thurs_day:
                    if (thurs_day_btn.getText().equals("◯")) {
                        thurs_day_btn.setText("×");
                        entThursDayBtn.setText("×");
                        /**色指定**/
                        thurs_day_btn.setTextColor(Color.RED);
                        days[3] = "";
                    } else if (thurs_day_btn.getText().equals("×")) {
                        thurs_day_btn.setText("◯");
                        entThursDayBtn.setText("〇");
                        /**色指定**/
                        mon_day_btn.setTextColor(Color.GREEN);
                        days[3] = "木";
                    }
                    break;
                case R.id.fri_day:
                    if (fri_day_btn.getText().equals("◯")) {
                        fri_day_btn.setText("×");
                        entFriDayBtn.setText("×");
                        /**色指定**/
                        fri_day_btn.setTextColor(Color.RED);
                        days[4] = "";
                    } else if (fri_day_btn.getText().equals("×")) {
                        fri_day_btn.setText("◯");
                        entFriDayBtn.setText("〇");
                        /**色指定**/
                        mon_day_btn.setTextColor(Color.GREEN);
                        days[4] = "金";
                    }
                    break;
                case R.id.saturs_day:
                    if (saturs_day_btn.getText().equals("◯")) {
                        saturs_day_btn.setText("×");
                        entSatuDayBtn.setText("×");
                        /**色指定**/
                        saturs_day_btn.setTextColor(Color.RED);
                        days[5] = "";
                    } else if (saturs_day_btn.getText().equals( "×")) {
                        saturs_day_btn.setText("◯");
                        entSatuDayBtn.setText("〇");
                        /**色指定**/
                        mon_day_btn.setTextColor(Color.GREEN);
                        days[5] = "土";
                    }
                    break;
                case R.id.sun_day:
                    if (sun_day_btn.getText().equals("◯")) {
                        sun_day_btn.setText("×");
                        entSunDayBtn.setText("×");
                        /**色指定**/
                        sun_day_btn.setTextColor(Color.RED);
                        days[6] = "";
                    } else if (sun_day_btn.getText().equals("×")) {
                        sun_day_btn.setText("◯");
                        entSunDayBtn.setText("〇");
                        /**色指定**/
                        mon_day_btn.setTextColor(Color.GREEN);
                        days[6] = "日";
                    }
                    break;
            }
        }
    }

    //表示非表示切り替え
    private class Visibilitys implements View.OnClickListener {
        @Override
        public void onClick(View v){
            int gudieStatus = user.getGuideStatus();
            //登録完了状態
            //登録ボタンが押されたあとの処理
            if (v == btnEntryProfile) {
                //編集結果をセット
                user.setName(editTexteName.getText().toString());
                Log.d("名前内容？", editTexteName.getText().toString());
                user.setJob(editSelectedJob.getText().toString());
                user.setUse_languages(editSelectedLang.getText().toString());
                user.setIntroduction(editTextProfile.getText().toString());
                Log.d("自己紹介テキスト内容？", editTextProfile.getText().toString());
                user.setArea(editSelectedArea.getText().toString());

                entSelectedJob.setText(user.getJob());
                entSelectLang.setText(user.getUse_languages());
                entTextProfile.setText(user.getIntroduction());
                entSelectArea.setText(user.getArea());
                entTextName.setText(user.getName());


                //表示・非表示の切り替え
                switchUser.setVisibility(View.VISIBLE);
                editSelectedJob.setVisibility(View.GONE);
                entSelectedJob.setVisibility(View.VISIBLE);

                editSelectedLang.setVisibility(View.GONE);
                entSelectLang.setVisibility(View.VISIBLE);

                editTexteName.setVisibility(View.GONE);
                entTextName.setVisibility(View.VISIBLE);

                editTextProfile.setVisibility(View.GONE);
                entTextProfile.setVisibility(View.VISIBLE);

                //ガイド状態の判定及びデータの更新処理
                final MediaType JSON = MediaType.get("application/json; charset=utf-8");
                if (gudieStatus == 1) {
                    editDayTable.setVisibility(View.GONE);
                    entDayTable.setVisibility(View.VISIBLE);
                    editSelectedArea.setVisibility(View.GONE);
                    entSelectArea.setVisibility(View.VISIBLE);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i <= 6; i++) {
                        if(sb.length() > 0) {
                            sb.append(", ");
                        }
                        sb.append(days[i]);
                    }
                    String month = sb.toString();
                    user.setWeek(month);


                    String json = "{\"name\":\"" + user.getName() + "\", \"job\":\"" + user.getJob() + "\", \"introduction\":\"" + user.getIntroduction() + "\", " +
                                    "\"information_area\":\"" + user.getArea() + "\", \"information_week\":\"" + user.getWeek() + "\" , " +
                                    "\"profile\":\"" + user.getProfile() + "\", \"language\":\"" + user.getUse_languages() + "\", \"user_id\":\"" + user.getUserId() + "\"}";
                    RequestBody body = RequestBody.create(json, JSON);

                    if(user.getUserId() > 0) {
                        try {
                            client.newCall(request).enqueue(new Callback() {
                                final Handler mHandler = new Handler(Looper.getMainLooper());
                                @Override
                                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                                }

                                @Override
                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                    String urlS = url + "/Modification_MyPage.php";
                                    Request request = new Request.Builder()
                                            .url(urlS)
                                            .post(body)
                                            .build();

                                    OkHttpClient client = new OkHttpClient.Builder().build();
                                    client.newCall(request).enqueue(new Callback() {
                                        @Override
                                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                                        }

                                        @Override
                                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                            mHandler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(getApplicationContext(),"プロフィールがアップデートされました。", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        } catch(NetworkOnMainThreadException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    editDayTable.setVisibility(View.GONE);
                    entDayTable.setVisibility(View.GONE);
                    editSelectedArea.setVisibility(View.GONE);
                    entSelectArea.setVisibility(View.GONE);
                    String json = "{\"name\":\"" + user.getName() + "\", \"job\":\"" + user.getJob() + "\", \"introduction\":\"" + user.getIntroduction() + "\", " +
                                    "\"profile\":\"" + user.getProfile() + "\", \"language\":\"" + user.getUse_languages() + "\", \"user_id\":\"" + user.getUserId() + "\"}";
                    RequestBody body = RequestBody.create(json, JSON);

                    if(user.getUserId() > 0) {
                        try {
                            client.newCall(request).enqueue(new Callback() {
                                final Handler mHandler = new Handler(Looper.getMainLooper());
                                @Override
                                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                                }

                                @Override
                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                    String urlS = url + "/Modification_MyPage.php";
                                    Request request = new Request.Builder()
                                            .url(urlS)
                                            .post(body)
                                            .build();

                                    OkHttpClient client = new OkHttpClient.Builder().build();
                                    client.newCall(request).enqueue(new Callback() {
                                        @Override
                                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                                        }

                                        @Override
                                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                            mHandler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(getApplicationContext(),"プロフィールがアップデートされました。", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        } catch(NetworkOnMainThreadException e) {
                            e.printStackTrace();
                        }
                    }
                }

                myProfile.setOnClickListener(null);

                //ボタン表示の切り替え
                btnEditProfile.setVisibility(View.VISIBLE);
                btnEntryProfile.setVisibility(View.GONE);
            }

            //登録モード
            //編集ボタンが押されたあとの処理
            if (v == btnEditProfile) {
                //既存のデータをセット
                if(user.getJob() == null) {
                    editSelectedJob.setText("未選択");
                }
                editSelectedJob.setText(user.getJob());
                editSelectedLang.setText(user.getUse_languages());
                editTextProfile.setText(user.getIntroduction());
                if(user.getArea() == null) {
                    editSelectedArea.setText("未選択");
                }
                editSelectedArea.setText(user.getArea());
                editTexteName.setText(user.getName());

                //表示・非表示の切り替え
                switchUser.setVisibility(View.GONE);
                editSelectedJob.setVisibility(View.VISIBLE);
                entSelectedJob.setVisibility(View.GONE);

                editSelectedLang.setVisibility(View.VISIBLE);
                entSelectLang.setVisibility(View.GONE);

                editTexteName.setVisibility(View.VISIBLE);
                entTextName.setVisibility(View.GONE);
                entTextName.setVisibility(View.GONE);
                if(!editTexteName.getText().toString().equals("")) {
                    user.setName(editTexteName.getText().toString());
                }

                editTextProfile.setVisibility(View.VISIBLE);
                entTextProfile.setVisibility(View.GONE);
                if(!editTextProfile.getText().toString().equals("")) {
                    user.setIntroduction(editTextProfile.getText().toString());
                }

                //ガイド状態の判定
                if (gudieStatus == 1) {
                    editDayTable.setVisibility(View.VISIBLE);
                    entDayTable.setVisibility(View.GONE);
                    editSelectedArea.setVisibility(View.VISIBLE);
                    entSelectArea.setVisibility(View.GONE);
                } else {
                    editDayTable.setVisibility(View.GONE);
                    entDayTable.setVisibility(View.GONE);
                    editSelectedArea.setVisibility(View.GONE);
                    entSelectArea.setVisibility(View.GONE);
                }

                myProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.setType("image/*");
                        startActivityForResult(intent, READ_REQUEST_CODE);
                    }
                });

                //ボタン表示の切り替え
                btnEditProfile.setVisibility(View.GONE);
                btnEntryProfile.setVisibility(View.VISIBLE);
            }
        }
    }

    //マイページのプロフィールの編集
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData){
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
                    myProfile.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
