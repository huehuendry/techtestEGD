package com.hendry.testegd.service

import com.hendry.testegd.model.ResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET("qotd")
    fun getQuotes(): Call<ResponseModel>
}