package com.example.to_dolist.ui.todo.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_dolist.R;

public class TodoListHeaderAdapter extends RecyclerView.Adapter<TodoListHeaderAdapter.ViewHolder> {

    private final String text;

    public TodoListHeaderAdapter(String text) {
        this.text = text;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView headerText;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.headerText = itemView.findViewById(R.id.text_todo_header);
        }

        void bind(String text) {
            headerText.setText(text);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View item = inflater.inflate(R.layout.todo_header, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoListHeaderAdapter.ViewHolder holder, int position) {
        holder.bind(text);
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
