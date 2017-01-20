package com.gitlab.PCU.PCU;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
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

import com.gitlab.PCU.PCU.helper.ServerCfg;
import com.gitlab.PCU.PCU.helper.ServerSettingsStore;

import java.io.IOException;
import java.util.ArrayList;

public class ServerSettingsActivity extends AppCompatActivity {

    public ArrayList<ServerSettingsStore> serverSettingsStores = new ArrayList<>();
    public boolean fresh = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serverSettingsStores = new ArrayList<>();
        try {
            ServerCfg.read(getApplicationContext(), serverSettingsStores);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            serverSettingsStores = new ArrayList<>();
            try {
                ServerCfg.read(getApplicationContext(), serverSettingsStores);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            fresh = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            ServerCfg.write(getApplicationContext(), serverSettingsStores);
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverSettingsStores = new ArrayList<>();
    }

    protected boolean isValidFragment(String fragmentName) {
        return SingleServerSettingsFragment.class.getName().equals(fragmentName);
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

    public void makeServerInView(ArrayList<ServerSettingsStore> serverSettingsStores) {
        final Context context = getApplicationContext();
        final LinearLayout ll = (LinearLayout) findViewById(R.id.content_server_settings);
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
        for (ServerSettingsStore serverSettingsStore : serverSettingsStores) {
            Button b = new Button(context);
            b.setText(serverSettingsStore.getName());
            b.setOnClickListener(onClickListener);
            ll.addView(b);
        }
    }

    public void onClickFabServerSettings(View view) {
        Snackbar.make(view, "Added new Server", Snackbar.LENGTH_LONG).show();
        Button b = new Button(getApplicationContext());
        b.setText(getString(R.string.default_name));
        b.setOnClickListener(new View.OnClickListener() {
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
        });
        LinearLayout ll = (LinearLayout) findViewById(R.id.content_server_settings);
        ll.addView(b);
    }
}
