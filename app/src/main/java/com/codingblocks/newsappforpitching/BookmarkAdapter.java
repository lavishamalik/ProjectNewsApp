package com.codingblocks.newsappforpitching;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {
    LiveData<List<BookmarkNews>> List;
    ArrayList<BookmarkNews>arrayList= (ArrayList<BookmarkNews>) List.getValue();

    public BookmarkAdapter(LiveData<List<BookmarkNews>> arrayList) {
        this.List = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_bookmark_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       BookmarkNews news=arrayList.get(position);
       holder.title.setText(news.getNewstitle());
       holder.url.setText(news.getNewsurl());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,url;
        public ViewHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.tvTitlebookmark);
            url=itemView.findViewById(R.id.tvurlbookmark);
        }
    }
}
