package com.bcit.comp3717assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.xml.transform.Result;

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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    class SearchNews extends AsyncTask<String, Void, String> {

        protected void onPostExecute(String result){
            Context context = MainActivity.this;
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
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