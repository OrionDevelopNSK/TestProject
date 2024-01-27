package com.example.cfttest2024.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.cfttest2024.data.entity.RootEntity


@Dao
abstract class RoomDao {
    @Query("SELECT * FROM users_db")
    abstract fun getInfoAndUserInfo() : MutableList<RootEntity>

    @Insert
    abstract fun insertInfoAndUserInfo (rootEntity: MutableList<RootEntity>)
}