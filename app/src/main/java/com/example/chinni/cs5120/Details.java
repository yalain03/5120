package com.example.chinni.cs5120;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class Details extends Fragment {


    private String TAG = MatchSummary.class.getSimpleName();
    String url = "http://mapps.cricbuzz.com/cbzios/match/livematches";
    HashMap<String, String> summary_hp = new HashMap<String, String>();
    public int index;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.details, container, false);
        new GetContacts(getActivity(), rootView).execute();
        String id = getArguments().getString("id");
        index = Integer.parseInt(id);
        return rootView;
    }


    private class GetContacts extends AsyncTask<Void, Void, Void> {

        private Context mContext;
        private View rootView;

        public GetContacts(Context context, View rootView) {
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
                    String status = matches.getJSONObject(index).getJSONObject("header").getString("status");

                    JSONArray batsmans = matches.getJSONObject(index).getJSONArray("batsman");
                    String b1_name = batsmans.getJSONObject(0).getString("name");
                    String b1_runs = batsmans.getJSONObject(0).getString("r");
                    String b1_4s = batsmans.getJSONObject(0).getString("4s");
                    String b1_6s = batsmans.getJSONObject(0).getString("6s");

                    String b2_name = batsmans.getJSONObject(1).getString("name");
                    String b2_runs = batsmans.getJSONObject(1).getString("r");
                    String b2_4s = batsmans.getJSONObject(1).getString("4s");
                    String b2_6s = batsmans.getJSONObject(1).getString("6s");

                    JSONArray bowler = matches.getJSONObject(index).getJSONArray("bowler");
                    String bw_name = bowler.getJSONObject(0).getString("name");
                    String bw_over = bowler.getJSONObject(0).getString("o");
                    String bw_runs = bowler.getJSONObject(0).getString("r");
                    String bw_wkt = bowler.getJSONObject(0).getString("w");

                    String toss = matches.getJSONObject(index).getJSONObject("header").getString("toss");
                    String location = matches.getJSONObject(index).getJSONObject("venue").getString("location");
                    String name = matches.getJSONObject(index).getJSONObject("venue").getString("name");


                    summary_hp.put("status", status);
                    summary_hp.put("b1_name", b1_name);
                    summary_hp.put("b1_runs", b1_runs);
                    summary_hp.put("b1_4s", b1_4s);
                    summary_hp.put("b1_6s", b1_6s);

                    summary_hp.put("b2_name", b2_name);
                    summary_hp.put("b2_runs", b2_runs);
                    summary_hp.put("b2_4s", b2_4s);
                    summary_hp.put("b2_6s", b2_6s);

                    summary_hp.put("toss", toss);
                    summary_hp.put("location", location);
                    summary_hp.put("name", name);

                    summary_hp.put("bw_name", bw_name);
                    summary_hp.put("bw_over", bw_over);
                    summary_hp.put("bw_runs", bw_runs);
                    summary_hp.put("bw_wkt", bw_wkt);

                    Log.e(TAG, "status" + status);
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

            TextView details_view = (TextView) rootView.findViewById(R.id.status);
            TextView b1_name = (TextView) rootView.findViewById(R.id.b1_name);
            TextView b1_runs = (TextView) rootView.findViewById(R.id.b1_runs);
            TextView b1_4s = (TextView) rootView.findViewById(R.id.b1_4s);
            TextView b1_6s = (TextView) rootView.findViewById(R.id.b1_6s);

            TextView b2_name = (TextView) rootView.findViewById(R.id.b2_name);
            TextView b2_runs = (TextView) rootView.findViewById(R.id.b2_runs);
            TextView b2_4s = (TextView) rootView.findViewById(R.id.b2_4s);
            TextView b2_6s = (TextView) rootView.findViewById(R.id.b2_6s);

            TextView bw_name = (TextView) rootView.findViewById(R.id.bw_name);
            TextView bw_over = (TextView) rootView.findViewById(R.id.bw_over);
            TextView bw_runs = (TextView) rootView.findViewById(R.id.bw_runs);
            TextView bw_wkt = (TextView) rootView.findViewById(R.id.bw_wkt);

            TextView toss = (TextView) rootView.findViewById(R.id.toss);
            TextView location = (TextView) rootView.findViewById(R.id.location);
            TextView stadium = (TextView) rootView.findViewById(R.id.stadium);

            details_view.setText(summary_hp.get("status"));
            b1_name.setText(summary_hp.get("b1_name"));
            b1_runs.setText(summary_hp.get("b1_runs"));
            b1_4s.setText(summary_hp.get("b1_4s"));
            b1_6s.setText(summary_hp.get("b1_6s"));

            b2_name.setText(summary_hp.get("b2_name"));
            b2_runs.setText(summary_hp.get("b2_runs"));
            b2_4s.setText(summary_hp.get("b2_4s"));
            b2_6s.setText(summary_hp.get("b2_6s"));

            bw_name.setText(summary_hp.get("bw_name"));
            bw_over.setText(summary_hp.get("bw_over"));
            bw_runs.setText(summary_hp.get("bw_runs"));
            bw_wkt.setText(summary_hp.get("bw_wkt"));

            toss.setText(summary_hp.get("toss"));
            location.setText(summary_hp.get("location"));
            stadium.setText(summary_hp.get("name"));
        }

    }
}
