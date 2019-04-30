package com.example.chinni.cs5120;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PlayersList extends AppCompatActivity {
    PlayerAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_list);

        adapter = new PlayerAdapter();
        listView = (ListView) findViewById(R.id.list_item);

        // the players
        adapter.addItem("GJ Maxwell", 325026);
        adapter.addItem("Sachin Tendulkar", 35320);
        adapter.addItem("B Kumar", 326016);
        adapter.addItem("KL Rahul", 422108);
        adapter.addItem("S Dhawan", 28235);
        adapter.addItem("RG Sharma", 34102);
        adapter.addItem("V Kohli", 253802);
        adapter.addItem("RR Pant", 931581);
        adapter.addItem("KM Jadhav", 290716);
        adapter.addItem("V Shankar", 477021);
        adapter.addItem("Kuldeep Yadav", 559235);

        // setting the adapter for the list view
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String playerId = Integer.toString(((PlayerItemView)view).getPlayerId());
                // creating an intent for the player's statistics details
                Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
                // passing the players id to the other activity for identification
                intent.putExtra("id", playerId);
                // starting the intent
                startActivity(intent);
            }
        });
    }

    class PlayerAdapter extends BaseAdapter {
        // list to store all of the players
        ArrayList<PlayerItem> items = new ArrayList<>();

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PlayerItemView view = new PlayerItemView(getApplicationContext());

            PlayerItem item = items.get(position);
            // setting the player's name in player item object
            view.setValue(item.getName());
            // setting the player's id in the player item object
            view.setPlayerId(item.getId());

            return view;
        }

        public void addItem(String value, int id) {
            items.add(new PlayerItem(value, id));
        }

        public ArrayList<PlayerItem> getItems() {
            return items;
        }
    }
}
