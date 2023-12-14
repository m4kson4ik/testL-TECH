package com.example.applicationl_tech.Room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LTECHUserDB::class], version = 1)
abstract class LTECHUserDateBase : RoomDatabase() {
    abstract fun getUserDao() : LTECHUserDAO
}