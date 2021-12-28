package com.example.myfirbaseauthenticationproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter {
    ArrayList<ChatMessage> data;

    public MyAdapter(ArrayList<ChatMessage> data) {
        super();
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_view,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage currentMessage = data.get(position);
        ViewHolder currentholder = ((ViewHolder)holder);

        currentholder.tvMessage.setText(currentMessage.getText());
        currentholder.tvName.setText(currentMessage.getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    private class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvMessage = itemView.findViewById(R.id.tvMessage);
        }
    }
}
