package com.forestmouse.samples.streaming.repository

import android.util.Log
import com.forestmouse.samples.streaming.api.AppApi
import com.forestmouse.samples.streaming.dataModel.PostModel
import java.net.SocketTimeoutException

class PostListRepository() : BaseRepository() {

    companion object {
        private const val TAG = "PostListRepository"
    }

    suspend fun getPostList(appApi: AppApi): MutableList<PostModel>? {
        Log.d(TAG, "getPostList()")
        try {
            return safeApiResult(
                    call = {
                        appApi.getAllPosts().await()
                    },
                    error = "Error fetching posts"
            )
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "catched socketTimeoutException getting postList")
        }
        return null
    }
}