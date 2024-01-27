package com.example.cfttest2024.data.database

import android.content.Context
import androidx.room.Room

class RoomDatabaseBuilder {
    fun getAppDatabase(context: Context): AppDataBase {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "info_and_info_users"
        ).build()
    }
}

