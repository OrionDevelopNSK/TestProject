package com.example.cfttest2024.data.repo

import com.example.cfttest2024.data.dao.RoomDao
import com.example.cfttest2024.data.entity.RootEntity

class RoomRepository(private val roomDao: RoomDao) {
    fun insertInfoAndUserInfo(rootEntity: RootEntity){
        roomDao.insertInfoAndUserInfo(rootEntity)
    }

    fun getInfoAndUserInfo() : MutableList<RootEntity>{
        return roomDao.getInfoAndUserInfo()
    }

    fun clearDatabase(){
        roomDao.clearDatabase()
    }
}