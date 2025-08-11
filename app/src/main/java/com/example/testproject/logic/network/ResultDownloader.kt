package com.example.testproject.logic.network

import com.example.testproject.R
import com.example.testproject.data.entity.RootEntity
import com.example.testproject.util.Converter
import com.example.testproject.logic.interfaces.IResultDownloader
import com.example.testproject.retrofit.ResultsApiService
import retrofit2.awaitResponse

class ResultDownloader(
    private val apiService: ResultsApiService
) : IResultDownloader {

    override suspend fun downloadResults(
        action: suspend (RootEntity) -> Unit,
        errorHandler: (Int) -> Unit
    ) {
        try {
            val response = apiService.getResult().awaitResponse()
            if (response.isSuccessful) {
                response.body()?.let { root ->
                    val objectToEntity = Converter.objectToEntity(root)
                    action(objectToEntity)
                }
            } else {
                errorHandler(R.string.error_download)
            }
        } catch (_: Throwable) {
            errorHandler(R.string.error_download)
        }
    }
}