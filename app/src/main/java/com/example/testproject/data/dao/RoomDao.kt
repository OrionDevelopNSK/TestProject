package com.example.testproject.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.testproject.data.entity.RootEntity


@Dao
abstract class RoomDao {
    @Query("SELECT * FROM users_db")
    abstract fun load() : MutableList<RootEntity>

    @Insert
    abstract fun save (rootEntity: RootEntity)

    @Query("DELETE FROM users_db")
    abstract fun clearDatabase()
}