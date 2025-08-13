package com.example.testproject.logic.actions

import android.app.Application
import android.content.Intent
import androidx.core.net.toUri
import com.example.testproject.logic.interfaces.IAction
import com.example.testproject.model.Result

class MapOpener(private val app: Application) : IAction {
    private val googleMap = "http://maps.google.com/maps?q=loc:"

    override fun execute(result: Result?) {
        val longitude = result?.location?.coordinates?.longitude ?: return
        val latitude = result.location.coordinates.latitude
        val intent = Intent(
            Intent.ACTION_VIEW,
            "${googleMap}$latitude,$longitude".toUri()
        ).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        }
        app.startActivity(intent)
    }
}