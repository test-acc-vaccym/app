package com.gitlab.PCU.PCU.helper;

import android.util.ArrayMap;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by tim on 20.01.17.
 */


public class DFCompound {

    private ServerSettingsStore serverSettingsStore;
    private Button button;
    private ArrayMap<String, ServerSettingsStore> serverSettingsStores;

    public DFCompound(ServerSettingsStore serverSettingsStore, Button button, ArrayMap<String, ServerSettingsStore> serverSettingsStores) {
        this.serverSettingsStore = serverSettingsStore;
        this.button = button;
        this.serverSettingsStores = serverSettingsStores;
    }

    public ServerSettingsStore getServerSettingsStore() {
        return serverSettingsStore;
    }

    public Button getButton() {
        return button;
    }

    public ArrayMap<String, ServerSettingsStore> getServerSettingsStores() {
        return serverSettingsStores;
    }
}
