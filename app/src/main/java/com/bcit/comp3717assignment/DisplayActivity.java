package com.bcit.comp3717assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

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
        topicTextView.setText("Results: " + topic.toUpperCase());

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

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtra("article", articles.get(position));
        startActivity(intent);
    }
}