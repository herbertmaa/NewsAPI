package com.bcit.comp3717assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DisplayItemAdapter extends RecyclerView.Adapter<DisplayItemAdapter.DisplayItemHolder> {

    private Context context;
    ArrayList<DisplayItem> displayItems;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public DisplayItemAdapter(Context context, ArrayList<DisplayItem> displayItems, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;
        this.displayItems = displayItems;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public DisplayItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_item_card_view, null);

        return new DisplayItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DisplayItemHolder holder, int position) {
        holder.titleTextView.setText(displayItems.get(position).getArticleTitle());
        holder.detailTextView.setText(displayItems.get(position).getArticleDetail());

        ImageDownloaderTask imageTask = new ImageDownloaderTask(holder.displayImageView);
        imageTask.execute(displayItems.get(position).getArticleImage());
    }

    @Override
    public int getItemCount() {
        return displayItems.size();
    }

    public class DisplayItemHolder extends RecyclerView.ViewHolder {

        ImageView displayImageView;
        TextView titleTextView, detailTextView;

        public DisplayItemHolder(@NonNull View itemView) {
            super(itemView);

            this.displayImageView = itemView.findViewById(R.id.display_image_view);
            this.titleTextView  = itemView.findViewById(R.id.display_title_text_view);
            this.detailTextView = itemView.findViewById(R.id.display_detail_text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewClickInterface.onClick(getAbsoluteAdapterPosition());
                }
            });
        }
    }

    public interface RecyclerViewClickInterface {
        void onClick(int position);
    }
}
