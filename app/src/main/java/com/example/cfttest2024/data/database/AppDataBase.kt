package com.example.cfttest2024.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cfttest2024.data.dao.RoomDao
import com.example.cfttest2024.data.entity.RootEntity


@Database(
    entities = [RootEntity::class], version = 1, exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun roomDao(): RoomDao
}