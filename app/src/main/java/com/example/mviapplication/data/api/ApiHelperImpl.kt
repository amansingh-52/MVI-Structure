package com.example.mviapplication.data.api

import com.example.mviapplication.data.model.User

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {
    override suspend fun getUsers(): List<User> = apiService.getUsers()
}