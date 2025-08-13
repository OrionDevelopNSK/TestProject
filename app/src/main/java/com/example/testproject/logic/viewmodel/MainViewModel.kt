package com.example.testproject.logic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.data.entity.RootEntity
import com.example.testproject.logic.actions.MailSender
import com.example.testproject.logic.actions.MapOpener
import com.example.testproject.logic.actions.PhoneCaller
import com.example.testproject.logic.interfaces.IDatabaseManager
import com.example.testproject.logic.interfaces.IResultDownloader
import com.example.testproject.model.Root
import com.example.testproject.util.Converter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    val mailSender: MailSender,
    val phoneCaller: PhoneCaller,
    val mapOpener: MapOpener,
    val resultDownloader: IResultDownloader,
    val databaseManager: IDatabaseManager
) : ViewModel() {
    private val _rootData = MutableStateFlow<List<Root>>(emptyList())
    val rootData: StateFlow<List<Root>> = _rootData.asStateFlow()
    private val _currentIndexUser = MutableStateFlow(0)
    val currentIndexUser: StateFlow<Int> = _currentIndexUser.asStateFlow()
    private val _errorEvent = MutableStateFlow<Int?>(null)
    val errorEvent: StateFlow<Int?> = _errorEvent.asStateFlow()

    fun setIndexUser(index: Int) {
        _currentIndexUser.value = index
    }

    fun downloadResults() {
        viewModelScope.launch {
            resultDownloader.downloadResults(
                action = { act -> saveAndLoad(act) },
                errorHandler = { err ->
                    _errorEvent.update { err }
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

            }
            _rootData.update { emptyList() }
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

    private fun getResult() = currentIndexUser.value.let { rootData.value.getOrNull(it) }?.results?.get(0)

    private fun load() {
        val tmp = databaseManager.load()
        if (tmp.isNotEmpty()) _rootData.update { Converter.entitiesToObjects(tmp).reversed() }
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