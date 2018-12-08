package com.codingblocks.newsappforpitching.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codingblocks.newsappforpitching.BookmarkNews;
import com.codingblocks.newsappforpitching.BookmarksDatabase;
import com.codingblocks.newsappforpitching.News;
import com.codingblocks.newsappforpitching.NoteApplication;
import com.codingblocks.newsappforpitching.R;
import com.codingblocks.newsappforpitching.WebViewClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static com.codingblocks.newsappforpitching.R.drawable.ic_bookmarkclicked;

public class NewsListViewAdapter extends BaseAdapter {
    ArrayList<News> arrayList;
    TextToSpeech textToSpeech;
    int clickcount=0;
    Context context;
  //  String current_page_url;
    Bitmap bitmap;
    public static final String PREFERENCES = "PREFERENCES_NAME";
    public static final String WEB_LINKS = "links";
    public static final String WEB_TITLE = "title";
    Bitmap bitmap2;
    //private SeekBar mSeekBarPitch;
    //private SeekBar mSeekBarSpeed;
    private SharedPreferences sharedPrefs;
   // BookmarksDatabase bookmarksDatabase;

    public NewsListViewAdapter(ArrayList<News> arrayList, Context context/*, BookmarksDatabase bookmarksDatabase*/) {
        this.arrayList = arrayList;
        this.context = context;
       // this.bookmarksDatabase=bookmarksDatabase;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") View itemView = LayoutInflater.from(context).inflate(R.layout.news_card_item, viewGroup, false);
        final TextView titleTextView;
        TextView sectionTextView;
        TextView authorTextView;
        TextView dateTextView;
        ImageView thumbnailImageView;
        ImageView shareImageView;
        TextView trailTextView;
        final ImageView readAlong;
        final CardView cardView;
        final ImageView bookmark;
        titleTextView = itemView.findViewById(R.id.title_card);
        sectionTextView = itemView.findViewById(R.id.section_card);
        authorTextView = itemView.findViewById(R.id.author_card);
        dateTextView = itemView.findViewById(R.id.date_card);
        thumbnailImageView = itemView.findViewById(R.id.thumbnail_image_card);
        shareImageView = itemView.findViewById(R.id.share_image_card);
        trailTextView = itemView.findViewById(R.id.trail_text_card);
        cardView = itemView.findViewById(R.id.card_view);
        readAlong = itemView.findViewById(R.id.readAlong);
       // bookmark=itemView.findViewById(R.id.bookmark);
       /* bitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_bookmarkclicked);
        bitmap2=BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_bookmarknotclicked);
       */ sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        setColorTheme(titleTextView);
        setTextSize(titleTextView, sectionTextView, authorTextView, dateTextView, trailTextView);
        final News currentNews = arrayList.get(i);

        titleTextView.setText(currentNews.getTitle());
        sectionTextView.setText(currentNews.getSection());
        if (currentNews.getAuthor() == null) {
            authorTextView.setVisibility(View.GONE);
        } else {
            authorTextView.setVisibility(View.VISIBLE);
            authorTextView.setText(currentNews.getAuthor());
        }
        dateTextView.setText(getTimeDifference(formatDate(currentNews.getDate())));
        final String trailTextHTML = currentNews.getTrailTextHtml();
        trailTextView.setText(Html.fromHtml(Html.fromHtml(trailTextHTML).toString()));

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = currentNews.getUrl();
                Intent intent = new Intent(context, WebViewClass.class);
                intent.putExtra("url", url);
                context.startActivity(intent);
            }
        });

        if (currentNews.getThumbnail() == null) {
            thumbnailImageView.setVisibility(View.GONE);
        } else {
            thumbnailImageView.setVisibility(View.VISIBLE);
            Glide.with(context.getApplicationContext())
                    .load(currentNews.getThumbnail())
                    .into(thumbnailImageView);
        }


        textToSpeech=new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status==TextToSpeech.SUCCESS)
                {
                    int result=textToSpeech.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                    Toast.makeText(context,"Feature not supported in your device",Toast.LENGTH_SHORT).show();
                }
            }
        });
        readAlong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickcount++;
                if(clickcount%2!=0) {
                    speak(currentNews);
                }
                else{
                    textToSpeech.stop();
                }
            }
        });
        shareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareData(currentNews);
            }
        });

        /*bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickcount++;
                if(clickcount%2!=0)
                {
                    bookmark.setImageBitmap(bitmap);
                  BookmarkNews bookmarkNews=new BookmarkNews(currentNews.getmTitle(),currentNews.getmUrl());
                    if(bookmarkNews!=null)
                    bookmarksDatabase.getNewsDao().insertNews(bookmarkNews);
                }
                else{
                    bookmark.setImageBitmap(bitmap2);
                    BookmarkNews bookmarkNews=new BookmarkNews(currentNews.getmTitle(),currentNews.getmUrl());
                    if(bookmarkNews!=null)
                    bookmarksDatabase.getNewsDao().deleteNews(bookmarkNews);
                }
            }*/
       /* });*/
        return itemView;
    }

    private void speak(News news) {
        String []arr=new String[2];
        arr[0]=news.getTitle();
        arr[1]=news.getTrailTextHtml();
        textToSpeech.speak(arr[0], TextToSpeech.QUEUE_FLUSH, null);
       textToSpeech.speak(arr[1], TextToSpeech.QUEUE_FLUSH, null);
    }


    private String formatDate(String dateStringUTC) {
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss'Z'");
        Date dateObject = null;
        try {
            dateObject = simpleDateFormat.parse(dateStringUTC);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat df = new SimpleDateFormat("MMM d, yyyy  h:mm a", Locale.ENGLISH);
        String formattedDateUTC = df.format(dateObject);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = df.parse(formattedDateUTC);
            df.setTimeZone(TimeZone.getDefault());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return df.format(date);
    }
    private void shareData(News news) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT,
                news.getTitle() + " : " + news.getUrl());
        context.startActivity(Intent.createChooser(sharingIntent,
                context.getString(R.string.share_article)));
    }

   /* private void readAlongFunc(String title,String trailTexthtml)
    {
        textToSpeech=new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status==TextToSpeech.SUCCESS)
                {
                    int result=textToSpeech.setLanguage(Locale.ENGLISH);
                if (result == TextToSpeech.LANG_MISSING_DATA
                        || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Language not supported");
                }
            } else {
                Log.e("TTS", "Initialization failed");
                    Toast.makeText(context,"Feature not supported in your device",Toast.LENGTH_SHORT).show();
            }
            }
        });
    }*/

    public void clearAll() {
        arrayList.clear();
        notifyDataSetChanged();
    }
    public void addAll(List<News> newsList) {
        arrayList.clear();
        arrayList.addAll(newsList);
        notifyDataSetChanged();
    }
    private void setTextSize(TextView titleTextView, TextView sectionTextView, TextView authorTextView, TextView dateTextView, TextView trailTextView) {
        String textSize = sharedPrefs.getString(
                context.getString(R.string.settings_text_size_key),
                context.getString(R.string.settings_text_size_default));

        if(textSize.equals(context.getString(R.string.settings_text_size_medium_value))) {
            titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    context.getResources().getDimension(R.dimen.sp22));
            sectionTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    context.getResources().getDimension(R.dimen.sp14));
            trailTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    context.getResources().getDimension(R.dimen.sp16));
            authorTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    context.getResources().getDimension(R.dimen.sp14));
            dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    context.getResources().getDimension(R.dimen.sp14));
        } else if(textSize.equals(context.getString(R.string.settings_text_size_small_value))) {
            titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    context.getResources().getDimension(R.dimen.sp20));
            sectionTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    context.getResources().getDimension(R.dimen.sp12));
            trailTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    context.getResources().getDimension(R.dimen.sp14));
            authorTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    context.getResources().getDimension(R.dimen.sp12));
            dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    context.getResources().getDimension(R.dimen.sp12));
        } else if(textSize.equals(context.getString(R.string.settings_text_size_large_value))) {
            titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    context.getResources().getDimension(R.dimen.sp24));
            sectionTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    context.getResources().getDimension(R.dimen.sp16));
            trailTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    context.getResources().getDimension(R.dimen.sp18));
            authorTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    context.getResources().getDimension(R.dimen.sp16));
            dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    context.getResources().getDimension(R.dimen.sp16));
        }
    }

    private CharSequence getTimeDifference(String formattedDate) {
        long currentTime = System.currentTimeMillis();
        long publicationTime = getDateInMillis(formattedDate);
        return DateUtils.getRelativeTimeSpanString(publicationTime, currentTime,
                DateUtils.SECOND_IN_MILLIS);
    }

    private static long getDateInMillis(String formattedDate) {
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("MMM d, yyyy  h:mm a");
        long dateInMillis;
        Date dateObject;
        try {
            dateObject = simpleDateFormat.parse(formattedDate);
            dateInMillis = dateObject.getTime();
            return dateInMillis;
        } catch (ParseException e) {
            Log.e("Problem parsing date", e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    private void setColorTheme(TextView titleTextView) {

        String colorTheme = sharedPrefs.getString(
                context.getString(R.string.settings_color_key),
                context.getString(R.string.settings_color_default));
        if (colorTheme.equals(context.getString(R.string.settings_color_white_value))) {
            titleTextView.setBackgroundResource(R.color.white);
            titleTextView.setTextColor(Color.BLACK);
        }else if (colorTheme.equals(context.getString(R.string.settings_color_sky_blue_value))) {
            titleTextView.setBackgroundResource(R.color.nav_bar_start);
            titleTextView.setTextColor(Color.WHITE);
        } else if (colorTheme.equals(context.getString(R.string.settings_color_dark_blue_value))) {
            titleTextView.setBackgroundResource(R.color.color_app_bar_text);
            titleTextView.setTextColor(Color.WHITE);
        } else if (colorTheme.equals(context.getString(R.string.settings_color_violet_value))) {
            titleTextView.setBackgroundResource(R.color.violet);
            titleTextView.setTextColor(Color.WHITE);
        } else if (colorTheme.equals(context.getString(R.string.settings_color_light_green_value))) {
            titleTextView.setBackgroundResource(R.color.light_green);
            titleTextView.setTextColor(Color.WHITE);
        } else if (colorTheme.equals(context.getString(R.string.settings_color_green_value))) {
            titleTextView.setBackgroundResource(R.color.color_section);
            titleTextView.setTextColor(Color.WHITE);
        }
    }
}
