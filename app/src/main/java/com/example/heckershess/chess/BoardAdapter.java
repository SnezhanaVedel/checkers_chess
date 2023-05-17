package com.example.heckershess.chess;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heckershess.R;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> implements BoardAdapterInterface {
    private final int size;
    public BoardAdapter(int size) {
        this.size = size;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int row = position / size;
        int col = position % size;

        if ((row + col) % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.WHITE);
        } else {
            holder.itemView.setBackgroundColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return size * size;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }
}