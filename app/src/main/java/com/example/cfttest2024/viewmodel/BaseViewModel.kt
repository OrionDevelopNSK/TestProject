package com.example.cfttest2024.viewmodel;

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
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
    private var _rootData: MutableLiveData<Root> = MutableLiveData()
    val rootData: LiveData<Root> = _rootData

    val rootEntities: MutableList<RootEntity> = mutableListOf()


    private lateinit var dataBaseHelper : DataBaseHelper


    open fun createDataBaseHelper(context: Context){
        val roomDatabaseBuilder = RoomDatabaseBuilder().getAppDatabase(context)
        dataBaseHelper = DataBaseHelper(roomDatabaseBuilder)
    }



    open fun loadResults() {
        viewModelScope.launch {
            ResultsApi.retrofitService.getResult().enqueue(object : Callback<Root> {
                @SuppressLint("NullSafeMutableLiveData")
                override fun onResponse(call: Call<Root>, response: Response<Root>) {
                    val root = response.body()
                    if (root != null){
                        _rootData.value = root
                        val objectToEntity = Converter.objectToEntity(root)
                        rootEntities.add(objectToEntity)
                        saveInfo()
                    }


//                    response.body()!!.results.forEach { Log.e("", it.email) }

                }

                override fun onFailure(call: Call<Root>, t: Throwable) {

                }
            })

        }


    }

    fun saveInfo(){
        dataBaseHelper.saveInfoAndUserInfo(rootEntities)
    }

    fun loadInfo(){

    }

}