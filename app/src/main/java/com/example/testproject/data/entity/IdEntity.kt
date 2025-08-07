package com.example.testproject.data.entity

import androidx.room.ColumnInfo

data class IdEntity(
    @ColumnInfo(name = "id_entity_name")
    val name: String,
    val value: String
)
