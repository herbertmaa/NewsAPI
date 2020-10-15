package com.bcit.comp3717assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

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

    class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {

        private final WeakReference<ImageView> imageViewWeakReference;

        public ImageDownloaderTask(ImageView imageview) {
            imageViewWeakReference = new WeakReference<>(imageview);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadBitmap(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewWeakReference != null) {
                ImageView imageView = imageViewWeakReference.get();
                if (imageView != null) {
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    } else {
                        Drawable placeholder = imageView.getContext().getResources().getDrawable(R.drawable.placeholder);
                        imageView.setImageDrawable(placeholder);
                    }
                }
            }
        }

        private Bitmap downloadBitmap(String url) {
            HttpURLConnection urlConnection = null;
            try {
                URL uri = new URL(url);
                urlConnection = (HttpURLConnection) uri.openConnection();
                int statusCode = urlConnection.getResponseCode();
                if (statusCode != HttpURLConnection.HTTP_OK) {
                    return null;
                }

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                }
            } catch (Exception e) {
                urlConnection.disconnect();
                Log.e("ImageDownloader", e.toString());
                Log.w("ImageDownloader", "Error downloading image from " + url);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;

        }

    }

}