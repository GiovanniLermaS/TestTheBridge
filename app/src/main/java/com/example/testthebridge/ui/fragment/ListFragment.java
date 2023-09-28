package com.example.testthebridge.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testthebridge.common.NetworkResult;
import com.example.testthebridge.data.Item;
import com.example.testthebridge.databinding.FragmentListBinding;
import com.example.testthebridge.ui.MainViewModel;
import com.example.testthebridge.ui.adapter.ListAdapter;

import java.util.List;

public class ListFragment extends Fragment {

    private static final String VIEW_MODEL = "view_model";
    private static final String LIST_ADAPTER = "list_adapter";

    private MainViewModel viewModel;

    private ListAdapter listAdapter;

    private FragmentListBinding binding;

    private ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            listAdapter.removeItem(position);
        }
    };

    public static ListFragment newInstance(MainViewModel viewModel, ListAdapter listAdapter) {
        ListFragment listFragment = new ListFragment();
        Bundle args = new Bundle();
        args.putSerializable(VIEW_MODEL, viewModel);
        args.putSerializable(LIST_ADAPTER, listAdapter);
        listFragment.setArguments(args);
        return listFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            viewModel = (MainViewModel) getArguments().getSerializable(VIEW_MODEL);
            listAdapter = (ListAdapter) getArguments().getSerializable(LIST_ADAPTER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(getLayoutInflater());
        binding.toolbarIcon.setOnClickListener(view -> viewModel.fetchList());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(binding.rv);
        getList();
        return binding.getRoot();
    }

    private void getList() {
        viewModel.getListResponse().observe(getViewLifecycleOwner(), networkResult -> {
            if (networkResult instanceof NetworkResult.Loading) {
                boolean isLoading = ((NetworkResult.Loading<?>) networkResult).isLoading;
                binding.progressbar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            } else if (networkResult instanceof NetworkResult.Failure) {
                String errorMessage = ((NetworkResult.Failure<?>) networkResult).errorMessage;
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                binding.progressbar.setVisibility(View.GONE);
            } else if (networkResult instanceof NetworkResult.Success) {
                List<Item> data = ((NetworkResult.Success<List<Item>>) networkResult).data;
                listAdapter.updateItems(data);
                binding.rv.setAdapter(listAdapter);
                binding.progressbar.setVisibility(View.GONE);
            }
        });
    }
}