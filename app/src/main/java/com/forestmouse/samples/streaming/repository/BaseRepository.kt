package com.forestmouse.samples.streaming.repository

import android.util.Log
import com.forestmouse.samples.streaming.api.ApiResult
import retrofit2.Response
import java.io.IOException

open class BaseRepository {
    suspend fun <T : Any>  safeApiResult(call : suspend()-> Response<T>, error : String) :  T?{
        val result = feedsApiOutput(call, error)
        var output : T? = null
        when(result){
            is ApiResult.Success ->
                output = result.output
            is ApiResult.Error -> Log.e("BaseRepository: ", "error: $error   Exception: ${result.exception}")
        }
        return output
    }

    private suspend fun<T : Any> feedsApiOutput(call: suspend()-> Response<T>, error: String) : ApiResult<T>{
        val response = call.invoke()
        return if (response.isSuccessful)
            ApiResult.Success(response.body()!!)
        else
            ApiResult.Error(IOException("BaseRepository: Something went wrong due to error: $error"))
    }
}
