package com.example.cfttest2024.data.entity

import androidx.room.Embedded
import androidx.room.Entity


@Entity(tableName = "root")
data class RootEntity(
    val results: List<UserEntity>,
    @Embedded
    val info: InfoEntity
)