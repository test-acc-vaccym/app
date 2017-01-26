package com.gitlab.PCU.PCU.helper;

import android.content.Context;

import com.gitlab.PCU.PCU.R;

/**
 * Created by tim on 24.01.17.
 */

@SuppressWarnings("WeakerAccess")
public final class Defaults {

    public static class ServerVariable {

        public ServerVariable(Context context) {
            this.NAME = context.getString(R.string.default_name);
            this.DESC = context.getResources().getString(R.string.default_desc);
            this.SERVER_SETTINGS_STORE = new ServerSettingsStore(NAME, ServerStatic.IP, DESC);
        }

        public final String NAME;
        public final String DESC;
        public final ServerSettingsStore SERVER_SETTINGS_STORE;
    }

    public static final class ServerStatic {
        public static final IP IP = new IP(0, 0, 0, 0);
        public static final String SERVER_JSON_OBJECT_NAME = "servers_test1";
    }

    public static final class RequestCode {
        public static final int EDIT_SERVER = 4;
    }
}