package com.example.testproject.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.R
import com.example.testproject.data.database.DataBaseHelper
import com.example.testproject.data.database.RoomDatabaseBuilder
import com.example.testproject.data.entity.RootEntity
import com.example.testproject.data.util.Converter
import com.example.testproject.model.Root
import com.example.testproject.retrofit.ResultsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BaseViewModel : ViewModel() {
    private var _rootData: MutableLiveData<List<Root>> = MutableLiveData()
    val rootData: LiveData<List<Root>> = _rootData

    private var _currentIndexUser: MutableLiveData<Int> = MutableLiveData(0)
    val currentIndexUser: LiveData<Int> = _currentIndexUser

    private val _errorEvent = MutableLiveData<Int?>(null)
    val errorEvent: LiveData<Int?> = _errorEvent

    private lateinit var dataBaseHelper: DataBaseHelper

    private var onCallPhone: ((String) -> Unit)? = null

    private var onOpenEmailProgram: ((String) -> Unit)? = null

    private var onOpenMap: ((String, String) -> Unit)? = null

    val googleMap = "http://maps.google.com/maps?q=loc:"


    fun setCallPhoneListener(listener: (String) -> Unit) {
        onCallPhone = listener
    }

    fun setOpenEmailProgram(listener: (String) -> Unit) {
        onOpenEmailProgram = listener
    }

    fun setOpenMap(listener: (String, String) -> Unit) {
        onOpenMap = listener
    }

    fun createDataBaseHelper(context: Context) {
        val roomDatabaseBuilder = RoomDatabaseBuilder().getAppDatabase(context)
        dataBaseHelper = DataBaseHelper(roomDatabaseBuilder)
    }

    fun clearDatabase() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dataBaseHelper.clearDatabase()
            }
        }
        _rootData.value = emptyList()
    }

    fun resetError() {
        _errorEvent.value = null
    }

    fun downloadResults() {
        ResultsApi.retrofitService.getResult().enqueue(object : Callback<Root> {
            override fun onResponse(call: Call<Root>, response: Response<Root>) {
                if (response.isSuccessful) {
                    response.body()?.let { root ->
                        viewModelScope.launch {
                            val objectToEntity = Converter.objectToEntity(root)
                            saveInfoToDatabase(objectToEntity)
                            loadInfoFromDatabase()
                        }
                    }
                } else {
                    _errorEvent.postValue(R.string.error_download)
                }
            }

            override fun onFailure(call: Call<Root>, t: Throwable) {
                _errorEvent.postValue(R.string.error_download)
            }
        })
    }

    fun saveInfoToDatabase(rootEntity: RootEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dataBaseHelper.saveInfoAndUserInfo(rootEntity)
            }
        }
    }

    fun loadInfoFromDatabase() {
        viewModelScope.launch {
            val tmp = withContext(Dispatchers.IO) {
                dataBaseHelper.loadInfoAndUserInfo()
            }

            if (tmp.isNotEmpty()) _rootData.value = Converter.entitiesToObjects(tmp).reversed()
        }
    }

    fun setIndexUser(index: Int) {
        _currentIndexUser.value = index
    }

    fun openMap() {
        val longitude = currentIndexUser.value?.let { rootData.value?.get(it) }
            ?.results?.get(0)?.location?.coordinates?.longitude ?: return
        val latitude = currentIndexUser.value?.let { rootData.value?.get(it) }
            ?.results?.get(0)?.location?.coordinates?.latitude ?: return

        onOpenMap?.invoke(latitude, longitude)
    }

    fun openEmailProgram() {
        val email =
            currentIndexUser.value?.let { rootData.value?.get(it) }?.results?.get(0)?.email
                ?: return
        onOpenEmailProgram?.invoke(email)
    }

    fun callPhone() {
        val phone =
            currentIndexUser.value?.let { rootData.value?.get(it) }?.results?.get(0)?.phone
                ?: return
        onCallPhone?.invoke(phone)
    }


}