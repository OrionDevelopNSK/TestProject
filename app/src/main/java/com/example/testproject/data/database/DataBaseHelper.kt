package com.example.testproject.data.database

import com.example.testproject.data.entity.RootEntity
import com.example.testproject.data.repo.RoomRepository

class DataBaseHelper(
    dataBase: AppDataBase
) {
    private val roomRepo = RoomRepository(dataBase.roomDao())

    fun saveInfoAndUserInfo(rootEntity: RootEntity){
        roomRepo.insertInfoAndUserInfo(rootEntity)
    }

    fun loadInfoAndUserInfo(): List<RootEntity> = roomRepo.getInfoAndUserInfo()

    fun clearDatabase(){
        roomRepo.clearDatabase()
    }
}