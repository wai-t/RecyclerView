package com.example.android.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.common.logger.Log;

import static com.example.android.recyclerview.MainActivity.TAG;

public class MyViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;

    public MyViewHolder(View v) {
        super(v);

        // Define click listener for the ViewHolder's View.
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
            }
        });
        textView = (TextView) v.findViewById(R.id.textView);

    }
    public void bindTo(MyItem item){
        textView.setText(item.getText());
    }
}
