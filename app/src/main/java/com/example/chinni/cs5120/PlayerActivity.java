package com.example.chinni.cs5120;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PlayerActivity extends AppCompatActivity {
    final String API_KEY = "9Od3UhVkccQo2wUKs7fWSkgXHe93"; // key for my id
    final String URL = "http://cricapi.com/api/playerStats"; // url for data
    ListView listBowling; // list view for all statistics info
    ImageView imageView; // view for player image
    TextView textBowling; // view for bowling ability
    TextView textBatting; // view for batting ability
    TextView textName; // view for player name
    TextView textRole; // view for player role

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // getting player's id
        Intent intent = getIntent();
        String pid = (String) intent.getSerializableExtra("id");

        // creating request params to identify user
        // and the player
        RequestParams params = new RequestParams();
        params.put("apikey", API_KEY);
        params.put("pid", pid);

        // fetching the data from internet
        getRequestedData(params);
    }

    public void getRequestedData(RequestParams params) {
        // creating asynchronous client object
        AsyncHttpClient client = new AsyncHttpClient();
        // establishing connection to data source
        client.get(URL, params, new JsonHttpResponseHandler() {
            // in case the request is successful
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // adapter for the list view that displays statistics information
                StatAdapter adapter = new StatAdapter();
                // retrieving all views from the layout file
                listBowling = (ListView) findViewById(R.id.list_bowling);
                imageView = (ImageView) findViewById(R.id.imageView);
                textBowling = (TextView) findViewById(R.id.text_bowling);
                textBatting = (TextView) findViewById(R.id.text_batting);
                textName = (TextView) findViewById(R.id.textView);
                textRole = (TextView) findViewById(R.id.textView2);

                try {
                    // getting desired information from the response object
                    String imageUrl = response.getString("imageURL");
                    Picasso.with(PlayerActivity.this).load(imageUrl).into(imageView);
                    String bowlingStyle = response.getString("bowlingStyle");
                    String battingStyle = response.getString("battingStyle");
                    String name = response.getString("name");
                    String role = response.getString("playingRole");

                    // displaying desired information after fetch success
                    textBowling.setText(bowlingStyle);
                    textBatting.setText(battingStyle);
                    textName.setText(name);
                    textRole.setText(role);

                    JSONObject data = response.getJSONObject("data");
                    JSONObject bowling = data.getJSONObject("bowling");
                    JSONObject lista = bowling.getJSONObject("listA");

                    // setting all values for individual statistics data
                    String ten = lista.getString("10");
                    if(!(ten.isEmpty() || ten == null))
                        adapter.addItem(new StatItem("ten", ten));
                    String w5 = lista.getString("5w");
                    if(!(w5.isEmpty() || w5 == null))
                        adapter.addItem(new StatItem("5w", w5));
                    String w4 = lista.getString("4w");
                    if(!(w4.isEmpty() || w4 == null))
                        adapter.addItem(new StatItem("4w",w4));
                    String sr = lista.getString("SR");
                    if(!(sr.isEmpty() || sr == null))
                        adapter.addItem(new StatItem("SR", sr));
                    String econ = lista.getString("Econ");
                    if(!(econ.isEmpty() || econ == null))
                        adapter.addItem(new StatItem("Econ", econ));
                    String ave = lista.getString("Ave");
                    if(!(ave.isEmpty() || ave == null))
                        adapter.addItem(new StatItem("Ave", ave));
                    String wkts = lista.getString("Wkts");
                    if(!(wkts.isEmpty() || wkts == null))
                        adapter.addItem(new StatItem("Wkts", wkts));
                    String runs = lista.getString("Runs");
                    if(!(runs.isEmpty() || runs == null))
                        adapter.addItem(new StatItem("Runs", runs));

                    // displaying all statistics information
                    listBowling.setAdapter(adapter);
                } catch(JSONException e) {
                    e.printStackTrace();
                    return;
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                Toast.makeText(PlayerActivity.this, "Failure", Toast.LENGTH_LONG).show();
            }
        });
    }

    class StatAdapter extends BaseAdapter {
        // list of statistics items
        ArrayList<StatItem> items = new ArrayList<>();

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            StatItemView view = new StatItemView(getApplicationContext());

            StatItem item = items.get(position);
            view.setValue(item.getKey() + ": ");
            // getting stat item value
            double value = Double.parseDouble(item.getValue());
            // converting value to int
            int intValue = (int) value;
            // setting the progress bar value
            view.setProgressValue(intValue);
            return view;
        }

        public void addItem(StatItem item) {
            items.add(item);
        }
    }
}
