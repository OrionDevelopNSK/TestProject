package com.example.testproject.logic.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testproject.data.database.RoomDatabaseBuilder
import com.example.testproject.logic.actions.MailSender
import com.example.testproject.logic.actions.MapOpener
import com.example.testproject.logic.actions.PhoneCaller
import com.example.testproject.logic.repo.DatabaseManager
import com.example.testproject.logic.interfaces.IDatabaseManager
import com.example.testproject.logic.interfaces.IResultDownloader
import com.example.testproject.logic.network.ResultDownloader
import com.example.testproject.logic.viewmodel.MainViewModel
import com.example.testproject.retrofit.ResultsApi

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val db = RoomDatabaseBuilder().getAppDatabase(app)
        val ms = MailSender(app)
        val pc = PhoneCaller(app)
        val mo = MapOpener(app)
        val dm: IDatabaseManager = DatabaseManager(db.roomDao())
        val rd: IResultDownloader = ResultDownloader(ResultsApi.retrofitService)

        return MainViewModel(
            mailSender = ms,
            phoneCaller = pc,
            mapOpener = mo,
            databaseManager = dm,
            resultDownloader = rd
        ) as T
    }

}