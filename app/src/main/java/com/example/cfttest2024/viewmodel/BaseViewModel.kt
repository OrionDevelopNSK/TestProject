package com.example.cfttest2024.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cfttest2024.R
import com.example.cfttest2024.data.database.DataBaseHelper
import com.example.cfttest2024.data.database.RoomDatabaseBuilder
import com.example.cfttest2024.data.entity.RootEntity
import com.example.cfttest2024.data.util.Converter
import com.example.cfttest2024.model.Root
import com.example.cfttest2024.retrofit.ResultsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BaseViewModel : ViewModel() {
    private var _rootData: MutableLiveData<List<Root>> = MutableLiveData()
    val rootData: LiveData<List<Root>> = _rootData

    private var _currentIndexUser: MutableLiveData<Int> = MutableLiveData(0)
    val currentIndexUser: LiveData<Int> = _currentIndexUser

    private lateinit var dataBaseHelper: DataBaseHelper

    private val googleMap = "http://maps.google.com/maps?q=loc:"


    fun createDataBaseHelper(context: Context) {
        val roomDatabaseBuilder = RoomDatabaseBuilder().getAppDatabase(context)
        dataBaseHelper = DataBaseHelper(roomDatabaseBuilder)
    }

    fun clearDatabase(){
        viewModelScope.launch {
            runBlocking {
                launch(Dispatchers.IO) {
                    dataBaseHelper.clearDatabase()
                }
            }
        }
        _rootData.value = emptyList()

    }

    fun downloadResults(context: Context) {
        viewModelScope.launch {
            ResultsApi.retrofitService.getResult().enqueue(object : Callback<Root> {
                @SuppressLint("NullSafeMutableLiveData")
                override fun onResponse(call: Call<Root>, response: Response<Root>) {
                    val root = response.body()
                    if (root != null) {
                        val objectToEntity = Converter.objectToEntity(root)
                        saveInfoToDatabase(objectToEntity)
                        loadInfoFromDatabase()
                    }
                }

                override fun onFailure(call: Call<Root>, t: Throwable) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.error_download), Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

    fun saveInfoToDatabase(rootEntity: RootEntity) {
        dataBaseHelper.saveInfoAndUserInfo(rootEntity)
    }

    fun loadInfoFromDatabase() {
        val tmp = dataBaseHelper.loadInfoAndUserInfo()
        if (tmp != null && tmp.isNotEmpty()) _rootData.value =
            Converter.entitiesToObjects(tmp).reversed()
    }

    fun setIndexUser(index: Int) {
        _currentIndexUser.value = index
    }

    fun openMap(context: Context) {
        val root =
            currentIndexUser.value?.let { rootData.value?.get(it) } ?: return
        val longitude = root.results[0].location.coordinates.longitude
        val latitude = root.results[0].location.coordinates.latitude

        val intent =
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("$googleMap$latitude,$longitude")
            )
        ContextCompat.startActivity(context, intent, null)
    }

    fun openEmailProgram(context: Context) {
        val root =
            currentIndexUser.value?.let { rootData.value?.get(it) } ?: return
        val email = root.results[0].email

        val intent =
            Intent(Intent.ACTION_SENDTO)
                .setData(Uri.parse("mailto:$email"))
        val createChooser = Intent.createChooser(intent, null)
        ContextCompat.startActivity(context, createChooser, null)
    }

    fun callPhone(context: Context) {
        val root =
            currentIndexUser.value?.let { rootData.value?.get(it) } ?: return
        val phone = root.results[0].phone

        val intent =
            Intent(Intent.ACTION_DIAL, Uri.parse("tel: $phone"))
        ContextCompat.startActivity(context, intent, null)
    }
}