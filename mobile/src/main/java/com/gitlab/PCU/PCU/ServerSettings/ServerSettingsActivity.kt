package com.gitlab.PCU.PCU.ServerSettings

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.ArrayMap
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import android.widget.TextView
import com.gitlab.PCU.PCU.R
import com.gitlab.PCU.PCU.R.layout.activity_server_settings
import com.gitlab.PCU.PCU.helper.Defaults
import com.gitlab.PCU.PCU.helper.Defaults.ResultCode.RESULT_DELETE
import com.gitlab.PCU.PCU.helper.ServerCfg
import com.gitlab.PCU.PCU.helper.ServerSettingsStore
import kotlinx.android.synthetic.main.activity_main_screen.*
import kotlinx.android.synthetic.main.content_server_settings.*
import java.util.*


class ServerSettingsActivity : Activity() {

    private var adapter: RecyclerView.Adapter<*>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_server_settings)
        try {
            createViews()
        } catch (ignored: NullPointerException) { }

    }

    private fun createViews() {
        ServerCfg.list(getSharedPreferences("servers", 0))
        ss_rv.setHasFixedSize(false)
        layoutManager = LinearLayoutManager(applicationContext)
        ss_rv.layoutManager = layoutManager
        adapter = ServerSettingAdapter(ServerCfg.list(getSharedPreferences("servers", 0)))
        ss_rv.adapter = adapter
    }

    fun onFab(v: View) {
        val list: ArrayMap<String, Array<String>> = ServerCfg.list(getSharedPreferences("servers"))
        ServerCfg.write()
    }

    private fun startEdit(serverSettingsStore: ServerSettingsStore, serverId: String) {
        val intent = Intent(applicationContext, SingleServerSettings::class.java)
        intent.putExtra("in", serverSettingsStore)
        startActivityForResult(intent, Defaults.RequestCode.EDIT_SERVER)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            Defaults.RequestCode.EDIT_SERVER -> if (resultCode == RESULT_OK && data != null) {
                val serverSettingsStore = data.getParcelableExtra<ServerSettingsStore>("out")
                val id = data.getStringExtra("id")
                ServerCfg.write(getSharedPreferences("servers", 0), id, serverSettingsStore)
                createViews()
            } else if (resultCode == RESULT_DELETE && data != null) {
                val id = data.getStringExtra("id")
                ServerCfg.delete(getSharedPreferences("servers", 0), id)
                createViews()
            }
        }
    }

    private inner class EditOCL internal constructor(private val id: String) : View.OnClickListener {

        /**
         * Called when a view has been clicked.

         * @param v The view that was clicked.
         */
        override fun onClick(v: View) {
            val serverSettingsStore = ServerCfg.read(getSharedPreferences("servers", 0), Defaults.ServerVariable(applicationContext), id)
            startEdit(serverSettingsStore, id)
        }
    }
}
