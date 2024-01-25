package com.example.cfttest2024.viewmodel;

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cfttest2024.model.Root
import com.example.cfttest2024.retrofit.ResultsApi
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BaseViewModel : ViewModel() {
    private var _rootData: MutableLiveData<Root> = MutableLiveData()
    val rootData: LiveData<Root> = _rootData


    open fun loadResults() {
        viewModelScope.launch {
            ResultsApi.retrofitService.getResult().enqueue(object : Callback<Root> {
                override fun onResponse(call: Call<Root>, response: Response<Root>) {
                    _rootData.value = response.body()
                }

                override fun onFailure(call: Call<Root>, t: Throwable) {

                }
            })

        }
    }

}