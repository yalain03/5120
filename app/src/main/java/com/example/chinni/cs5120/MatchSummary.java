package com.example.chinni.cs5120;

//import android.app.ActionBar;
import android.support.v7.app.ActionBar;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MatchSummary extends AppCompatActivity {

    Details details;
    Summary summary;

    private String TAG = MatchSummary.class.getSimpleName();
    String url = "http://mapps.cricbuzz.com/cbzios/match/";
    String summary_url;
    private ProgressDialog pDialog;
    HashMap<String, String> summary_hp = new HashMap<String, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        details = new Details();
        summary = new Summary();

        // showing fragment1 in FRIENDS tab by default
        getSupportFragmentManager().beginTransaction().add(R.id.container,details).commit();

        TabLayout tabs = (TabLayout)findViewById(R.id.tabs);
        // Adding two Tabs FRIENDS and SETTINGS
        tabs.addTab(tabs.newTab().setText("SCORE CARD"));
        tabs.addTab(tabs.newTab().setText("SUMMARY"));

        // Function to display fragments when respective tab is selected
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if(position==0){
                    selected = details;
                }
                else if(position==1) {
                    selected = summary;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        Intent i = getIntent();
        String unique_id = i.getStringExtra("unique_id");
        summary_url = url+"&unique_id="+unique_id;

        //new GetContacts().execute();
        String msg = summary_hp.get("score");

        TextView tv = (TextView)findViewById(R.id.summaryText);
       // tv.setText(msg);
    }

    // Setting Action bar menu layout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    // Adding functions to action bar menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();
        switch (curId) {
            case R.id.menu_home:
                Intent intent1 = new Intent(this,CricketMenu.class);
                startActivity(intent1);
                break;
            case R.id.matches_item:
                Intent intent2 = new Intent(this,MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.stats_item:
                Intent intent3 = new Intent(this,PlayersList.class);
                startActivity(intent3);
                break;
            case R.id.news_item:
                Intent intent4 = new Intent(this,News.class);
                startActivity(intent4);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
