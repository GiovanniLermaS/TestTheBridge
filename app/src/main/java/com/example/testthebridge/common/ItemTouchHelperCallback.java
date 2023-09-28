package com.example.testthebridge.common;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testthebridge.ui.adapter.ListAdapter;

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private RecyclerView recyclerView;

    public ItemTouchHelperCallback(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return ItemTouchHelper.Callback.makeMovementFlags(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.ACTION_STATE_SWIPE);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder targetViewHolder) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        ListAdapter adapter = (ListAdapter) recyclerView.getAdapter();
        adapter.removeItem(viewHolder.getAdapterPosition());
    }
}
