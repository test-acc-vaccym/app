package com.gitlab.PCU.PCU.helper;

import android.content.Context;
import android.util.JsonWriter;

import com.gitlab.PCU.PCU.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by tim on 18.01.17.
 */

public final class ServerCfg {

    public static void read(Context context, ArrayList<ServerSettingsStore> serverSettingsStores) throws IOException {
        File data_dir = context.getFilesDir();
        File server_cfg_file = new File(data_dir.toString() + "server_cfg.json");
        if (!server_cfg_file.canRead()) {
            //noinspection ResultOfMethodCallIgnored
            server_cfg_file.setReadable(true);
        }

        if (!server_cfg_file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            server_cfg_file.createNewFile();
        }
        FileReader reader = new FileReader(server_cfg_file);
        char[] file_content = new char[]{'a', 'b'};
        //noinspection ResultOfMethodCallIgnored
        reader.read(file_content);
        String content = "";
        for (char c : file_content) {
            content += Character.toString(c);
        }
        JSONObject json;
        try {
            json = new JSONObject(content);
        } catch (JSONException e) {
            json = new JSONObject();
        }

        try {
            JSONObject servers = json.getJSONObject("servers");
            Iterator<String> iter = servers.keys();
            ArrayList<JSONObject> serverObjects = new ArrayList<>();
            while (iter.hasNext()) {
                final String key = iter.next();
                if (servers.has(key)) {
                    Object object = servers.get(key);
                    if (object.getClass().getName().equals(JSONObject.class.getName())) {
                        serverObjects.add((JSONObject) object);
                    }
                }
            }
            for (JSONObject serverObject : serverObjects) {
                String name = "";
                if (serverObject.has("name")) {
                    Object object = serverObject.get("name");
                    if (object.getClass().getName().equals(String.class.getName())) {
                        name = (String) object;
                    } else {
                        name = context.getResources().getString(R.string.default_name);
                    }
                }
                serverSettingsStores.add(new ServerSettingsStore(name));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void write(Context context, ArrayList<ServerSettingsStore> serverSettingsStores) throws IOException {
        File data_dir = context.getFilesDir();
        File server_cfg_file = new File(data_dir.toString() + "server_cfg.json");
        if (!server_cfg_file.canWrite()) {
            //noinspection ResultOfMethodCallIgnored
            server_cfg_file.setWritable(true);
        }

        if (!server_cfg_file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            server_cfg_file.createNewFile();
        }
        try {
            JSONObject servers = new JSONObject();
            int i = 0;
            for (ServerSettingsStore serverSettingsStore : serverSettingsStores) {
                JSONObject server = new JSONObject();
                server.put("name", serverSettingsStore.getName());
                servers.put(Integer.toString(i), server);
                i++;
            }
            JSONObject json = new JSONObject().put("servers", servers);
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(data_dir.toString() + "server_cfg.json"));
            outputStream.writeObject(json);
            outputStream.flush();
            outputStream.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
