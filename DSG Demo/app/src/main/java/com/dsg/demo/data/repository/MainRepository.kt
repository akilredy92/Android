package com.dsg.demo.data.repository

import com.dsg.demo.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers()
    suspend fun getEvents(search: String = "") = apiHelper.getEvents(search)
}