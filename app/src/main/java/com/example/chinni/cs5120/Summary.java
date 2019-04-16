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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Summary extends Fragment {

    private String TAG = MatchSummary.class.getSimpleName();
    String url = "http://mapps.cricbuzz.com/cbzios/match/livematches";
    String summary_url;
    private ProgressDialog pDialog;
    HashMap<String, String> summary_hp = new HashMap<String, String>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.summary,container,false);
        new Summary.GetSummary(getActivity(), rootView).execute();
        return rootView;
    }

    private class GetSummary extends AsyncTask<Void, Void, Void> {

        private Context mContext;
        private View rootView;
        public GetSummary(Context context, View rootView){
            this.mContext=context;
            this.rootView=rootView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            // pDialog = new ProgressDialog(Details.this);
            // pDialog.setMessage("Please wait...");
            // pDialog.setCancelable(false);
            // pDialog.show();

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

                    String status = matches.getJSONObject(0).getJSONObject("header").getString("status");
                    String series_name = matches.getJSONObject(0).getString("series_name");
                    String toss = matches.getJSONObject(0).getJSONObject("header").getString("toss");
                    String type = matches.getJSONObject(0).getJSONObject("header").getString("type");


                    summary_hp.put("status", status);
                    summary_hp.put("series_name",series_name);
                    summary_hp.put("toss",toss);
                    summary_hp.put("type",type);
                    Log.e(TAG, "status"+status);
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    /* runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    }); */

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                /* runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                }); */

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            //if (pDialog.isShowing())
            //  pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            //TextView tv = (TextView)findViewById(R.id.summaryText);
            TextView series_name = (TextView) rootView.findViewById(R.id.series_name);
            TextView toss = (TextView) rootView.findViewById(R.id.toss);
            TextView status = (TextView) rootView.findViewById(R.id.status);
            TextView type = (TextView) rootView.findViewById(R.id.type);

            series_name.setText(summary_hp.get("series_name"));
            toss.setText(summary_hp.get("toss"));
            status.setText(summary_hp.get("status"));
            type.setText(summary_hp.get("type"));

        }

    }
}
