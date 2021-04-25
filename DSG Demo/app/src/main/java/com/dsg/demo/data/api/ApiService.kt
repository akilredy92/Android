package com.dsg.demo.data.api

import com.dsg.demo.data.model.EventList
import com.dsg.demo.data.model.User
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("events?client_id=MjE3NzcxMjF8MTYxOTIxNDg5Ny4wMjgwNjA3")
    suspend fun getEvents(@Query("q")search: String = ""): EventList
}