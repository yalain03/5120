package com.example.chinni.cs5120;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Summary extends Fragment {

    private String TAG = MatchSummary.class.getSimpleName();
    String url = "http://mapps.cricbuzz.com/cbzios/match/livematches";
    HashMap<String, String> summary_hp = new HashMap<String, String>();
    public int index;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.summary, container, false);
        new Summary.GetSummary(getActivity(), rootView).execute();
        String id = getArguments().getString("id");
        index = Integer.parseInt(id);
        return rootView;
    }

    private class GetSummary extends AsyncTask<Void, Void, Void> {

        private Context mContext;
        private View rootView;

        public GetSummary(Context context, View rootView) {
            this.mContext = context;
            this.rootView = rootView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            //Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray matches = jsonObj.getJSONArray("matches");

                    String status = matches.getJSONObject(index).getJSONObject("header").getString("state_title");
                    String series_name = matches.getJSONObject(index).getString("series_name");
                    String desc = matches.getJSONObject(index).getJSONObject("header").getString("match_desc");
                    String type = matches.getJSONObject(index).getJSONObject("header").getString("type");
                    String mom = matches.getJSONObject(index).getJSONObject("header").getJSONArray("momNames").getString(0);
                    String team1 = matches.getJSONObject(index).getJSONObject("team1").getString("name");
                    String team2 = matches.getJSONObject(index).getJSONObject("team2").getString("name");



                    summary_hp.put("status", status);
                    summary_hp.put("series_name", series_name);
                    summary_hp.put("desc", desc);
                    summary_hp.put("type", type);
                    summary_hp.put("mom", mom);
                    summary_hp.put("team1", team1);
                    summary_hp.put("team2", team2);

                    Log.e(TAG, "index " + index);
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            TextView status = (TextView) rootView.findViewById(R.id.status);
            TextView series_name = (TextView) rootView.findViewById(R.id.series_name);
            TextView desc = (TextView) rootView.findViewById(R.id.desc);
            TextView type = (TextView) rootView.findViewById(R.id.type);
            TextView mom = (TextView) rootView.findViewById(R.id.mom);
            TextView team1 = (TextView) rootView.findViewById(R.id.team1);
            TextView team2 = (TextView) rootView.findViewById(R.id.team2);


            status.setText(summary_hp.get("status"));
            series_name.setText(summary_hp.get("series_name"));
            desc.setText(summary_hp.get("desc"));
            type.setText(summary_hp.get("type"));
            mom.setText(summary_hp.get("mom"));
            team1.setText(summary_hp.get("team1"));
            team2.setText(summary_hp.get("team2"));

        }

    }
}
