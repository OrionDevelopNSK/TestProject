package com.example.cfttest2024.data.entity

import androidx.room.PrimaryKey

data class InfoEntity(
    val seed: String,
    val results: Long,
    val page: Long,
    val version: String
)
