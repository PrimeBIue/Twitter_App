package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Entities {

    public String mediaUrl;

    // empty constructor needed by the Parcel library
    public Entities() {}

    public static Entities fromJson(JSONObject jsonObject) throws JSONException {
        Entities entities = new Entities();
        if (jsonObject.has("media")) {
            JSONArray jsonArray = jsonObject.getJSONArray("media");
            Log.i("Entities", jsonArray.toString());
            JSONObject media = jsonArray.getJSONObject(0);
            entities.mediaUrl = media.getString("media_url_https");
        }

        return entities;
    }
}
