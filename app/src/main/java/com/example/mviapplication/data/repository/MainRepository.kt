package com.example.mviapplication.data.repository

import com.example.mviapplication.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers()

}
