package com.example.cfttest2024.retrofit

import com.example.cfttest2024.model.WrapperResults
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url


private const val BASE_URL = "https://randomuser.me/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface WrapperResultsApiService {
    @GET
    fun getUser(@Url url: String): Call<WrapperResults>
}

object WrapperResultsApi{
    val retrofitService: WrapperResultsApiService by lazy {
        retrofit.create(WrapperResultsApiService::class.java)
    }
}