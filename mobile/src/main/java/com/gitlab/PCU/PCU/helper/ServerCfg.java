package com.gitlab.PCU.PCU.helper;

import android.content.SharedPreferences;
import android.util.ArrayMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Map;

/**
 * Created by tim on 18.01.17.
 */

public final class ServerCfg {

    public static ArrayMap<String, ServerSettingsStore> read(SharedPreferences sharedPreferences) {
        String json = sharedPreferences.getString("servers", "{}");
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(new JSONTokener(json));
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayMap<>();
        }

        if (jsonObject.has("servers")) {
            try {
                JSONArray servers = jsonObject.getJSONArray("servers");
                ArrayMap<String, ServerSettingsStore> serverSettingsStores = new ArrayMap<>();
                for (int i = 0; i < servers.length(); i++) {
                    JSONObject server = servers.getJSONObject(i);
                    try {
                        ServerSettingsStore serverSettingsStore = new ServerSettingsStore(server.getString("name"));
                        serverSettingsStores.put(serverSettingsStore.getName(), serverSettingsStore);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return serverSettingsStores;
            } catch (JSONException e) {
                e.printStackTrace();
                return new ArrayMap<>();
            }
        } else {
            return new ArrayMap<>();
        }
    }

    public static void write(SharedPreferences sharedPreferences, ArrayMap<String, ServerSettingsStore> serverSettingsStores) {
        JSONArray servers = new JSONArray();
        for (Map.Entry<String, ServerSettingsStore> serverSettingsStoreEntry : serverSettingsStores.entrySet()) {
            try {
                ServerSettingsStore serverSettingsStore = serverSettingsStoreEntry.getValue();
                JSONObject server = new JSONObject();
                server.put("name", serverSettingsStore.getName());
                servers.put(server);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("servers", servers);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String jsonText = jsonObject.toString();
        sharedPreferences.edit().putString("servers", jsonText).apply();
    }
}
