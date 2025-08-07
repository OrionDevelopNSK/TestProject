package com.example.testproject.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users_db")
data class RootEntity(
    @Embedded
    val results: UserEntity,
    @Embedded
    val info: InfoEntity,
    @PrimaryKey(autoGenerate = true)
    val rootEntityPrimaryKey: Int
)