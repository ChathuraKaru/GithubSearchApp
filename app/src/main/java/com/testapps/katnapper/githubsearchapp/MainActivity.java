package com.testapps.katnapper.githubsearchapp;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.testapps.katnapper.githubsearchapp.Github.GithubObject;
import com.testapps.katnapper.githubsearchapp.NetworkUtils.displayResutls;
import com.testapps.katnapper.githubsearchapp.NetworkUtils.Downloader;
import com.testapps.katnapper.githubsearchapp.NetworkUtils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements displayResutls {

    List<String> results = new ArrayList<>();
    ListView list;
    EditText searchText;
    Button search;
    List<String> tempResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
        list = (ListView) findViewById(R.id.lst_results);
        searchText = (EditText) findViewById(R.id.txtSearch);
        search = (Button) findViewById(R.id.btn_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaySearchResults();
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,results);
        list.setAdapter(adapter);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        tempResults = results;
        setContentView(R.layout.activity_main);
        initializeViews();
    }

    private void initializeViews(){
        list = (ListView) findViewById(R.id.lst_results);
        searchText = (EditText) findViewById(R.id.txtSearch);
        search = (Button) findViewById(R.id.btn_search);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,tempResults);
        list.setAdapter(adapter);
    }

    public void showResults(JSONObject jsn){
        try {
            JSONArray array = jsn.getJSONArray("items");
            results.clear();
            for(int i = 0;i<array.length();i++){
                JSONObject json = array.getJSONObject(i);
                GithubObject git = new GithubObject(json);
                String listItem = "Name: "+git.getName()+"\n\n"+"Description: "+git.getDescription()+"\n\n"+"url: "+git.getUrl();
                results.add(listItem);
            }
            ArrayAdapter<String> adapter = (ArrayAdapter<String>) list.getAdapter();
            adapter.notifyDataSetChanged();
        }catch (JSONException js){
            js.printStackTrace();
        }

    }


    public void displaySearchResults() {
        String search = searchText.getText().toString();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager!=null){
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isConnected() && (networkInfo.getType()==ConnectivityManager.TYPE_MOBILE || networkInfo.getType()==ConnectivityManager.TYPE_WIFI)){
                new Downloader(this).execute(search);
            }
        }
    }
}
