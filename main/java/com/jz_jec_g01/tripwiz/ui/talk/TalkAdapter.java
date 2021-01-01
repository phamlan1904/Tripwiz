package com.jz_jec_g01.tripwiz.ui.talk;

//AndroidX

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jz_jec_g01.tripwiz.chats.ChatActivity;
import com.jz_jec_g01.tripwiz.R;

import java.util.List;

//import android.support.v7.widget.RecyclerView;

public class TalkAdapter extends RecyclerView.Adapter<TalkAdapter.ViewHolder> {

    private List<Integer> iImages;
    private List<String> iNames;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        ImageView imageView;
        TextView text_name_View;
        TextView talkView;

        ViewHolder(View v) {
            super(v);
            imageView = v.findViewById(R.id.talk_image_view);
            text_name_View = v.findViewById(R.id.talk_name_view);
            talkView = v.findViewById(R.id.talk_view);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(v.getContext(), ChatActivity.class);
                    v.getContext().startActivity(intent);
                }
            });

        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    TalkAdapter(List<Integer> itemImages, List<String> itemNames) {
        this.iImages = itemImages;
        this.iNames = itemNames;
    }

    // Create new views (invoked by the layout manager)
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_talk, parent, false);

        // set the view's size, margins, paddings and layout parameters

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.imageView.setImageResource(iImages.get(position));
        holder.text_name_View.setText(iNames.get(position));
        holder.talkView.setText("メッセージを送ってみましょう");
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return iNames.size();
    }
}
