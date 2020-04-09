package com.example.chapter3.homework;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.content.Context;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.NumberViewHolder> {

    private static final String TAG = "MyAdapter";
    private int mNumberItems;
    private final ListItemClickListener mOnClickListener;
    private static int viewHolderCount;

    MyAdapter(int numListItems, @Nullable ListItemClickListener listener) {
        mNumberItems = numListItems;
        mOnClickListener = listener;
        viewHolderCount = 0;
    }

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.number_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        NumberViewHolder viewHolder = new NumberViewHolder(view);
        viewHolder.viewHolderIndex = viewHolderCount;
        viewHolderCount++;

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder numberViewHolder, int position) {
        numberViewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    public class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView listItemNumberView;
        private int viewHolderIndex;

        public NumberViewHolder(@NonNull View itemView) {
            super(itemView);
            listItemNumberView = itemView.findViewById(R.id.tv_item_number);
            viewHolderIndex = -1;
            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            String str = "好友" + String.valueOf(position) + "号";
            listItemNumberView.setText(str);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            if (mOnClickListener != null) {
                mOnClickListener.onListItemClick(clickedPosition);
            }
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

}
