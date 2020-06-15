package com.forestmouse.samples.streaming.repository

import android.util.Log
import com.forestmouse.samples.streaming.api.AppApi
import com.forestmouse.samples.streaming.dataModel.CommentModel
import com.forestmouse.samples.streaming.dataModel.PhotoModel
import com.forestmouse.samples.streaming.dataModel.PostModel
import java.net.SocketTimeoutException
import javax.inject.Inject

class Repository @Inject constructor(
        private val appApi: AppApi
) {



    suspend fun getPostList(): MutableList<PostModel>? {
        val postListRepository = PostListRepository()
        return postListRepository.getPostList(appApi)
    }

    suspend fun getPhotoList(): MutableList<PhotoModel>? {
        val photoListRepository = PhotoListRepository()
        return photoListRepository.getPhotoList(appApi)
    }

    suspend fun getCommentList(postId: Int): MutableList<CommentModel>? {
        val commentListRepository = CommentListRepository()
        return commentListRepository.getCommentList(postId, appApi)
    }






}