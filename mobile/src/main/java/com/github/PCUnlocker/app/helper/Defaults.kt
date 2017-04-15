package com.github.PCUnlocker.app.helper

import android.content.Context
import android.util.ArrayMap

import com.github.PCUnlocker.app.R
import com.github.PCUnlocker.app.Tools
import java.util.*

/**
 * Created by tim on 24.01.17.
 */

class Defaults {

    class ServerVariable(val context: Context) {

        val NAME: String = context.getString(R.string.default_name)
        val DESC: String = context.resources.getString(R.string.default_desc)

        fun newServerSettingsStore(): ServerSettingsStore {
            val list: ArrayMap<String, Pair<String, String>> = ServerCfg.list(context.getSharedPreferences("servers", 0))
            val a_to_z: CharRange = 'A'..'Z'.toUpperCase()
            var new_id: String = Tools.generateString(Random(909245970345L), a_to_z, 8)
            while (new_id in list.keys) new_id = Tools.generateString(Random(909245970345L), a_to_z, 8)
            return ServerSettingsStore(new_id, NAME, ServerStatic.IP, DESC)
        }
    }

    object ServerStatic {
        val IP = IP(0, 0, 0, 0)
        val SERVER_JSON_OBJECT_NAME = "servers_test1"
        val NAME: String = "No Name"
        val DESC: String = "No Description"
        //public val SERVER_SETTINGS_STORE: ServerSettingsStore

        //init {
        //    val new_id: String = Tools.generateString(Random(909245970345L), 'A'..'Z', 8)
        //    this.SERVER_SETTINGS_STORE = ServerSettingsStore(new_id, NAME, ServerStatic.IP, DESC)
        //}
    }

    object RequestCode {
        val REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS_UNLOCK = 1
        val REQUEST_CODE_SETTINGS = 2
        val REQUEST_CODE_SERVER_SETTINGS = 3
        val EDIT_SERVER = 4
    }

    object ResultCode {
        val RESULT_DELETE = 2
    }
}
