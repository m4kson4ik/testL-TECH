package com.example.applicationl_tech.ViewModel

import androidx.lifecycle.ViewModel
import com.example.applicationl_tech.Model.PhoneMask
import com.example.applicationl_tech.Model.Posts
import com.example.applicationl_tech.Model.Status
import com.example.applicationl_tech.Repository.Repository
import com.example.applicationl_tech.Room.LTECHUserDB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor (private val repository: Repository) : ViewModel() {
    var statusInternet = MutableStateFlow(Status.None)

    var selectedPost = MutableStateFlow(Posts("", "", "", "", 1, "", null))

    var postsFlow = MutableStateFlow(emptyList<Posts>())

    var maskPhoneFlow = MutableStateFlow(PhoneMask(""))
    private val scope = CoroutineScope(Dispatchers.Default)

    fun autorizationUser()
    {
        repository.getPhone("79005868675", "devExam18")
    }

    fun getPosts()
    {
        val items = repository.getPosts()
        scope.launch {
            items.collect {
                val item = it.second
                if (item != null) {
                    postsFlow.emit(it.second.orEmpty())
                }
            }
        }
    }

    suspend fun insertUser(item : LTECHUserDB)
    {
        repository.insertUser(item)
    }

    suspend fun deletedUser(item : LTECHUserDB)
    {
        repository.deletedUser(item)
    }

    fun sortedPostsServer()
    {
         postsFlow.value = postsFlow.value.sortedBy { it.sort }
    }

    fun sortedPostsDate()
    {
        postsFlow.value = postsFlow.value.sortedBy { it.date }
    }

    fun getMaskPhone()
    {
        val flow = repository.getPhoneMask()
        scope.launch {
            flow.collect {
                statusInternet.emit(it.first)
                val item = it.second
                if (item != null )
                {
                    maskPhoneFlow.emit(item)
                }
            }
        }
    }
}