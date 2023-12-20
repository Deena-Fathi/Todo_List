package com.example.to_dolist.ui.todo.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_dolist.R;

/**
 * Adapter for displaying a header in a RecyclerView for a list of to-do items. <br>
 *
 * - text      The text to be displayed in the header. <br>
 * - ViewHolder ViewHolder for displaying the header text. <br>
 * - onCreateViewHolder Creates new ViewHolders for the RecyclerView. <br>
 * - onBindViewHolder Binds data to ViewHolders for displaying in the RecyclerView. <br>
 * - getItemCount Returns the total number of items in the adapter (always 1 for a header).
 */
public class TodoListHeaderAdapter extends RecyclerView.Adapter<TodoListHeaderAdapter.ViewHolder> {

    private final String text;

    /**
     * Constructs a TodoListHeaderAdapter with the specified text for the header.
     *
     * @param text The text to be displayed in the header.
     */
    public TodoListHeaderAdapter(String text) {
        this.text = text;
    }

    /**
     * ViewHolder for displaying the header text in the RecyclerView. <br>
     *
     * - headerText TextView for displaying the header text. <br>
     * - bind       Binds data to the ViewHolder for display.
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView headerText;

        /**
         * Constructs a ViewHolder.
         *
         * @param itemView The view for displaying the header.
         */
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.headerText = itemView.findViewById(R.id.text_todo_header);
        }

        /**
         * Binds data to the ViewHolder for display.
         *
         * @param text The text to be displayed in the header.
         */
        void bind(String text) {
            headerText.setText(text);
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
        final View item = inflater.inflate(R.layout.todo_header, parent, false);
        return new ViewHolder(item);
    }

    /**
     * Binds data to ViewHolders for displaying in the RecyclerView.
     *
     * @param holder   The ViewHolder to bind the data to.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull TodoListHeaderAdapter.ViewHolder holder, int position) {
        holder.bind(text);
    }

    /**
     * Returns the total number of items in the adapter (always 1 for a header).
     *
     * @return The total number of items in the adapter.
     */
    @Override
    public int getItemCount() {
        return 1;
    }
}
