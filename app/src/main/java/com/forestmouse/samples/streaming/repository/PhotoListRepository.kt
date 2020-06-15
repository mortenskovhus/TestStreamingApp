package com.forestmouse.samples.streaming.repository

import android.util.Log
import com.forestmouse.samples.streaming.api.AppApi
import com.forestmouse.samples.streaming.dataModel.PhotoModel
import java.net.SocketTimeoutException

class PhotoListRepository() : BaseRepository() {

    companion object {
        private const val TAG = "PhotoListRepository"
    }

    suspend fun getPhotoList(appApi: AppApi): MutableList<PhotoModel>? {
        Log.d(TAG, "getPhotoList()")
        try {
            return safeApiResult(
                    call = {
                        appApi.getAllPhotos().await()
                    },
                    error = "Error fetching photos"
            )
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "catched socketTimeoutException getting photoList")
        }
        return null
    }
}