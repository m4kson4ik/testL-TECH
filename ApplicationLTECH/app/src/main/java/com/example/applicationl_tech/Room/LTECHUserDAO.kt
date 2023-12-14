package com.example.applicationl_tech.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LTECHUserDAO {
    @Query("select * from LTECHUser")
    fun getAllFlow() : Flow<List<LTECHUserDB>>

    @Delete
    suspend fun deleted(item : LTECHUserDB)

    @Insert
    suspend fun insert(item: LTECHUserDB)
}