package com.example.testproject.logic.interfaces

import com.example.testproject.data.entity.RootEntity

interface IResultDownloader {
    suspend fun downloadResults(action: suspend (RootEntity) -> Unit, errorHandler: (Int) -> Unit)
}