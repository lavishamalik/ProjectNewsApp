package com.codingblocks.newsappforpitching;

import android.accounts.NetworkErrorException;
import android.appwidget.AppWidgetManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.codingblocks.newsappforpitching.Constants.API_KEY;
import static com.codingblocks.newsappforpitching.Constants.API_KEY_PARAM;
import static com.codingblocks.newsappforpitching.Constants.QUERY_PARAM;

public class AppWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
      //  Log.e("TAG","remoteviewfactory");
        return new AppWidgetItemFactory(getApplicationContext(),intent);
    }

    class AppWidgetItemFactory implements RemoteViewsFactory{

        String url="https://content.guardianapis.com/sections?&api-key=4614dcce-2192-4add-97df-2a121c0e3c0b";
        private Context context;
        private int appWidgetId;

        ArrayList<News>arrayList=new ArrayList<>();
            AppWidgetItemFactory(Context context, Intent intent) {
            this.context = context;
            this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,

                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        @Override
        public void onCreate() {
               Log.e("TAG","in OnCreate");
               initData();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void initData() {
               Log.e("TAG","in initdata");
                makeNetworkCall(url);
        }

        private void makeNetworkCall(String url) {
                OkHttpClient client=new OkHttpClient();
            Request request=new Request.Builder()
                    .url(url)
                    .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG","in failure");
                call.enqueue(this);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               String res = response.body().string();
               arrayList=JSONParse(res);
               Log.e("TAG", String.valueOf(arrayList.size()));
            }
        });
        }

        private ArrayList<News> JSONParse(String newsJSON) {

            if (TextUtils.isEmpty(newsJSON)) {
                return null;
            }

            ArrayList<News> newsList = new ArrayList<>();


            try {

                JSONObject baseJsonResponse = new JSONObject(newsJSON);


                JSONObject responseJsonObject = baseJsonResponse.getJSONObject(Constants.JSON_KEY_RESPONSE);

                JSONArray resultsArray = responseJsonObject.getJSONArray(Constants.JSON_KEY_RESULTS);


                for (int i = 0; i < resultsArray.length(); i++) {

                    JSONObject currentNews = resultsArray.getJSONObject(i);
                    String webTitle = currentNews.getString(Constants.JSON_KEY_WEB_TITLE);
                //   String sectionName = currentNews.getString(Constants.JSON_KEY_SECTION_NAME);
            //                    String webPublicationDate = currentNews.getString(Constants.JSON_KEY_WEB_PUBLICATION_DATE);
              //      String webUrl = currentNews.getString(Constants.JSON_KEY_WEB_URL);
                   /* String author = null;
                    if (currentNews.has(Constants.JSON_KEY_TAGS)) {
                        JSONArray tagsArray = currentNews.getJSONArray(Constants.JSON_KEY_TAGS);
                        if (tagsArray.length() != 0) {

                            JSONObject firstTagsItem = tagsArray.getJSONObject(0);
                            author = firstTagsItem.getString(Constants.JSON_KEY_WEB_TITLE);
                        }
                    }*/

                    String thumbnail = null;
              //      String trailText = null;
                    if (currentNews.has(Constants.JSON_KEY_FIELDS)) {

                        JSONObject fieldsObject = currentNews.getJSONObject(Constants.JSON_KEY_FIELDS);
                        if (fieldsObject.has(Constants.JSON_KEY_THUMBNAIL)) {
                            thumbnail = fieldsObject.getString(Constants.JSON_KEY_THUMBNAIL);
                        }
                       /* if (fieldsObject.has(Constants.JSON_KEY_TRAIL_TEXT)) {
                            trailText = fieldsObject.getString(Constants.JSON_KEY_TRAIL_TEXT);
                        }*/
                    }
                    News news = new News(webTitle/*, sectionName*/, thumbnail);
                    Log.e("TAG","News fetched and added");
                    newsList.add(news);
                }
            } catch (JSONException e) {

                Log.e("LOG_TAG", "Problem parsing the news JSON results", e);
            }

            return newsList;
        }
        @Override
        public void onDataSetChanged() {
                initData();
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
                initData();
             //   Log.e("TAG","hello");
               News news=arrayList.get(i);
                Log.e("TAG","remote views");
            RemoteViews views=new RemoteViews(context.getPackageName(),R.layout.app_widget_row);
            views.setTextViewText(R.id.tvAppWidget,arrayList.get(i).getTitle());
            Log.e("TAG","text added");
           // views.setImageViewUri(R.id.imgAppWidget, Uri.parse(news.getThumbnail()));
         //   views.setInt(R.id.imgAppWidget, String.valueOf(Uri.parse(news.getThumbnail())),R.color.colorPrimary);
            //remoteView.setInt(R.id.viewid, "setBackgroundResource", R.color.your_color)
            Uri uriimg=Uri.parse(news.getmThumbnail());
            Log.e("TAG", String.valueOf(uriimg));
            Bitmap bitmap= null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(AppWidgetService.this.getContentResolver(),uriimg);
            } catch (IOException e) {
                Log.e("TAG","inside catch block");
                e.printStackTrace();
            }
            views.setImageViewBitmap(R.id.imgAppWidget,bitmap);
            Log.e("TAG","thumbnail");
            Bundle extras = new Bundle();
            extras.putInt(NewsAppWidget.EXTRA_ITEM, i);
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);
            views.setOnClickFillInIntent(R.id.tvAppWidget, fillInIntent);

          // Log.e("TAG",news.getTitle());
         //   Log.e("TAG",news.getmUrl());
            try {
                System.out.println("Loading view " + i);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Return the remote views object.
        return views;
         /*   RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget_row);
            views.setTextViewText(R.id.tvAppWidget, arrayList.get(i).getTitle());
            SystemClock.sleep(500);
            return views;*/
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
