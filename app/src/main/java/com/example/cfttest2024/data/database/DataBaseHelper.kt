package com.example.cfttest2024.data.database

import com.example.cfttest2024.data.entity.RootEntity
import com.example.cfttest2024.data.repo.RoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DataBaseHelper(
    dataBase: AppDataBase
) {
    private val roomRepo = RoomRepository(dataBase.roomDao())

    fun saveInfoAndUserInfo(rootEntities: MutableList<RootEntity>){
        runBlocking {
            launch(Dispatchers.IO) { roomRepo.insertInfoAndUserInfo(rootEntities) }
        }
    }

    fun loadInfoAndUserInfo(): List<RootEntity>{
        var tmp= mutableListOf<RootEntity>()
        runBlocking {
            val job = async (Dispatchers.IO){
                tmp = roomRepo.getInfoAndUserInfo()
            }
            job.await()
        }
        return tmp
    }


}