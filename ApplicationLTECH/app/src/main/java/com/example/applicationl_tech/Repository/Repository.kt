package com.example.applicationl_tech.Repository

import android.annotation.SuppressLint
import android.util.Log
import com.example.applicationl_tech.Interface.PostsService
import com.example.applicationl_tech.Interface.IRepository
import com.example.applicationl_tech.Model.AuthResponse
import com.example.applicationl_tech.Model.PhoneMask
import com.example.applicationl_tech.Model.Posts
import com.example.applicationl_tech.Model.Status
import com.example.applicationl_tech.Room.LTECHUserDB
import com.example.applicationl_tech.Room.LTECHUserDateBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class Repository @Inject constructor(db : LTECHUserDateBase) : IRepository {
    private val userDao = db.getUserDao()

    private val scope = CoroutineScope(Dispatchers.IO)

    private val retrofit = Retrofit.Builder().baseUrl(BASE_URl).addConverterFactory(
        GsonConverterFactory.create()
    ).build()

    private val authService = retrofit.create(PostsService::class.java)

    companion object {
        const val BASE_URl = "http://dev-exam.l-tech.ru/"
    }

    @SuppressLint("SuspiciousIndentation")
    override fun getPhoneMask(): MutableStateFlow<Pair<Status, PhoneMask?>> {
        val result = authService.getPhoneMask()
        val stateFlow = MutableStateFlow<Pair<Status, PhoneMask?>>(Status.Waiting to null)
            scope.launch {
                try {
                val res = result.execute()
                if (res.isSuccessful) {
                    val body = res.body()
                    if (body == null) {
                        stateFlow.emit(Status.Error to null)
                    } else {
                        stateFlow.emit(Status.OK to body)
                    }
                } else {
                    stateFlow.emit(Status.Error to null)
                }
                }
                catch (e: Exception)
                {
                    stateFlow.emit(Status.Error to null)
                }
            }
        return stateFlow
    }

    override fun getPhone(phone: String, password: String): StateFlow<Pair<Status, AuthResponse?>> {
        val result = authService.getPhone("79005868675", "devExam18")
        val stateFlow = MutableStateFlow<Pair<Status, AuthResponse?>>(Status.Waiting to null)
        scope.launch {
            try {
                val res = result.execute()
                if (res.isSuccessful) {
                    val body = res.body()
                    if (body == null) {
                        stateFlow.emit(Status.Error to null)
                    } else {
                        stateFlow.emit(Status.OK to body)
                    }
                } else {
                    stateFlow.emit(Status.Error to null)
                }
            }
            catch (e: Exception)
            {
                stateFlow.emit(Status.Error to null)
            }
        }
        return stateFlow
    }

    override fun getPosts(): MutableStateFlow<Pair<Status, List<Posts>?>> {
        val result = authService.getPosts()
        val stateFlow = MutableStateFlow<Pair<Status, List<Posts>?>>(Status.Waiting to null)
        scope.launch {
            try {
                val res = result.execute()
                if (res.isSuccessful) {
                    val body = res.body()
                    if (body == null) {
                        stateFlow.emit(Status.Error to null)
                    } else {
                        body.map {
                            it.imageSource =
                                authService.getImagePosts(it.image).execute().body()?.bytes()
                        }
                        stateFlow.emit(Status.OK to body)
                    }
                } else {
                    stateFlow.emit(Status.Error to null)
                }
            }
            catch (e : Exception)
            {
                stateFlow.emit(Status.Error to null)
            }
        }
        return stateFlow
    }

    override fun getAllFlowUser(): Flow<List<LTECHUserDB>> = userDao.getAllFlow()

    override suspend fun insertUser(item: LTECHUserDB) {
        userDao.insert(item)
    }

    override suspend fun deletedUser(item: LTECHUserDB) {
        userDao.deleted(item)
    }
}