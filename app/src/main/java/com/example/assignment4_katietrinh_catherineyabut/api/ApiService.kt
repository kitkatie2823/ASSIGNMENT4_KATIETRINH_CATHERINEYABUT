package com.example.assignment4_katietrinh_catherineyabut.api

import retrofit2.Call
import retrofit2.http.GET
import com.example.assignment4_katietrinh_catherineyabut.model.Todo

interface ApiService {
    @GET("todos") // Endpoint to fetch todos
    fun getTodos(): Call<List<Todo>>
}
