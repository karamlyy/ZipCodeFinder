package com.example.zipcodefounder.api

import com.example.zipcodefounder.model.DetailsDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ZipCodeApi {
        @GET("{zipcode}")
        suspend fun getRegionByZip(@Path("zipcode") zipcode: String): Response<DetailsDTO>
}
