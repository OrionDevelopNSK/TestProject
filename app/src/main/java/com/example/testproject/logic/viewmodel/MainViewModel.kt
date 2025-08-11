package com.example.testproject.logic.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.data.entity.RootEntity
import com.example.testproject.util.Converter
import com.example.testproject.logic.actions.MailSender
import com.example.testproject.logic.actions.MapOpener
import com.example.testproject.logic.actions.PhoneCaller
import com.example.testproject.logic.interfaces.IDatabaseManager
import com.example.testproject.logic.interfaces.IResultDownloader
import com.example.testproject.model.Root
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    val mailSender: MailSender,
    val phoneCaller: PhoneCaller,
    val mapOpener: MapOpener,
    val resultDownloader: IResultDownloader,
    val databaseManager: IDatabaseManager
) : ViewModel() {
    private var _rootData: MutableLiveData<List<Root>> = MutableLiveData()
    val rootData: LiveData<List<Root>> = _rootData
    private var _currentIndexUser: MutableLiveData<Int> = MutableLiveData(0)
    val currentIndexUser: LiveData<Int> = _currentIndexUser
    private val _errorEvent = MutableLiveData<Int?>(null)
    val errorEvent: LiveData<Int?> = _errorEvent

    fun setIndexUser(index: Int) {
        _currentIndexUser.value = index
    }

    fun downloadResults() {
        viewModelScope.launch {
            resultDownloader.downloadResults(
                action = { act -> saveAndLoad(act) },
                errorHandler = { err ->
                    _errorEvent.postValue(err)
                })
        }
    }

    fun resetError() {
        _errorEvent.value = null
    }

    fun clearDatabase() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                databaseManager.clear()
                _rootData.postValue(emptyList())
            }
        }
    }

    fun loadFromDataBase() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                load()
            }
        }
    }

    fun openMap() = mapOpener.execute(getResult())

    fun openEmailProgram() = mailSender.execute(getResult())

    fun callPhone() = phoneCaller.execute(getResult())

    private fun getResult() = currentIndexUser.value?.let { rootData.value?.get(it) }?.results?.get(0)

    private fun load() {
        val tmp = databaseManager.load()
        if (tmp.isNotEmpty()) _rootData.postValue(Converter.entitiesToObjects(tmp).reversed())
    }

    private fun save(rootEntity: RootEntity) {
        databaseManager.save(rootEntity)
    }

    private suspend fun saveAndLoad(rootEntity: RootEntity) {
        withContext(Dispatchers.IO) {
            save(rootEntity)
            load()
        }
    }

}