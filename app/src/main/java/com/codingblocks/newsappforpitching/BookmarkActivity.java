package com.codingblocks.newsappforpitching;
import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class BookmarkActivity extends AppCompatActivity {

    public BookmarksDatabase bookmarksDatabase;
    public BookmarkActivity(BookmarksDatabase bookmarksDatabase) {

        this.bookmarksDatabase=bookmarksDatabase;
    }

    public BookmarkActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bookmark);
       // bookmarksDatabase=Singleton.ge
        LiveData<List<BookmarkNews>> liveNotes = bookmarksDatabase.getNewsDao().getAllNews();
        RecyclerView recyclerView=findViewById(R.id.bookmarkRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        BookmarkAdapter bookmarkAdapter=new BookmarkAdapter(liveNotes);
        recyclerView.setAdapter(bookmarkAdapter);
    }
}