package com.dsg.demo.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getUsers() = apiService.getUsers()
    suspend fun getEvents(search: String = "") = apiService.getEvents(search)
}