package com.example.applicationl_tech.Interface

import com.example.applicationl_tech.Model.AuthResponse
import com.example.applicationl_tech.Model.PhoneMask
import com.example.applicationl_tech.Model.Posts
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PostsService {
    @GET("api/v1/phone_masks")
    fun getPhoneMask(
    ) : Call<PhoneMask>


    @FormUrlEncoded
    @POST("api/v1/auth")
    fun getPhone(
        @Field("phone") phone: String,
        @Field("password") password: String
    ) : Call<AuthResponse>


    @GET("api/v1/posts")
    fun getPosts() : Call<List<Posts>>

    @GET("{imageLink}")
    fun getImagePosts(
        @Path("imageLink") imageLink : String
    ) : Call<ResponseBody>
}