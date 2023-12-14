package com.example.applicationl_tech.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LTECHUser")
data class LTECHUserDB (
    @ColumnInfo("userPhone") val userPhone : String,
    @ColumnInfo("password") val password : String
)
{
    @PrimaryKey(true) var uid : Int = 0
}