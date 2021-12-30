package com.example.mviapplication.data.api

import com.example.mviapplication.data.model.User

interface ApiHelper {

    suspend fun getUsers(): List<User>

}