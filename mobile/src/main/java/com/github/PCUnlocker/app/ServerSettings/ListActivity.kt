package com.github.PCUnlocker.app.ServerSettings

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.ArrayMap
import android.util.Log
import android.view.View
import android.widget.Toast
import com.github.PCUnlocker.app.R
import com.github.PCUnlocker.app.Tools.generateString
import com.github.PCUnlocker.app.helper.*
import com.github.PCUnlocker.app.helper.Defaults.ResultCode.RESULT_DELETE
import kotlinx.android.synthetic.main.activity_server_settings.*
import kotlinx.android.synthetic.main.content_server_settings.*
import java.security.SecureRandom
import java.util.*


class ListActivity : AppCompatActivity() {

    private var adapter: RecyclerView.Adapter<*>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var serverPreferences: SharedPreferences? = null
    private val random: Random = Random(SecureRandom().nextLong())

    public override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("dbg", "create...")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server_settings)
        setSupportActionBar(server_toolbar)
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
            val oclList: PairList<Pair<String, Pair<String, String>>, EditOCL> = PairList()
            list.forEach { id, na_de -> oclList.add(Pair(id, na_de), EditOCL(id)) }
            adapter = ListAdapter(oclList)
            ss_rv.adapter = adapter
            ss_rv.invalidate()
        }
        Log.d("dbg", "created Views")
    }

    @Suppress("UNUSED_PARAMETER")
    fun onFab(v: View) {
        val list: ArrayMap<String, Pair<String, String>> = ServerCfg.list(serverPreferences!!)
        val a_to_z: CharRange = CharRange('A', 'Z')
        var new_id: String = generateString(random, a_to_z, 8)
        var trys: Int = 0
        test@ while (new_id in list.keys) {
            new_id = generateString(random, a_to_z, 8)
            trys++
            if (trys > 50) {
                Toast.makeText(applicationContext, Errors.RANDOM_OVER_50_TRYS.getErrorString(), Toast.LENGTH_LONG).show()
                return@onFab
            } else {
                continue@test
            }
        }
        val server: ServerSettingsStore = ServerSettingsStore(new_id, "No name")
        ServerCfg.write(serverPreferences!!, server)
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
        createViews()
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
