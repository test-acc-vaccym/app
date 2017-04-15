package com.github.PCU.android.ServerSettings

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.github.PCU.android.ServerSettings.ListActivity.EditOCL
import com.github.PCU.android.helper.PairList

/**
 * Created by tim on 26.03.17.
 */
class ListAdapter(private val dataset: PairList<Pair<String, Pair<String, String>>, EditOCL>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

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
        holder.v.nameView.text = dataset.firstAt(position).second.first
        holder.v.descView.text = dataset.firstAt(position).second.second
        holder.v.setEditOCL(dataset.secondAt(position))
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return dataset.size
    }
}
