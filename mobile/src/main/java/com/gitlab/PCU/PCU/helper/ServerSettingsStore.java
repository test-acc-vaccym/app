package com.gitlab.PCU.PCU.helper;

/**
 * Created by tim on 19.01.17.
 */

public final class ServerSettingsStore {

    private String name;

    public ServerSettingsStore(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ServerSettingsStore setName(String name) {
        this.name = name;
        return this;
    }
}
