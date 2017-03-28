package com.gitlab.PCU.PCU.ServerSettings

import android.support.v7.widget.RecyclerView
import android.util.ArrayMap
import android.view.ViewGroup

/**
 * Created by tim on 26.03.17.
 */
class ListAdapter(p_dataset: ArrayMap<String, Pair<String, String>>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    private val dataset: ArrayMap<String, Pair<String, String>> = p_dataset

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder(var v: ServerView) : RecyclerView.ViewHolder(v)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // create a new view
        val v: ServerView = ServerView(parent.context)
        // set the view's size, margins, paddings and layout parameters
        val vh: ViewHolder = ViewHolder(v)
        return vh
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataset_part: Pair<String, Pair<String, String>> = Pair(dataset.keyAt(position), dataset.valueAt(position))
        holder.v.nameView.text = dataset_part.second.first
        holder.v.descView.text = dataset_part.second.second
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return dataset.size
    }
}
