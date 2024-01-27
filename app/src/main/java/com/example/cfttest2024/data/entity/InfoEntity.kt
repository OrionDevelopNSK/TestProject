package com.example.cfttest2024.data.entity

import androidx.room.PrimaryKey

data class InfoEntity(
    @PrimaryKey(autoGenerate = true)
    val idInfoPrimaryKey: Int,
    val seed: String,
    val results: Long,
    val page: Long,
    val version: String
)
