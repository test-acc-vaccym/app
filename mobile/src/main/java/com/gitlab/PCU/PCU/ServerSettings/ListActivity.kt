package com.gitlab.PCU.PCU.ServerSettings

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.ArrayMap
import android.util.Log
import android.view.View
import android.widget.Toast
import com.gitlab.PCU.PCU.R.layout.activity_server_settings
import com.gitlab.PCU.PCU.Tools.generateString
import com.gitlab.PCU.PCU.helper.Defaults
import com.gitlab.PCU.PCU.helper.Defaults.ResultCode.RESULT_DELETE
import com.gitlab.PCU.PCU.helper.Errors
import com.gitlab.PCU.PCU.helper.ServerCfg
import com.gitlab.PCU.PCU.helper.ServerSettingsStore
import kotlinx.android.synthetic.main.content_server_settings.*
import java.security.SecureRandom
import java.util.*


class ListActivity : Activity() {

    private var adapter: RecyclerView.Adapter<*>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var serverPreferences: SharedPreferences? = null
    private val random: Random = Random(SecureRandom().nextLong())

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_server_settings)
        serverPreferences = getSharedPreferences("servers", 0)
        ss_rv.setHasFixedSize(false)
        layoutManager = LinearLayoutManager(applicationContext)
        ss_rv.layoutManager = layoutManager
        createViews()
        Log.d("dbg", "Created")
    }

    private fun createViews() {
        Log.d("dbg", "create Views...")
        if (serverPreferences != null) {
            val list: ArrayMap<String, Pair<String, String>> = ServerCfg.list(serverPreferences as SharedPreferences)
            adapter = ListAdapter(list)
            ss_rv.adapter = adapter
            ss_rv.invalidate()
        }
        Log.d("dbg", "created Views")
    }

    fun onFab(v: View) {
        val list: ArrayMap<String, Pair<String, String>> = ServerCfg.list(serverPreferences!!)
        val a_to_z: CharRange = CharRange('A', 'Z')
        var new_id: String = generateString(random, a_to_z, 8)
        var trys: Int = 0
        while (new_id in list.keys) {
            new_id = generateString(random, a_to_z, 8)
            trys++
            if (trys > 50) Toast.makeText(applicationContext, Errors.RANDOM_OVER_50_TRYS.getErrorString(),
                    Toast.LENGTH_LONG).show(); return
        }
        val server: ServerSettingsStore = ServerSettingsStore(new_id, "No name")
        ServerCfg.write(serverPreferences!!, server)
        createViews()
        startEdit(server)
    }

    private fun startEdit(serverSettingsStore: ServerSettingsStore) {
        val intent = Intent(applicationContext, EditActivity::class.java)
        intent.putExtra("in", serverSettingsStore)
        startActivityForResult(intent, Defaults.RequestCode.EDIT_SERVER)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            Defaults.RequestCode.EDIT_SERVER -> if (resultCode == RESULT_OK && data != null) {
                val serverSettingsStore = data.getParcelableExtra<ServerSettingsStore>("out")
                ServerCfg.write(serverPreferences!!, serverSettingsStore)
                createViews()
            } else if (resultCode == RESULT_DELETE && data != null) {
                val serverSettingsStore = data.getParcelableExtra<ServerSettingsStore>("out")
                ServerCfg.delete(serverPreferences!!, serverSettingsStore.id)
                createViews()
            }
        }
    }

    inner class EditOCL internal constructor(private val id: String) : View.OnClickListener {

        /**
         * Called when a view has been clicked.

         * @param v The view that was clicked.
         */
        override fun onClick(v: View) {
            val serverSettingsStore = ServerCfg.read(serverPreferences!!, Defaults.ServerVariable(applicationContext), id)
            startEdit(serverSettingsStore)
        }

        fun getId(): String = id
    }
}
