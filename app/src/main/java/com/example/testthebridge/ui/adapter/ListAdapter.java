package com.example.testthebridge.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testthebridge.data.Item;
import com.example.testthebridge.databinding.ItemAdapterBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ItemViewHolder> implements Serializable {

    private List<Item> items = new ArrayList<>();

    @Inject
    public ListAdapter() {
    }

    public void updateItems(List<Item> items) {
        this.items = items;
        notifyItemRangeInserted(0, items.size());
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemAdapterBinding binding = ItemAdapterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item item = items.get(position);
        holder.view.tvText.setText(item.getTitle());
        Glide.with(holder.view.ivImage.getContext())
                .load(item.getUrl())
                .centerCrop()
                .into(holder.view.ivImage);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void removeItem(int adapterPosition) {
        items.remove(adapterPosition);
        notifyDataSetChanged();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private ItemAdapterBinding view;

        public ItemViewHolder(ItemAdapterBinding view) {
            super(view.getRoot());
            this.view = view;
        }
    }
}


