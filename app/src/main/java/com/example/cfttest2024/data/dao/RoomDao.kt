package com.example.cfttest2024.data.dao

import androidx.room.Dao
import androidx.room.Query


@Dao
abstract class RoomDao {
    @Query("SELECT * FROM root")
    abstract fun getRoot()
}