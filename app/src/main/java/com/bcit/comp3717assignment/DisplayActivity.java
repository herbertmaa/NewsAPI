package com.bcit.comp3717assignment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.xml.transform.Result;

public class DisplayActivity extends AppCompatActivity implements DisplayItemAdapter.RecyclerViewClickInterface {

    private Context context;
    private String topic;
    private ArrayList<Article> articles;
    private ArrayList<String> articleTitles;
    private RecyclerView recyclerView;
    private DisplayItemAdapter displayItemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        context = this;

        if (getIntent().getExtras().getSerializable("articles") != null)
        {
            articles = (ArrayList<Article>) getIntent().getExtras().getSerializable("articles");
            topic = (String) getIntent().getExtras().getString("topic");
        }

        TextView topicTextView = findViewById(R.id.topicTextView);
        topicTextView.setText("Topic: " + topic.toUpperCase());

        TextView totalResultsView = findViewById(R.id.totalResultsView);
        totalResultsView.setText("Total results: " + articles.size() + " articles");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        displayItemAdapter = new DisplayItemAdapter(this, getDisplayItems(articles), this);
        recyclerView.setAdapter(displayItemAdapter);
    }

    private ArrayList<DisplayItem> getDisplayItems(ArrayList<Article> articles) {

        ArrayList<DisplayItem> displayItems = new ArrayList<>();

        for (Article a: articles) {
            DisplayItem displayItem = new DisplayItem(a.getTitle(), a.getDescription(), a.getUrlToImage());
            displayItems.add(displayItem);
        }

        return displayItems;
    }

    private void showArticleTitles(ListView articleListView) {
        articleTitles = new ArrayList<>();
        if (getIntent().getExtras().getSerializable("articles") != null)
            articles = (ArrayList<Article>) getIntent().getExtras().getSerializable("articles");

        if (articles != null)
            for (Article article : articles)
                articleTitles.add(article.getTitle());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, articleTitles);
        articleListView.setAdapter(adapter);
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtra("article", articles.get(position));
        startActivity(intent);
    }
}