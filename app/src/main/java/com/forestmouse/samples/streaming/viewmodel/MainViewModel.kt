package com.forestmouse.samples.streaming.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.forestmouse.samples.streaming.dataModel.CommentModel
import com.forestmouse.samples.streaming.dataModel.PhotoModel
import com.forestmouse.samples.streaming.dataModel.PostModel
import com.forestmouse.samples.streaming.dataModel.PostsAndPhotos
import com.forestmouse.samples.streaming.extensions.setOrPost
import com.forestmouse.samples.streaming.repository.Repository
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class MainViewModel @Inject constructor() :
        ViewModel(), CoroutineScope {

    @Inject
    lateinit var repository: Repository

    private val job = SupervisorJob()
    private val TIMEOUT_LIMIT = 5000L
    private val COMMENTS_NUMBER = 3

    @Inject
    lateinit var context: Context

    override val coroutineContext: CoroutineContext = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    val currentScrollingPositionLiveData = MutableLiveData<Int>()

    fun getScrollingPosition(pos: Int) {
        currentScrollingPositionLiveData.setOrPost(pos)
    }

    private val postListLiveData = MutableLiveData<MutableList<PostModel>>()
    private val photoListLiveData = MutableLiveData<MutableList<PhotoModel>>()
    val postAndPhotoListLiveData = MutableLiveData<PostsAndPhotos>()
    val isLoadingPostsAndPhotos = MutableLiveData<Boolean>().apply { value = true }
    val isTimeoutPostsAndPhotos = MutableLiveData<Boolean>().apply { value = false }

    fun getPostsAndPhotos() {
        isTimeoutPostsAndPhotos.setOrPost(false)
        launch {
            withContext(Dispatchers.IO) {
                val latestPosts = repository.getPostList()
                val latestPhotos = repository.getPhotoList()
                if (!latestPosts.isNullOrEmpty() && !latestPhotos.isNullOrEmpty()) {
                    isLoadingPostsAndPhotos.setOrPost(false)

                    postListLiveData.setOrPost(latestPosts)
                    photoListLiveData.setOrPost(latestPhotos)

                    postAndPhotoListLiveData.setOrPost(PostsAndPhotos(latestPosts, latestPhotos))
                } else {
                    Log.e("Empty post or Photo", "No contents about this post or photo!")

                    delay(TIMEOUT_LIMIT)
                    isLoadingPostsAndPhotos.setOrPost(false)
                    isTimeoutPostsAndPhotos.setOrPost(true)
                }
            }
        }
    }

    var selectedPostIdLiveData = MutableLiveData<Int?>()

    private val _commentsLiveData = MutableLiveData<MutableList<CommentModel>>()
    val commentsLiveData = _commentsLiveData //TODO check if the _commentsLiveData is needed

    val isLoadingComments = MutableLiveData<Boolean>().apply { value = true }
    val isTimeoutComments = MutableLiveData<Boolean>().apply { value = false }

    fun getComments(postId: Int) {
        isTimeoutComments.setOrPost(false)
        launch {
            withContext(Dispatchers.IO) {
                val latestComments = repository.getCommentList(postId)

                if (!latestComments.isNullOrEmpty()) {
                    isLoadingComments.setOrPost(false)
                    _commentsLiveData.setOrPost(latestComments.take(COMMENTS_NUMBER).toMutableList())
                } else {
                    Log.i("Empty COMMENTS", "No comments about this post!")

                    delay(TIMEOUT_LIMIT)
                    isLoadingComments.setOrPost(false)
                    isTimeoutComments.setOrPost(true)
                }
            }
        }
    }
}
