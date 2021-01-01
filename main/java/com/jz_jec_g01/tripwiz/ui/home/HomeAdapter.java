package com.jz_jec_g01.tripwiz.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.jz_jec_g01.tripwiz.R;
import com.jz_jec_g01.tripwiz.chats.ChatActivity;
import com.jz_jec_g01.tripwiz.ui.guide.GuideAdapter;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private List<Integer> iImages;
    private List<String> iNames;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        ImageView imageView;
        TextView textView;

        ViewHolder(View v) {
            super(v);
            imageView = v.findViewById(R.id.user_image_view);
            textView = v.findViewById(R.id.user_comment_view);

        }


    }

    // Provide a suitable constructor (depends on the kind of dataset)
    HomeAdapter(List<Integer> itemImages, List<String> itemNames) {
        this.iImages = itemImages;
        this.iNames = itemNames;
    }

    // Create new views (invoked by the layout manager)
    @Override
    @NonNull
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_home, parent, false);



        return new HomeAdapter.ViewHolder(view);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.imageView.setImageResource(iImages.get(position));
        holder.textView.setText(iNames.get(position));

//        // like Action いいねアクション未完成
//        Log.d("likeCnt", "いいねアクション１: ");
//        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (likeCnt == 0) {
//                    Log.d("likeCnt", "いいねアクション２: ");
//                    /**色指定**/
//                    ((Button)v).setTextColor(Color.RED);
//                    likeCnt = 1;
//                } else if (likeCnt == 1) {
//                    Log.d("likeCnt", "いいねアクション３: ");
//                    /**色指定**/
//                    ((Button)v).setTextColor(Color.BLACK);
//                    likeCnt = 0;
//                }
//            }
//        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return iNames.size();
    }
}
