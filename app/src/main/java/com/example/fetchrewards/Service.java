package com.example.fetchrewards;

import android.os.AsyncTask;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class Service extends AsyncTask<Void, Void, HashMap<Integer,ArrayList<String>>> {
    private static final String LOG_TAG = "ExampleApp";
    private static final String PLACES_API_BASE = "https://fetch-hiring.s3.amazonaws.com/hiring.json";
    public static HashMap<Integer, ArrayList<String>> resultList = null;

    @Override
    protected HashMap<Integer,ArrayList<String>> doInBackground(Void... voids) {

        HashMap<Integer, ArrayList<String>> dictMap = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            URL url = new URL(PLACES_API_BASE);
            conn = (HttpsURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return null;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            String str = jsonResults.toString();
            str = str.substring(2);
            str = str.substring(0,str.length()-2);

            String[] arr = str.split("\n");
            dictMap = new HashMap<>();
            for (String s:arr) {
                JSONObject jsonObj = new JSONObject(s);
                Integer key = jsonObj.getInt("listId");
                String n = jsonObj.getString("name");
                if(n.equals("null") || n.equals("")){
                    continue;
                }
                if(dictMap.containsKey(key)){
                    ArrayList<String> names = dictMap.get(key);
                    assert names != null;
                    names.add(n);
                    dictMap.put(key,names);
                }else{
                    ArrayList<String> temp = new ArrayList<>();
                    temp.add(n);
                    dictMap.put(key,temp);
                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error processing JSON results", e);
        }

        return dictMap;
    }


    @Override
    protected void onPostExecute(HashMap<Integer,ArrayList<String>> items) {
        super.onPostExecute(items);
        resultList = items;
    }
}

