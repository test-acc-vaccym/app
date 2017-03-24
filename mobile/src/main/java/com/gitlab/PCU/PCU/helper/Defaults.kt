package com.gitlab.PCU.PCU.helper

import android.content.Context

import com.gitlab.PCU.PCU.R

/**
 * Created by tim on 24.01.17.
 */

public class Defaults {

    class ServerVariable(context: Context) {

        public val NAME: String = context.getString(R.string.default_name)
        public val DESC: String = context.resources.getString(R.string.default_desc)
        public val SERVER_SETTINGS_STORE: ServerSettingsStore

        init {
            this.SERVER_SETTINGS_STORE = ServerSettingsStore(NAME, ServerStatic.IP, DESC)
        }
    }

    public object ServerStatic {
        public val IP = IP(0, 0, 0, 0)
        public val SERVER_JSON_OBJECT_NAME = "servers_test1"
        public val NAME: String = "No Name (Error)"
        public val DESC: String = "No Description (Error)"
        public val SERVER_SETTINGS_STORE: ServerSettingsStore

        init {
            this.SERVER_SETTINGS_STORE = ServerSettingsStore(NAME, ServerStatic.IP, DESC)
        }
    }

    object RequestCode {
        public val REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS_UNLOCK = 1
        public val REQUEST_CODE_SETTINGS = 2
        public val REQUEST_CODE_SERVER_SETTINGS = 3
        public val EDIT_SERVER = 4
    }

    object ResultCode {
        public val RESULT_DELETE = 2
    }
}
