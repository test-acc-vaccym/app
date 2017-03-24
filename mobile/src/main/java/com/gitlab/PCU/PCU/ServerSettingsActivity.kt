package com.gitlab.PCU.PCU

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import android.widget.TextView
import com.gitlab.PCU.PCU.R.layout.activity_server_settings
import com.gitlab.PCU.PCU.helper.Defaults
import com.gitlab.PCU.PCU.helper.Defaults.ResultCode.RESULT_DELETE
import com.gitlab.PCU.PCU.helper.ServerCfg
import com.gitlab.PCU.PCU.helper.ServerSettingsStore
import kotlinx.android.synthetic.main.activity_main_screen.*
import java.util.*


class ServerSettingsActivity : AppCompatActivity() {

    private var childs = ArrayList<View>()
    private val adapter: RecyclerView.Adapter<*>? = null
    private val layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_server_settings)
        try {
            createViews()
        } catch (ignored: NullPointerException) {
        }

        setSupportActionBar(toolbar)
        try {

            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        } catch (e: NullPointerException) {
            print("NPS @ getSupportActionBar().setDisplayHomeAsUpEnabled(true)")
        }

    }

    private fun createViews() {
        for (child in childs) {
            (child.parent as ViewGroup).removeView(child)
        }

        childs = ArrayList<View>()

        val list = ServerCfg.list(getSharedPreferences("servers", 0))

        val context = applicationContext

        for ((id, value) in list) {
            val name = TextView(context)
            name.text = value[0]
            val info = TextView(context)
            info.text = value[1]
            addServerView(name, info, content_server_settings_ll, id)
        }
    }

    private fun addServerView(name: TextView, info: TextView, linearLayout: LinearLayout, serverId: String) {
        if (linearLayout.childCount > 0) {
            val spacer = View(applicationContext)
            spacer.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, 2)
            spacer.setBackgroundColor(resources.getColor(R.color.colorLightGray, theme))
            linearLayout.addView(spacer)
            childs.add(spacer)
        }

        name.textSize = 18f
        info.textSize = 14f

        info.setTextColor(resources.getColor(R.color.colorLightGray, theme))

        val editOCL = EditOCL(serverId)

        name.setOnClickListener(editOCL)
        info.setOnClickListener(editOCL)

        linearLayout.addView(name)
        childs.add(name)
        linearLayout.addView(info)
        childs.add(info)
    }

    fun onFab(v: View) {
        val context = applicationContext
        val arrayMap = ServerCfg.list(getSharedPreferences("servers", 0))
        var i = arrayMap.size
        while (arrayMap.containsKey("Server" + Integer.toString(i))) {
            i++
        }
        val id = "Server" + Integer.toString(i)
        val serverSettingsStore = Defaults.ServerVariable(context).SERVER_SETTINGS_STORE
        ServerCfg.write(getSharedPreferences("servers", 0), id, serverSettingsStore)
        val name = TextView(context)
        name.text = serverSettingsStore.getName()
        val info = TextView(context)
        info.text = serverSettingsStore.getDesc()
        addServerView(name, info, findViewById(R.id.content_server_settings_ll) as LinearLayout, id)
    }

    private fun startEdit(serverSettingsStore: ServerSettingsStore, serverId: String) {
        val intent = Intent(applicationContext, SingleServerSettings::class.java)
        intent.putExtra("in", serverSettingsStore)
        intent.putExtra("id", serverId)
        startActivityForResult(intent, Defaults.RequestCode.EDIT_SERVER)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            Defaults.RequestCode.EDIT_SERVER -> if (resultCode == Activity.RESULT_OK && data != null) {
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
