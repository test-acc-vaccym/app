package com.gitlab.PCU.PCU;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.gitlab.PCU.PCU.helper.StaticContent;
import com.gitlab.PCU.PCU.helper.ServerCfg;
import com.gitlab.PCU.PCU.helper.ServerSettingsStore;

import java.util.ArrayList;

public class ServerSettingsActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private ArrayList<ServerSettingsStore> serverSettingsStores = new ArrayList<>();
    private boolean fresh = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(StaticContent.SERVER_PREF_SP_KEY, StaticContent.SP_MODE);
        serverSettingsStores = ServerCfg.read(sharedPreferences);
        makeServerInView(serverSettingsStores);
        setContentView(R.layout.activity_server_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            //noinspection ConstantConditions
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            System.out.print("NPS @getSupportActionBar().setDisplayHomeAsUpEnabled(true)");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!fresh) {
            serverSettingsStores = ServerCfg.read(sharedPreferences);
        } else {
            fresh = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*try {
            ServerCfg.write(getApplicationContext(), serverSettingsStores);
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverSettingsStores = new ArrayList<>();*/
    }

    public static class SingleServerSettingsFragment extends DialogFragment {

        private ServerSettingsStore serverSettingsStore;
        private Button button;

        public void setArguments(ServerSettingsStore serverSettingsStore, Button button) {
            this.serverSettingsStore = serverSettingsStore;
            this.button = button;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            LinearLayout v = (LinearLayout) inflater.inflate(R.layout.single_server_settings, container, false);
            EditText et = new EditText(getContext());
            et.setText(serverSettingsStore.getName());
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    button.setText(s);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            v.addView(et);
            return v;
        }

    }

    private void makeServerInView(ArrayList<ServerSettingsStore> serverSettingsStores) {

        for (ServerSettingsStore serverSettingsStore : serverSettingsStores) {
            registerServer(serverSettingsStore);
        }
    }

    private void registerServer(final ServerSettingsStore serverSettingsStore) {
        final Context context = getApplicationContext();
        LinearLayout ll = (LinearLayout) findViewById(R.id.content_server_settings_ll);
        if (ll == null) {
            System.out.println("ll is null");
            return;
        }
        final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                SingleServerSettingsFragment sssf = new SingleServerSettingsFragment();
                ServerSettingsStore sss = new ServerSettingsStore(button.getText().toString());
                sssf.setArguments(sss, button);
                sssf.show(ft, "dialog");
            }
        };


        Button b = new Button(context);
        b.setText(serverSettingsStore.getName());
        b.setOnClickListener(onClickListener);
        ll.addView(b);
    }

    public void onClickFabServerSettings(View view) {
        Snackbar.make(view, "Added new Server", Snackbar.LENGTH_LONG).show();
        ServerSettingsStore serverSettingsStore = new ServerSettingsStore(getString(R.string.default_name));
        serverSettingsStores.add(serverSettingsStore);
        registerServer(serverSettingsStore);
    }
}
