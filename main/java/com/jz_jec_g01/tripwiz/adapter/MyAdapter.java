//package com.jz_jec_g01.tripwiz.adapter;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
////import android.support.v7.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.jz_jec_g01.tripwiz.R;
//
//import java.util.List;
//
//public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
//
//    private List<Integer> iImages;
//    private List<String> iNames;
//
//    // Provide a reference to the views for each data item
//    // Complex data items may need more than one view per item, and
//    // you provide access to all the views for a data item in a view holder
//    static class ViewHolder extends RecyclerView.ViewHolder {
//
//        // each data item is just a string in this case
//        ImageView imageView;
//        TextView textView;
//
//        ViewHolder(View v) {
//            super(v);
//            imageView = v.findViewById(R.id.imageViewUser);
//            textView = v.findViewById(R.id.textViewUserName); }
//    }
//
//    // Provide a suitable constructor (depends on the kind of dataset)
//    public MyAdapter(List<Integer> itemImages, List<String> itemNames) {
//
//        this.iImages = itemImages;
//        this.iNames = itemNames;
//    }
//
//    // Create new views (invoked by the layout manager)
//    @Override
//    @NonNull
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        // create a new view
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.timelineitems, parent, false);
//
//         set the view's size, margins, paddings and layout parameters
//
//        return new ViewHolder(view);
//    }
//
//    // Replace the contents of a view (invoked by the layout manager)
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        // - get element from your dataset at this position
//        // - replace the contents of the view with that element
//        holder.imageView.setImageResource(iImages.get(position));
//        holder.textView.setText(iNames.get(position)); }
//
//    // Return the size of your dataset (invoked by the layout manager)
//    @Override
//    public int getItemCount() {
//        return iNames.size();
//    }
//}