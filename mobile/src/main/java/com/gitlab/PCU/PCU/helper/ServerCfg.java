package com.gitlab.PCU.PCU.helper;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by tim on 18.01.17.
 */

public class ServerCfg {
    private JSONObject json;
    private String content = "";

    public ServerCfg(Context context) throws IOException {
        File data_dir = context.getFilesDir();
        File server_cfg_file = new File(data_dir.toString() + "server_cfg.json");
        if (!server_cfg_file.canRead()) {
            //noinspection ResultOfMethodCallIgnored
            server_cfg_file.setReadable(true);
        }

        if (!server_cfg_file.canWrite()) {
            //noinspection ResultOfMethodCallIgnored
            server_cfg_file.setWritable(true, true);
        }

        if (!server_cfg_file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            server_cfg_file.createNewFile();
        }
        FileReader reader = new FileReader(server_cfg_file);
        char[] file_content = new char[]{'a', 'b'};
        //noinspection ResultOfMethodCallIgnored
        reader.read(file_content);
        for (char c : file_content) {
            content += Character.toString(c);
        }
        try {
            json = new JSONObject(content);
        } catch (JSONException e) {
            json = new JSONObject();
        }
        System.out.println(json.has("servers"));
    }

    public String get_content() {
        return content;
    }
}
