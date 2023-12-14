package com.example.applicationl_tech.Model

data class Posts(
    val id : String,
    val title : String,
    val text : String,
    val image : String,
    val sort : Int,
    val date : String,
    var imageSource : ByteArray?,
)
