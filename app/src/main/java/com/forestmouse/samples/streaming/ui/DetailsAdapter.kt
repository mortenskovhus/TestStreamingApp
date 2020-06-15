package com.forestmouse.samples.streaming.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.forestmouse.samples.streaming.R
import com.forestmouse.samples.streaming.dataModel.CommentModel
import kotlinx.android.synthetic.main.comment_view.view.*

class DetailsAdapter (
        private val commentList: MutableList<CommentModel>
) : RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder>() {

    class DetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.comment_view, parent, false)
        return DetailsViewHolder(view)
    }

    override fun getItemCount(): Int = commentList.size

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        val comment = commentList[position]
        holder.itemView.apply {
            holder.itemView.commentTitleText.text = comment.name
            holder.itemView.commentBodyText.text = comment.body
        }
    }
}