package com.forestmouse.samples.streaming.repository

import android.util.Log
import com.forestmouse.samples.streaming.api.AppApi
import com.forestmouse.samples.streaming.dataModel.CommentModel
import java.net.SocketTimeoutException

class CommentListRepository() : BaseRepository() {

    companion object {
        private const val TAG = "CommentListRepository"
    }

    suspend fun getCommentList(postId: Int, appApi: AppApi): MutableList<CommentModel>? {
        Log.d(TAG, "getCommentList()")
        try {
            return safeApiResult(
                    call = {
                        appApi.getThreeComments(id = postId).await()
                    },
                    error = "Error fetching comments"
            )
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "catched socketTimeoutException getting commentList")
        }
        return null
    }
}