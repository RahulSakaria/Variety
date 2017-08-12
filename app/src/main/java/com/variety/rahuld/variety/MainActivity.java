package com.variety.rahuld.variety;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()

                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config); // Do it on Application start

        new JSONTask().execute("https://public-api.wordpress.com/rest/v1.2/sites/43439658/posts/");
    }

    public class JSONTask extends AsyncTask<String,String,List<CardItem>> {

        @Override
        protected List<CardItem> doInBackground(String... params) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;

            try{
                URL url = new URL(params[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer buffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine())!=null){
                    buffer.append(line);
                }
                String JSONFile = buffer.toString();
                JSONObject parentObject = new JSONObject(JSONFile);
                JSONArray parentArray = parentObject.getJSONArray("posts");
                List<CardItem> itemList = new ArrayList<>();
                for(int i=0;i<parentArray.length();i++){
                    JSONObject childObject = parentArray.getJSONObject(i);
                    CardItem cardItem = new CardItem();
                    cardItem.setImage(childObject.getString("featured_image"));
                    cardItem.setTitle(childObject.getString("title"));
                    cardItem.setDate(childObject.getString("date"));
                    itemList.add(cardItem);
                }
                return itemList;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                try {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<CardItem> cardItems) {
            super.onPostExecute(cardItems);
            CardAdapter adapter = new CardAdapter(cardItems,MainActivity.this);
            recyclerView.setAdapter(adapter);
        }
}}
