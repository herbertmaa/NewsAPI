package com.bcit.comp3717assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {

    public static String ACTIVITY = "RESULT_ACTIVITY";

    private Article result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getArticle();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
    }


    private void getArticle(){
        if (getIntent().getExtras().getSerializable("article") != null){
            result = (Article) getIntent().getExtras().getSerializable("article");
        }
        Log.i(ACTIVITY, result.getTitle());
        Log.i(ACTIVITY, result.getDescription());
        Log.i(ACTIVITY, result.getAuthor());
        Log.i(ACTIVITY, result.getContent());
        Log.i(ACTIVITY, result.getUrl());
        Log.i(ACTIVITY, result.getUrlToImage());
    }
}