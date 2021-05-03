package com.kassemitani.fleetcomplete.utility

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale
import java.util.concurrent.TimeUnit

object DateHelper{
    fun formatDateToString(date: Date?, pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
       return if(date != null) SimpleDateFormat(pattern, Locale.US).format(date) else ""
    }
    fun formatStringToDate(date: String?, pattern: String = "yyyy-MM-dd HH:mm:ss"): Date {
        return if(date != null)  SimpleDateFormat(pattern, Locale.US).parse(date) else Date()
    }

    fun dateSince(date: String?, pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
        var str = ""
        var diff : Long = 0
        try {
            diff = Date().time -formatStringToDate(date).time
        } catch (e: Exception) {
            Log.d("sdf","error= $e")
        }
        var days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
        if (days > 0) str+= "${days}d "
        var hours = TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS) % 24
        if (hours > 0) str+= "${hours}h "
        var minutes = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS) % 60
        if (minutes > 0) str+= "${minutes}m "
        var seconds = TimeUnit.SECONDS.convert(diff, TimeUnit.MILLISECONDS)  % 60
        if (seconds > 0) str+= "${seconds}s "
        if (str != "") str += "ago" else return "now"
        return str
    }
}