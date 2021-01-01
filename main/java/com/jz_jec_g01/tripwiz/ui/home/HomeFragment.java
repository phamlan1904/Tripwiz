package com.jz_jec_g01.tripwiz.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.CallbackManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jz_jec_g01.tripwiz.MainActivity;
import com.jz_jec_g01.tripwiz.R;
import com.jz_jec_g01.tripwiz.TamplateActivity;
import com.jz_jec_g01.tripwiz.ui.guide.GuideAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class HomeFragment extends Fragment {

    // 表示する画像の名前（拡張子無し）
    private static final String[] names = {
            "#風景　#海 #きれい　#ブルー","#風景　#森 #きれい　#自然","#風景　#山 #きれい　#エベレスト"
    };
    private View v;

    // RecyclerViewとAdapter
    private RecyclerView recyclerView = null;
    private GuideAdapter rAdapter = null;
    private Activity mActivity = null;
    private FirebaseAuth mAuth;
//    private Button location

    // それぞれの画像ファイルをdarawableに入れます
    // ArrayListにコピーするためintからInteger型にしました
    private static final Integer[] photos = {
            R.drawable.sea, R.drawable.forest, R.drawable.mountain,

    };
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
    public void imagesList (){
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(rLayoutManager);


        // 配列をArrayListにコピー
        List<String> itemNames = new ArrayList<String>(Arrays.asList(names));
        List<Integer> itemImages = new ArrayList<Integer>(Arrays.asList(photos));

        // specify an adapter (see also next example)
        RecyclerView.Adapter rAdapter = new HomeAdapter(itemImages, itemNames);
        recyclerView.setAdapter(rAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL); // ここで横方向に設定
        recyclerView.setLayoutManager(manager);

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        // RecyclerViewの参照を取得
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        // レイアウトマネージャを設定(ここで縦方向の標準リストであることを指定)
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        imagesList();
       return v;
    }
}