package com.gitlab.PCU.PCU.helper

import android.content.Context
import android.util.ArrayMap

import com.gitlab.PCU.PCU.R
import com.gitlab.PCU.PCU.Tools
import java.util.*

/**
 * Created by tim on 24.01.17.
 */

public class Defaults {

    class ServerVariable(val context: Context) {

        public val NAME: String = context.getString(R.string.default_name)
        public val DESC: String = context.resources.getString(R.string.default_desc)

        fun newServerSettingsStore(): ServerSettingsStore {
            val list: ArrayMap<String, Array<String>> = ServerCfg.list(context.getSharedPreferences("servers", 0))
            val a_to_z: CharRange = 'A'..'Z'.toUpperCase()
            var new_id: String = Tools.generateString(Random(909245970345L), a_to_z, 8)
            while (new_id in list.keys) new_id = Tools.generateString(Random(909245970345L), a_to_z, 8)
            return ServerSettingsStore(new_id, NAME, ServerStatic.IP, DESC)
        }
    }

    public object ServerStatic {
        public val IP = IP(0, 0, 0, 0)
        public val SERVER_JSON_OBJECT_NAME = "servers_test1"
        public val NAME: String = "No Name"
        public val DESC: String = "No Description"
        //public val SERVER_SETTINGS_STORE: ServerSettingsStore

        //init {
        //    val new_id: String = Tools.generateString(Random(909245970345L), 'A'..'Z', 8)
        //    this.SERVER_SETTINGS_STORE = ServerSettingsStore(new_id, NAME, ServerStatic.IP, DESC)
        //}
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
