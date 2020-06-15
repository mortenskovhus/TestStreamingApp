package com.forestmouse.samples.streaming.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.forestmouse.samples.streaming.R
import com.forestmouse.samples.streaming.dataModel.PhotoModel
import com.forestmouse.samples.streaming.dataModel.PostModel
import com.forestmouse.samples.streaming.dataModel.PostsAndPhotos
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.post_item.view.*

class PostAdapter(
        private var postAndPhotoList: PostsAndPhotos,
        private val clickListener: (PostModel,PhotoModel,Int) -> Unit
) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    companion object {
        private const val TAG = "PostAdapterNew"
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.post_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = postAndPhotoList.posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val postItem = postAndPhotoList.posts[position]
        val photoItem = postAndPhotoList.photos[position]
        holder.itemView.apply {
            holder.itemView.title.text = postItem.title
            holder.itemView.body.text = postItem.body
            setOnClickListener { clickListener(postItem,photoItem, position) }
        }

        //val photoIndex = Random.nextInt(postAndPhotoList.photos.size - 1) //No idea why a random image is picked here
        val photoUrl = postAndPhotoList.photos[position].thumbnailUrl
        Log.d(TAG, "position: $position      photoUrl = $photoUrl")
        /*Glide.with(context) //Having trouble making glide work. Using Picasso, which works using their newest version but not the originally used version used here
                .load(photoUrl)    // photo can't be displayed although photoUrl matches with source data
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .fitCenter()
                .centerCrop()
                .into(holder.itemView.image)*/

        Picasso.get()
                .load(photoUrl)
                .into(holder.itemView.image)
    }
}