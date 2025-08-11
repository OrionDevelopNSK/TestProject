package com.example.testproject.logic.repo

import com.example.testproject.data.dao.RoomDao
import com.example.testproject.data.entity.RootEntity
import com.example.testproject.logic.interfaces.IDatabaseManager

class DatabaseManager(private val roomDao: RoomDao) : IDatabaseManager{

    override fun load() : MutableList<RootEntity>{
        return roomDao.load()
    }

    override fun save(rootEntity: RootEntity){
        roomDao.save(rootEntity)
    }

    override fun clear(){
        roomDao.clearDatabase()
    }
}