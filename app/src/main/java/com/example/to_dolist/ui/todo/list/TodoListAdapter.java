package com.example.to_dolist.ui.todo.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_dolist.R;
import com.example.to_dolist.database.Todo;

class TodoListAdapter extends ListAdapter<Todo, TodoListAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        private final CheckBox todoCheckbox;

        @NonNull
        private final TextView todoDateText;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.todoCheckbox = itemView.findViewById(R.id.checkbox_todo);
            this.todoDateText = itemView.findViewById(R.id.text_todo_date);
        }

        @NonNull
        static ViewHolder fromParent(@NonNull ViewGroup parent) {
            final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            final View item = inflater.inflate(R.layout.todo_item, parent, false);
            return new ViewHolder(item);
        }

        void bind(@NonNull Todo todo) {
            todoCheckbox.setText(todo.getTodo());
            todoCheckbox.setChecked(todo.done());
            // TODO: format date according to locale
            todoDateText.setText(todo.getDate().toString());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.fromParent(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Todo todo = getItem(position);
        holder.bind(todo);
    }

    static class TodoDiff extends DiffUtil.ItemCallback<Todo> {

        @Override
        public boolean areItemsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
            return oldItem.getTodo().equals(newItem.getTodo()) &&
                    oldItem.getDate().equals(newItem.getDate()) &&
                    oldItem.done() == newItem.done();
        }
    }

    TodoListAdapter() {
        super(new TodoDiff());
    }
}
