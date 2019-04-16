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
    String summary_url;
    private ProgressDialog pDialog;
    HashMap<String, String> summary_hp = new HashMap<String, String>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.details,container,false);
        new GetContacts(getActivity(), rootView).execute();
        return rootView;
    }


    private class GetContacts extends AsyncTask<Void, Void, Void> {

        private Context mContext;
        private View rootView;
        public GetContacts(Context context, View rootView){
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

                    JSONArray batsmans = matches.getJSONObject(0).getJSONArray("batsman");
                    String b1_name = batsmans.getJSONObject(0).getString("name");
                    String b1_runs = batsmans.getJSONObject(0).getString("r");
                    String b1_4s = batsmans.getJSONObject(0).getString("4s");
                    String b1_6s = batsmans.getJSONObject(0).getString("6s");


                    summary_hp.put("status", status);
                    summary_hp.put("b1_name",b1_name);
                    summary_hp.put("b1_runs",b1_runs);
                    summary_hp.put("b1_4s",b1_4s);
                    summary_hp.put("b1_6s",b1_6s);
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
            TextView details_view = (TextView) rootView.findViewById(R.id.status);
            TextView b1_name = (TextView)rootView.findViewById(R.id.b1_name);
            TextView b1_runs = (TextView)rootView.findViewById(R.id.b1_runs);
            TextView b1_4s = (TextView)rootView.findViewById(R.id.b1_4s);
            TextView b1_6s = (TextView)rootView.findViewById(R.id.b1_6s);

            details_view.setText(summary_hp.get("status"));
            b1_name.setText(summary_hp.get("b1_name"));
            b1_runs.setText(summary_hp.get("b1_runs"));
            b1_4s.setText(summary_hp.get("b1_4s"));
            b1_6s.setText(summary_hp.get("b1_6s"));
        }

    }
}
