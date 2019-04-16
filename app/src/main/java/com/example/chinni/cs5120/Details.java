package com.example.chinni.cs5120;

import android.app.ProgressDialog;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class Details extends Fragment {


    private String TAG = MatchSummary.class.getSimpleName();
    String url = "http://mapps.cricbuzz.com/cbzios/match/22466/scorecard.json";
    String summary_url;
    private ProgressDialog pDialog;
    HashMap<String, String> summary_hp = new HashMap<String, String>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.details,container,false);
        return rootView;
    }


    private class GetContacts extends AsyncTask<Void, Void, Void> {

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
            String jsonStr = sh.makeServiceCall(summary_url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String score = jsonObj.getString("score");
                    summary_hp.put("score", score);
                    Log.e(TAG, "score"+score);
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
            TextView tv = (TextView)findViewById(R.id.summaryText);
            String msg = summary_hp.get("score");
            Log.e(TAG, "msg"+msg);
            //tv.setText(msg.replace("amp;", ""));
        }

    }
}
