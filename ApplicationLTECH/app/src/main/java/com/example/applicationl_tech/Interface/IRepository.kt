package com.example.applicationl_tech.Interface

import com.example.applicationl_tech.Model.AuthResponse
import com.example.applicationl_tech.Model.PhoneMask
import com.example.applicationl_tech.Model.Posts
import com.example.applicationl_tech.Model.Status
import com.example.applicationl_tech.Room.LTECHUserDB
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface IRepository {
    fun getPhoneMask() : StateFlow<Pair<Status,PhoneMask?>>
    fun getPhone(phone: String, password: String) : StateFlow<Pair<Status,AuthResponse?>>
    fun getPosts() : MutableStateFlow<Pair<Status, List<Posts>?>>

    fun getAllFlowUser() : Flow<List<LTECHUserDB>>
    suspend fun insertUser(item : LTECHUserDB)
    suspend fun deletedUser(item : LTECHUserDB)
}