package com.example.cfttest2024.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cfttest2024.data.database.DataBaseHelper
import com.example.cfttest2024.data.database.RoomDatabaseBuilder
import com.example.cfttest2024.data.entity.RootEntity
import com.example.cfttest2024.data.util.Converter
import com.example.cfttest2024.model.Root
import com.example.cfttest2024.retrofit.ResultsApi
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BaseViewModel : ViewModel() {
    private var _rootData: MutableLiveData<List<Root>> = MutableLiveData()
    val rootData: LiveData<List<Root>> = _rootData

    private var _currentIndexUser: MutableLiveData<Int> = MutableLiveData(0)
    val currentIndexUser: LiveData<Int> = _currentIndexUser

    val rootEntities: MutableList<RootEntity> = mutableListOf()


    private lateinit var dataBaseHelper : DataBaseHelper


    fun createDataBaseHelper(context: Context){
        val roomDatabaseBuilder = RoomDatabaseBuilder().getAppDatabase(context)
        dataBaseHelper = DataBaseHelper(roomDatabaseBuilder)
    }

    fun downloadResults() {
        viewModelScope.launch {
            ResultsApi.retrofitService.getResult().enqueue(object : Callback<Root> {
                @SuppressLint("NullSafeMutableLiveData")
                override fun onResponse(call: Call<Root>, response: Response<Root>) {
                    val root = response.body()
                    if (root != null){
                        val objectToEntity = Converter.objectToEntity(root)
                        rootEntities.add(objectToEntity)
                        saveInfoToDatabase()
                        loadInfoFromDatabase()
                    }
                }

                override fun onFailure(call: Call<Root>, t: Throwable) {

                }
            })
        }


    }

    fun saveInfoToDatabase(){
        dataBaseHelper.saveInfoAndUserInfo(rootEntities)
    }

    fun loadInfoFromDatabase(){
        val tmp = dataBaseHelper.loadInfoAndUserInfo()
        _rootData.value = Converter.entitiesToObjects(tmp).reversed()
    }

    fun setIndexUser(index: Int){
        _currentIndexUser.value = index
    }

}