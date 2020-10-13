package com.bcit.comp3717assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import com.google.gson.Gson;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public static String NEWS_API_URL = "http://newsapi.org/v2/everything?";
    public static String ACTIVITY = "MAIN_ACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchView searchBar = findViewById(R.id.searchView);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e(ACTIVITY, query);
                String params[] = new String[]{NEWS_API_URL, query, BuildConfig.NEWS_API_KEY};
                new SearchNews().execute(params);

//                Intent intent = new Intent(this, DisplayActivity.class);
//                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    class SearchNews extends AsyncTask<String, Void, String> {

        protected void onPostExecute(String result) {
            Gson gson = new Gson();
            News news = gson.fromJson(result, News.class);

            Log.i("[onPostExecute]Response: ", result);
            Log.i("[onPostExecute]Response status:", news.getResponseStatus());
            Log.i("[onPostExecute]Total Results: ", "" + news.getTotalResults());

            Context mainActivityContext = MainActivity.this;
            Intent intent = new Intent(mainActivityContext, DisplayActivity.class);
            intent.putExtra("articles", news.getArticles());
            startActivity(intent);
        }

        @Override
        protected String doInBackground(String... strings) {

            String urlString = strings[0];
            String query = "q=" + strings[1];
            String api_key = "&apiKey=" + strings[2];

            String requestURL = urlString + query + api_key;

            try {
                URL url = new URL(requestURL);
                InputStream in = url.openStream();
                InputStreamReader reader = new InputStreamReader(in);

                BufferedReader streamReader = new BufferedReader(reader);
                StringBuilder responseStrBuilder = new StringBuilder();

                String inputStr;
                while((inputStr = streamReader.readLine()) != null){
                    responseStrBuilder.append(inputStr);
                }

                JSONObject response = new JSONObject(responseStrBuilder.toString());

                Log.e("TESTING", response.toString());

                in.close();
                reader.close();
                streamReader.close();
                return(response.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return("UNABLE TO PROCESS REQUEST");
        }
    }
}