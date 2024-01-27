package com.example.cfttest2024.data.entity

import androidx.room.ColumnInfo

data class RegisteredEntity(
    @ColumnInfo(name = "registered_entity_date")
    val date: String,
    @ColumnInfo(name = "registered_entity_age")
    val age: Long
)
