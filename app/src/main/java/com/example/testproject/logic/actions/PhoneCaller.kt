package com.example.testproject.logic.actions

import android.app.Application
import android.content.Intent
import androidx.core.net.toUri
import com.example.testproject.model.Result
import com.example.testproject.logic.interfaces.IAction

class PhoneCaller(private val app: Application) : IAction {
    override fun execute(result: Result?) {
        val phone = result?.phone ?: return
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = "tel: $phone".toUri()
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        app.startActivity(intent)
    }
}