package com.gitlab.PCU.PCU;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gitlab.PCU.PCU.helper.Defaults;
import com.gitlab.PCU.PCU.helper.ServerCfg;
import com.gitlab.PCU.PCU.helper.ServerSettingsStore;

import java.util.ArrayList;
import java.util.Map;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.gitlab.PCU.PCU.helper.Defaults.ResultCode.RESULT_DELETE;

public class ServerSettingsActivity extends AppCompatActivity {

    private ArrayList<View> childs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_settings);
        createViews();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            //noinspection ConstantConditions
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            System.out.print("NPS @getSupportActionBar().setDisplayHomeAsUpEnabled(true)");
        }
    }

    private void createViews() {
        for (View child : childs) {
            ((ViewGroup) child.getParent()).removeView(child);
        }

        childs = new ArrayList<>();

        ArrayMap<String, String[]> list = ServerCfg.list(getSharedPreferences("servers", 0));

        Context context = getApplicationContext();

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.content_server_settings_ll);

        for (Map.Entry<String, String[]> listEntry : list.entrySet()) {
            TextView name = new TextView(context);
            name.setText(listEntry.getValue()[0]);
            TextView info = new TextView(context);
            info.setText(listEntry.getValue()[1]);
            String id = listEntry.getKey();
            addServerView(name, info, linearLayout, id);
        }
    }

    private void addServerView(TextView name, TextView info, LinearLayout linearLayout, String serverId) {
        if (linearLayout.getChildCount() > 0) {
            View spacer = new View(getApplicationContext());
            spacer.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, 2));
            spacer.setBackgroundColor(getResources().getColor(R.color.colorLightGray, getTheme()));
            linearLayout.addView(spacer);
            childs.add(spacer);
        }

        name.setTextSize(18);
        info.setTextSize(14);

        info.setTextColor(getResources().getColor(R.color.colorLightGray, getTheme()));

        EditOCL editOCL = new EditOCL(serverId);

        name.setOnClickListener(editOCL);
        info.setOnClickListener(editOCL);

        linearLayout.addView(name);
        childs.add(name);
        linearLayout.addView(info);
        childs.add(info);
    }

    public void onFab(View v) {
        Context context = getApplicationContext();
        ArrayMap<String, String[]> arrayMap = ServerCfg.list(getSharedPreferences("servers", 0));
        int i = arrayMap.size();
        while (arrayMap.containsKey("Server" + Integer.toString(i))) {
            i++;
        }
        String id = "Server" + Integer.toString(i);
        ServerSettingsStore serverSettingsStore = new Defaults.ServerVariable(context).SERVER_SETTINGS_STORE;
        ServerCfg.write(getSharedPreferences("servers", 0), id, serverSettingsStore);
        TextView name = new TextView(context);
        name.setText(serverSettingsStore.getName());
        TextView info = new TextView(context);
        info.setText(serverSettingsStore.getDesc());
        addServerView(name, info, (LinearLayout) findViewById(R.id.content_server_settings_ll), id);
    }

    private void startEdit(ServerSettingsStore serverSettingsStore, String serverId) {
        Intent intent = new Intent(getApplicationContext(), SingleServerSettings.class);
        intent.putExtra("in", serverSettingsStore);
        intent.putExtra("id", serverId);
        startActivityForResult(intent, Defaults.RequestCode.EDIT_SERVER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Defaults.RequestCode.EDIT_SERVER:
                if (resultCode == RESULT_OK && data != null) {
                    ServerSettingsStore serverSettingsStore = data.getParcelableExtra("out");
                    String id = data.getStringExtra("id");
                    ServerCfg.write(getSharedPreferences("servers", 0), id, serverSettingsStore);
                    createViews();
                } else if (resultCode == RESULT_DELETE && data != null) {
                    String id = data.getStringExtra("id");
                    ServerCfg.delete(getSharedPreferences("servers", 0), id);
                    createViews();
                }
                break;
        }
    }

    private class EditOCL implements View.OnClickListener {

        private final String id;

        EditOCL(String id) {
            this.id = id;
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            ServerSettingsStore serverSettingsStore = ServerCfg.read(getSharedPreferences("servers", 0), new Defaults.ServerVariable(getApplicationContext()), id);
            startEdit(serverSettingsStore, id);
        }
    }
}
