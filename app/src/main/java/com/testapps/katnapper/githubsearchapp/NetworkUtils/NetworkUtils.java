package com.testapps.katnapper.githubsearchapp.NetworkUtils;

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by KATnapper on 24-05-2017.
 */

public class NetworkUtils {

    final static String GITHUB_BASE_URL = "https://api.github.com/search/repositories";
    final static String PARAM_QUERY = "q";
    final static String PARAM_SORT = "sort";
    final static String sortBy = "stars";


    public static URL buildURL(String searchText){
        Uri uri = Uri.parse(GITHUB_BASE_URL).buildUpon().appendQueryParameter(PARAM_QUERY,searchText).appendQueryParameter(PARAM_SORT,sortBy).build();
        URL url = null;
        try{
            url = new URL(uri.toString());
        }catch (MalformedURLException ex){
            ex.printStackTrace();
        }

        return url;
    }

    public static JSONObject downloadResults(URL url) throws IOException, JSONException {
        JSONObject jsn = null;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(3000);
        connection.setRequestMethod("GET");
        connection.connect();
        if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            String inputdata;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputdata = bufferedReader.readLine()) != null){
                stringBuilder.append(inputdata);
            }

            jsn = new JSONObject(stringBuilder.toString());

        }

        return jsn;
    }

}
