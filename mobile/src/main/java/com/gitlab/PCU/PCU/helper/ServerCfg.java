package com.gitlab.PCU.PCU.helper;

import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

/**
 * Created by tim on 18.01.17.
 */

public final class ServerCfg {

    public static ArrayList<ServerSettingsStore> read(SharedPreferences sharedPreferences) {
        //String json = sharedPreferences.getString("servers", "{}");
        String json = "{" +
                "\"servers\": [" +
                "{" +
                "\"name\": \"Server 1\"" +
                "}," +
                "{" +
                "\"name\": \"Server 2\"" +
                "}" +
                "]" +
                "}";

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(new JSONTokener(json));
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        if (jsonObject.has("servers")) {
            try {
                JSONArray servers = jsonObject.getJSONArray("servers");
                ArrayList<ServerSettingsStore> serverSettingsStores = new ArrayList<>();
                for (int i = 0; i < servers.length(); i++) {
                    JSONObject server = servers.getJSONObject(i);
                    try {
                        serverSettingsStores.add(new ServerSettingsStore(server.getString("name")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return serverSettingsStores;
            } catch (JSONException e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        } else {
            return new ArrayList<>();
        }
    }
}
