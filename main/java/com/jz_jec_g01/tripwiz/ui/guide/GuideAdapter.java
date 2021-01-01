package com.jz_jec_g01.tripwiz.ui.guide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jz_jec_g01.tripwiz.R;
import com.jz_jec_g01.tripwiz.chats.ChatActivity;
import com.jz_jec_g01.tripwiz.GuidePageActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.ViewHolder> {
    private List<String> iImages;
    private List<String> iNames;
    private List<Integer> iRating;
    //    final String url = "http://10.210.20.161";
    final String url = "http://www.jz.jec.ac.jp/17jzg01";
    final Request request = new Request.Builder().url(url).build();
    final OkHttpClient client = new OkHttpClient.Builder().build();
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        private ImageView imageView;
        private TextView textView;
        private RatingBar ratingBar;
        private Button btnRequest;
        ViewHolder(View v) {
            super(v);
            imageView = v.findViewById(R.id.user_image_view);
            textView = v.findViewById(R.id.user_name_view);
            ratingBar = v.findViewById(R.id.ratingBar);

            btnRequest = v.findViewById(R.id.btnRequest);
            btnRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(v.getContext(), ChatActivity.class);
                    v.getContext().startActivity(intent);
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(v.getContext(), GuidePageActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    GuideAdapter(List<String> itemImages, List<String> itemNames, List<Integer> itemRating) {
        this.iImages = itemImages;
        this.iNames = itemNames;
        this.iRating = itemRating;
    }

    // Create new views (invoked by the layout manager)
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_guide, parent, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        client.newCall(request).enqueue(new Callback() {
            final Handler mHandler = new Handler(Looper.getMainLooper());

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("接続","失敗");
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        String imgUrl = url + "/image/userImage/" + iImages.get(position);
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
                                                holder.imageView.setImageBitmap(bitmap);
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
                });
            }
        });
        holder.textView.setText(iNames.get(position));

        holder.ratingBar.setRating(iRating.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return iNames.size();
    }
}
