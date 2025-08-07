package com.example.testproject.data.repo

import com.example.testproject.data.dao.RoomDao
import com.example.testproject.data.entity.RootEntity

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