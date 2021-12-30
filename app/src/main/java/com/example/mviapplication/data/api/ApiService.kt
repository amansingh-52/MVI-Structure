package com.example.mviapplication.data.api

import com.example.mviapplication.data.model.User
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers() : List<User>

}