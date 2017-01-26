package com.gitlab.PCU.PCU.helper;

import android.content.SharedPreferences;
import android.util.ArrayMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by tim on 18.01.17.
 */
public final class ServerCfg {

    public static ServerSettingsStore read(SharedPreferences sharedPreferences, Defaults.ServerVariable serverVariable, final String serverID) {
        String json = sharedPreferences.getString("servers", "{}");
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(new JSONTokener(json));

            if (jsonObject.has(Defaults.ServerStatic.SERVER_JSON_OBJECT_NAME)) {
                JSONObject servers = jsonObject.getJSONObject(Defaults.ServerStatic.SERVER_JSON_OBJECT_NAME);
                if (!servers.has(serverID)) {
                    JSONObject server = new JSONObject();
                    servers.put(serverID, server);

                }

                JSONObject server = servers.getJSONObject(serverID);

                String name;
                if (server.has("name")) {
                    name = server.getString("name");
                } else {
                    name = serverVariable.NAME;
                }

                IP ip;
                if (server.has("ip_a") && server.has("ip_b") && server.has("ip_c") && server.has("ip_d")) {
                    ip = new IP(server.getInt("ip_a"), server.getInt("ip_b"), server.getInt("ip_c"), server.getInt("ip_d"));
                } else {
                    ip = Defaults.ServerStatic.IP;
                }

                String desc;
                if (server.has("desc")) {
                    desc = server.getString("desc");
                } else {
                    desc = serverVariable.DESC;
                }

                return new ServerSettingsStore(name, ip, desc);

            } else {
                jsonObject.put(Defaults.ServerStatic.SERVER_JSON_OBJECT_NAME, new JSONObject().put(serverID, new JSONObject()));
                return serverVariable.SERVER_SETTINGS_STORE;
            }
        } catch (JSONException ignored) {
            return serverVariable.SERVER_SETTINGS_STORE;
        }
    }

    public static void write(SharedPreferences sharedPreferences, final String serverID, final ServerSettingsStore serverSettingsStore) {
        String json = sharedPreferences.getString("servers", "{}");
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(new JSONTokener(json));


            if (jsonObject.has(Defaults.ServerStatic.SERVER_JSON_OBJECT_NAME)) {
                JSONObject servers = jsonObject.getJSONObject(Defaults.ServerStatic.SERVER_JSON_OBJECT_NAME);
                if (!servers.has(serverID)) {
                    JSONObject server = new JSONObject();
                    servers.put(serverID, server);

                }

                JSONObject server = servers.getJSONObject(serverID);
                server.put("name", serverSettingsStore.getName());
                int[] ip = serverSettingsStore.getIp().getInt();
                server.put("ip_a", ip[0]);
                server.put("ip_a", ip[1]);
                server.put("ip_a", ip[2]);
                server.put("ip_a", ip[3]);
                server.put("desc", serverSettingsStore.getDesc());
            } else {
                jsonObject.put(Defaults.ServerStatic.SERVER_JSON_OBJECT_NAME, new JSONObject().put(serverID, new JSONObject()));
            }
            sharedPreferences.edit().putString("servers", jsonObject.toString()).apply();
        } catch (JSONException ignored) {
        }
    }

    public static ArrayMap<String, String[]> list(SharedPreferences sharedPreferences) {
        String json = sharedPreferences.getString("servers", "{}");
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(new JSONTokener(json));

            if (jsonObject.has(Defaults.ServerStatic.SERVER_JSON_OBJECT_NAME)) {
                JSONObject servers = jsonObject.getJSONObject(Defaults.ServerStatic.SERVER_JSON_OBJECT_NAME);
                Iterator<String> iterator = servers.keys();
                ArrayMap<String, String[]> stringArrayList = new ArrayMap<>();
                while (iterator.hasNext()) {
                    String name = iterator.next();
                    try {
                        JSONObject server = servers.getJSONObject(name);
                        stringArrayList.put(name, new String[]{server.getString("name"), server.getString("desc")});
                    } catch (JSONException ignored) {
                    }
                }
                return stringArrayList;
            }
        } catch (JSONException ignored) {
        }
        return new ArrayMap<>();
    }
}
