package com.jz_jec_g01.tripwiz.feedbacks;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jz_jec_g01.tripwiz.feedbacks.FeedbackActivity;
import com.jz_jec_g01.tripwiz.R;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder>{


    private List<Integer> iImages;
    private List<String> iNames;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        ImageView imageView;
        TextView text_name_View;
        TextView feedbackView;

        ViewHolder(View v) {
            super(v);
            imageView = v.findViewById(R.id.feedback_image_view);
            text_name_View = v.findViewById(R.id.feedback_name_view);
            feedbackView = v.findViewById(R.id.feedback_view);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    FeedbackAdapter(List<Integer> itemImages, List<String> itemNames) {
        this.iImages = itemImages;
        this.iNames = itemNames;
    }

    // Create new views (invoked by the layout manager)
    @Override
    @NonNull
    public FeedbackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_feedback, parent, false);
        return new FeedbackAdapter.ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull FeedbackAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageResource(iImages.get(position));
        holder.text_name_View.setText(iNames.get(position));
        holder.feedbackView.setText("メッセージを送ってみましょう");
    }

    @Override
    public int getItemCount() {
        return iNames.size();
    }

}
