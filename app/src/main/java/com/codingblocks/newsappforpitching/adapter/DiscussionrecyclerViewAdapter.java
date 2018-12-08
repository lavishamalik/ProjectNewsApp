package com.codingblocks.newsappforpitching.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codingblocks.newsappforpitching.Post;
import com.codingblocks.newsappforpitching.R;

import java.util.ArrayList;

public class DiscussionrecyclerViewAdapter extends RecyclerView.Adapter<DiscussionrecyclerViewAdapter.ViewHolder> {

    ArrayList<Post>arrayList;

    public DiscussionrecyclerViewAdapter(ArrayList<Post> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.discussion_recycler_view_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post=arrayList.get(position);
        holder.userName.setText(post.getUserName());
        holder.message.setText(post.getMessage());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName,message;
        public ViewHolder(View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.tvUsername);
            message=itemView.findViewById(R.id.tvDiscussionMessage);
        }
    }
}
