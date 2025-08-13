package com.example.testproject.logic.actions

import android.app.Application
import android.content.Intent
import androidx.core.net.toUri
import com.example.testproject.model.Result
import com.example.testproject.logic.interfaces.IAction

class MailSender(private val app: Application) : IAction {
    override fun execute(result: Result?) {
        val email = result?.email ?: return
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = "mailto:$email".toUri()
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val createChooser = Intent.createChooser(intent, null).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)}
        app.startActivity(createChooser)
    }
}