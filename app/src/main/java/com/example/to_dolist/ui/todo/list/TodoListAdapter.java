package com.example.to_dolist.ui.todo.list;

import android.graphics.Paint;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
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

import java.util.function.BiConsumer;

class TodoListAdapter extends ListAdapter<Todo, TodoListAdapter.ViewHolder> {

    @NonNull
    private final BiConsumer<Todo, Boolean> onCheckedChange;

    TodoListAdapter(@NonNull BiConsumer<Todo, Boolean> onCheckedChange) {
        super(new TodoDiff());
        this.onCheckedChange = onCheckedChange;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final DateFormat dateTimeFormat = SimpleDateFormat.getDateTimeInstance(
                DateFormat.RELATIVE_SHORT,
                DateFormat.RELATIVE_SHORT
        );

        @NonNull
        private final CheckBox todoCheckbox;

        @NonNull
        private final TextView todoDateText;

        @NonNull
        private final BiConsumer<Todo, Boolean> onCheckedChange;

        ViewHolder(@NonNull View itemView, @NonNull BiConsumer<Todo, Boolean> onCheckedChange) {
            super(itemView);
            this.todoCheckbox = itemView.findViewById(R.id.checkbox_todo);
            this.todoDateText = itemView.findViewById(R.id.text_todo_date);
            this.onCheckedChange = onCheckedChange;
        }

        void bind(@NonNull Todo todo) {
            todoCheckbox.setText(todo.getTodo());
            todoCheckbox.setChecked(todo.done());
            todoCheckbox.setOnCheckedChangeListener(
                    (v, checked) -> onCheckedChange.accept(todo, checked)
            );
            todoDateText.setText(dateTimeFormat.format(todo.getDate()));

            // Show a strike-through on the text of the to-do when it is done.
            if (todo.done()) {
                todoCheckbox.setPaintFlags(todoCheckbox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                todoCheckbox.setPaintFlags(todoCheckbox.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View item = inflater.inflate(R.layout.todo_item, parent, false);
        return new ViewHolder(item, onCheckedChange);
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
}
