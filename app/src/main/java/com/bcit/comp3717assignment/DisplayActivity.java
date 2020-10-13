package com.bcit.comp3717assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.xml.transform.Result;

public class DisplayActivity extends AppCompatActivity {

    private Context context;
    private ArrayList<Article> articles;
    private ArrayList<String> articleTitles;
    private ListView articleListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        context = this;

        articleListView = (ListView)findViewById(R.id.articleListView);
        showArticleTitles(articleListView);

        articleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(context, ResultActivity.class);
                intent.putExtra("index", "Hiiii");
                startActivity(intent);
            }
        });
    }



    private void showArticleTitles(ListView articleListView)
    {
        articleTitles = new ArrayList<>();
        if (getIntent().getExtras().getSerializable("articles") != null)
            articles = (ArrayList<Article>)getIntent().getExtras().getSerializable("articles");

        if (articles != null)
            for (Article article: articles)
                articleTitles.add(article.getTitle());


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, articleTitles);
        articleListView.setAdapter(adapter);
    }

}