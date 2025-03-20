package com.sathvik.fetchtestapp.netowork

import com.sathvik.fetchtestapp.netowork.models.DataItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("hiring.json")
    suspend fun getData(): Response<List<DataItem>>

}