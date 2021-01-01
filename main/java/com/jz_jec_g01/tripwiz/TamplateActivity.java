package com.jz_jec_g01.tripwiz;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jz_jec_g01.tripwiz.chats.ChatActivity;
import com.jz_jec_g01.tripwiz.feedbacks.FeedbackActivity;
import com.jz_jec_g01.tripwiz.model.User;
import com.jz_jec_g01.tripwiz.ui.guide.GuideFragment;
import com.jz_jec_g01.tripwiz.ui.home.HomeFragment;
import com.jz_jec_g01.tripwiz.ui.myPage.MyPageFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.HashMap;

import static com.facebook.FacebookSdk.getApplicationContext;

public class TamplateActivity extends AppCompatActivity implements LocationListener {
    private static final String TAG = "DEB";
    private User user;
    String myArea ="";
    private BottomNavigationView navView;
    FirebaseUser fuser;
    DatabaseReference reference;
    private LocationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tamplate);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        Log.d("ユーザ", user.getName());
        Log.d("ユーザー２", Integer.toString(user.getUserId()));

        navView = findViewById(R.id.nav_view);

        Bundle bundle = new Bundle();
        bundle.putSerializable("User", user);
        Log.d("Bundle情報", String.valueOf(bundle));

        HomeFragment homeFragment = HomeFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, homeFragment)
                .commit();

        /**
         *クリック時画面遷移メソッド
         * XML bottom_nav_menu　activity_tamplete
         * */
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d(TAG, "ナビゲーション動作確認  " + item.getItemId());
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        HomeFragment homeFragment =  HomeFragment.newInstance();

                        Log.d(TAG, "Homeボタンクリック");
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, homeFragment)
                                .commit();
                        return true;
                    case R.id.navigation_guide:
                        GuideFragment guideFragment = GuideFragment.newInstance();

                        Log.d(TAG, "Guideボタンクリック");
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, guideFragment)
                                .commit();
                        return true;

                    case R.id.navigation_myPage:
                        MyPageFragment myPageFragment = new MyPageFragment();
                        myPageFragment.setArguments(bundle);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, myPageFragment)
                                .commit();
                        Log.d(TAG, "Mypegeボタンクリック");
                        return true;
                }
                return false;
            }
        });
        //座標取得
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, TamplateActivity.this);
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 1, TamplateActivity.this);
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
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public void onLocationChanged(Location location) {
        myArea = location.getLatitude() + ","+ location.getLongitude();
        Log.d(TAG, "onLocationChanged: 現在地　取得" + myArea);
        Locatioins(myArea);
    }
    private void Locatioins(String myArea){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("location", myArea);
        reference.updateChildren(hashMap);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.template_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case  R.id.logout:
                FirebaseAuth.getInstance().signOut();
                // change this code beacuse your app will crash
                startActivity(new Intent(TamplateActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                return true;
            case R.id.chat:
                startActivity(new Intent(TamplateActivity.this, ChatActivity.class));
                return true;
            case R.id.navigation:
                startActivity(new Intent(TamplateActivity.this, MapsActivity.class));
                return true;
            case R.id.feedback:
                startActivity(new Intent(TamplateActivity.this, FeedbackActivity.class));
                return true;

        }

        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        switch (requestCode) {
            case 123 :
                Log.d("" + requestCode, "requestCode　ガイド　値　取得");
                Log.d("" + resultCode, "resultCode　ガイド値　取得");
                Log.d("" + bundle.getString("key.StringAreas"), "エリア　ガイド　値　取得");
                Log.d("" + bundle.getString("key.StringDays"), "日付　ガイド　値　取得");
                Log.d("" + bundle.getString("key.StringLangs"), "言語　ガイド　値　取得");
                Log.d("" + bundle.getString("key.StringLangs"), "年齢　ガイド　値　取得");
                Log.d("" + bundle.getString("key.StringGenders"), "性別　ガイド　値　取得");
                Log.d("" + bundle.getString("key.StringCountrys"), "国籍　ガイド　値　取得");
                break;
            default:
                break;
        }
    }
}
