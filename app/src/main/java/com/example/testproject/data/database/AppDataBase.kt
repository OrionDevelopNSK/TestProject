package com.example.testproject.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testproject.data.dao.RoomDao
import com.example.testproject.data.entity.RootEntity


@Database(
    entities = [RootEntity::class], version = 1, exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun roomDao(): RoomDao
}