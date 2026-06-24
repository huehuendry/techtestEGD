package com.hendry.testegd.service

import com.hendry.testegd.model.ResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiCall {

    fun getQuotes(
        onSuccess: (ResponseModel) -> Unit,
        onError: (String) -> Unit
    ) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://favqs.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: RetrofitService = retrofit.create(RetrofitService::class.java)

        val call: Call<ResponseModel> = service.getQuotes()

        call.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {
                if (response.isSuccessful) {
                    val quoteResponse = response.body()

                    if (quoteResponse != null) {
                        onSuccess(quoteResponse)
                    } else {
                        onError("Data quote kosong")
                    }
                } else {
                    onError("Gagal mengambil quote")
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                onError(t.message ?: "Request failed")
            }
        })
    }
}