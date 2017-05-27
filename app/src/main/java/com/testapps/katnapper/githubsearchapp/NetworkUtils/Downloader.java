package com.testapps.katnapper.githubsearchapp.NetworkUtils;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import com.testapps.katnapper.githubsearchapp.NetworkUtils.NetworkUtils;
import com.testapps.katnapper.githubsearchapp.Github.GithubObject;

/**
 * Created by KATnapper on 24-05-2017.
 */

public class Downloader extends AsyncTask<String, Void, JSONObject> {

    private displayResutls resultDisplayInt;
    public Downloader(displayResutls resultDisplayInt){
        this.resultDisplayInt = resultDisplayInt;
    }


    @Override
    protected JSONObject doInBackground(String... params) {
        try{
            URL url = NetworkUtils.buildURL(params[0]);
            return NetworkUtils.downloadResults(url);
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
        catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        if(jsonObject != null){
            this.resultDisplayInt.showResults(jsonObject);
        }
    }
}
