package com.gitlab.PCU.PCU.ServerSettings

import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Created by tim on 28.03.17.
 */
class ServerView(val appContext: Context) : LinearLayout(appContext) {
    var nameView: TextView = TextView(appContext)
        get() = nameView
    var descView: TextView = TextView(appContext)
        get() = descView
    var id: String? = null
    var editOCL: ListActivity.EditOCL? = null
        set(value: ListActivity.EditOCL?) {
            if (value != null) {
                editOCL = value
                id = value.getId()
            }
        }
}
