package com.testapps.katnapper.githubsearchapp.Github;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by KATnapper on 24-05-2017.
 */

public class GithubObject {
    private String name;
    private String description;
    private String url;
    private String createdDate;
    private String pushedDate;
    private JSONObject input;

    public GithubObject(JSONObject jsn){
        input = jsn;
        convertJSON();
    }

    public void convertJSON(){
        if(input != null){
            try{
                JSONObject ob = input;
                name = ob.get("name").toString();
                description = ob.get("description").toString();
                url = ob.get("url").toString();
                createdDate = ob.get("created_at").toString();
                pushedDate = ob.get("pushed_at").toString();

            }catch(JSONException ex){
                ex.printStackTrace();
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getPushedDate() {
        return pushedDate;
    }
}
