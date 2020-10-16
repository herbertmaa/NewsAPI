package com.bcit.MA_KWON;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class ResultActivity extends AppCompatActivity {

    public static String ACTIVITY = "RESULT_ACTIVITY";

    private Article result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getArticle();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //Initialize view objects
        try {
            initializeViewContents();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void getArticle() {
        if (getIntent().getExtras().getSerializable("article") != null) {
            result = (Article) getIntent().getExtras().getSerializable("article");
        }
    }

    private void initializeViewContents() throws IOException {
        TextView resultTitle = findViewById(R.id.resultTitle);
        ImageView resultImage = findViewById(R.id.resultImage);
        TextView resultAuthor = findViewById(R.id.resultAuthor);
        TextView resultContent = findViewById(R.id.resultContent);
        TextView resultPublishedDate = findViewById(R.id.resultPublishedDate);
        TextView resultURL = findViewById(R.id.resultURL);
        TextView resultSource = findViewById(R.id.resultSource);

        resultTitle.setText(result.getTitle());
        resultAuthor.setText(result.getAuthor());
        resultContent.setText(result.getContent());
        resultPublishedDate.setText(result.getPublishedAt());
        resultURL.setText(result.getUrl());
        resultSource.setText(result.getSource().getName());


        if (result.getUrlToImage() != null && !result.getUrlToImage().isEmpty()) {
            ImageDownloaderTask imageTask = new ImageDownloaderTask(resultImage);
            imageTask.execute(result.getUrlToImage());
        }
    }
}