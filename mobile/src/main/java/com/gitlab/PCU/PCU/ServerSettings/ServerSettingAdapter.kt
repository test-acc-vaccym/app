package com.gitlab.PCU.PCU.ServerSettings

import android.support.v7.widget.RecyclerView
import android.util.ArrayMap
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by tim on 26.03.17.
 */
class ServerSettingAdapter(p_dataset: ArrayMap<String, Array<String>>) : RecyclerView.Adapter<ServerSettingAdapter.ViewHolder>() {
    private val dataset: ArrayMap<String, Array<String>> = p_dataset

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder(var textView: TextView) : RecyclerView.ViewHolder(textView)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // create a new view
        val v: TextView = TextView(parent.context)
        // set the view's size, margins, paddings and layout parameters
        val vh: ViewHolder = ViewHolder(v)
        return vh
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.text = dataset.keyAt(position)

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return dataset.size
    }
}
