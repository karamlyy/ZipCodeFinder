package com.example.zipcodefounder.network

import com.example.zipcodefounder.Constant
import com.example.zipcodefounder.api.ZipCodeApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object{
        private val retrofit : Retrofit = Retrofit
            .Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val zipApi: ZipCodeApi get() = retrofit.create(ZipCodeApi::class.java)
    }

}