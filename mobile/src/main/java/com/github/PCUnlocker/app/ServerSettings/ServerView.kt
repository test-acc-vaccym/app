package com.github.PCU.android.ServerSettings

import android.content.Context
import android.support.annotation.ColorInt
import android.support.annotation.Nullable
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.github.PCU.android.R


/**
 * Created by tim on 28.03.17.
 */
class ServerView(appContext: Context) : LinearLayout(appContext) {
    var nameView: TextView = TextView(appContext)
    var descView: TextView = TextView(appContext)
    var id: String? = null
    private var editOCL: ListActivity.EditOCL? = null

    init {
        orientation = VERTICAL
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        addView(nameView)
        addView(descView)
        addView(HorizontalLine(context, R.color.colorAccent))
    }

    fun setEditOCL(value: ListActivity.EditOCL) {
        editOCL = value
        id = value.getId()
        this.setOnClickListener(value)
    }

    class HorizontalLine : TextView {
        constructor(context: Context, @Nullable attrs: AttributeSet, defStyleAttr: Int, @ColorInt color: Int) : super(context, attrs, defStyleAttr) {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1)
            setBackgroundColor(color)
        }

        constructor(context: Context, @Nullable attrs: AttributeSet, @ColorInt color: Int) : super(context, attrs) {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1)
            setBackgroundColor(color)
        }

        constructor(context: Context, @ColorInt color: Int) : super(context) {
            layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1)
            setBackgroundColor(color)
        }
    }
}
