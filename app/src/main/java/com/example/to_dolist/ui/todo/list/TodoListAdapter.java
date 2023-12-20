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

/**
 * Adapter for displaying a list of to-do items in a RecyclerView. <br>
 *
 * - onCheckedChange   BiConsumer to handle changes in the checkbox state. <br>
 * - ViewHolder        ViewHolder for displaying individual to-do items. <br>
 * - onCreateViewHolder Creates new ViewHolders for the RecyclerView. <br>
 * - onBindViewHolder   Binds data to ViewHolders for displaying in the RecyclerView. <br>
 * - TodoDiff          DiffUtil.ItemCallback for efficient RecyclerView updates.
 */
class TodoListAdapter extends ListAdapter<Todo, TodoListAdapter.ViewHolder> {

    @NonNull
    private final BiConsumer<Todo, Boolean> onCheckedChange;

    /**
     * Constructs a TodoListAdapter.
     *
     * @param onCheckedChange BiConsumer to handle changes in the checkbox state.
     */
    TodoListAdapter(@NonNull BiConsumer<Todo, Boolean> onCheckedChange) {
        super(new TodoDiff());
        this.onCheckedChange = onCheckedChange;
    }

    /**
     * ViewHolder for displaying individual to-do items in the RecyclerView. <br>
     *
     * - dateTimeFormat   Date and time format for formatting to-do dates. <br>
     * - todoCheckbox     CheckBox for marking to-dos as done. <br>
     * - todoDateText     TextView for displaying to-do dates. <br>
     * - onCheckedChange  BiConsumer to handle changes in the checkbox state. <br>
     * - bind             Binds data to the ViewHolder for display.
     */
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

        /**
         * Constructs a ViewHolder.
         *
         * @param itemView        The view for displaying the individual to-do item.
         * @param onCheckedChange BiConsumer to handle changes in the checkbox state.
         */
        ViewHolder(@NonNull View itemView, @NonNull BiConsumer<Todo, Boolean> onCheckedChange) {
            super(itemView);
            this.todoCheckbox = itemView.findViewById(R.id.checkbox_todo);
            this.todoDateText = itemView.findViewById(R.id.text_todo_date);
            this.onCheckedChange = onCheckedChange;
        }

        /**
         * Binds data to the ViewHolder for display.
         *
         * @param todo The to-do item to bind.
         */
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
    /**
     * Creates new ViewHolders for the RecyclerView.
     *
     * @param parent   The parent view group.
     * @param viewType The type of the new view.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View item = inflater.inflate(R.layout.todo_item, parent, false);
        return new ViewHolder(item, onCheckedChange);
    }

    @Override
    /**
     * Binds data to ViewHolders for displaying in the RecyclerView.
     *
     * @param holder   The ViewHolder to bind the data to.
     * @param position The position of the item within the adapter's data set.
     */
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Todo todo = getItem(position);
        holder.bind(todo);
    }

    /**
     * DiffUtil.ItemCallback for efficient RecyclerView updates.
     */
    static class TodoDiff extends DiffUtil.ItemCallback<Todo> {

        /**
         * Checks whether the two items represent the same item.
         *
         * @param oldItem The old item.
         * @param newItem The new item.
         * @return True if the items represent the same item, false otherwise.
         */
        @Override
        public boolean areItemsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
            return oldItem.getId() == newItem.getId();
        }

        /**
         * Checks whether the contents of the two items are the same.
         *
         * @param oldItem The old item.
         * @param newItem The new item.
         * @return True if the contents of the items are the same, false otherwise.
         */
        @Override
        public boolean areContentsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
            return oldItem.getTodo().equals(newItem.getTodo()) &&
                    oldItem.getDate().equals(newItem.getDate()) &&
                    oldItem.done() == newItem.done();
        }
    }
}
