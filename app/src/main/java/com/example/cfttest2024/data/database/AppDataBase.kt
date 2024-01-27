package com.example.cfttest2024.data.database

import androidx.room.RoomDatabase
import com.example.cfttest2024.data.dao.RoomDao

abstract class AppDataBase : RoomDatabase() {

    abstract fun roomDao() : RoomDao
}