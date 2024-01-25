package com.example.cfttest2024.retrofit

import com.example.cfttest2024.model.Root
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "https://randomuser.me/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ResultsApiService {
    @GET(BASE_URL)
    fun getResult(): Call<Root>
}

object ResultsApi{
    val retrofitService: ResultsApiService by lazy {
        retrofit.create(ResultsApiService::class.java)
    }
}