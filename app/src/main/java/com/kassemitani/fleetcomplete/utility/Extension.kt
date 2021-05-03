package com.kassemitani.fleetcomplete.utility

import android.content.Context
import android.view.View
import java.text.SimpleDateFormat
import java.util.*

fun View.hide (){
    visibility = View.GONE
}

fun View.show(){
    visibility = View.VISIBLE
}

fun Date.inFuture(): Boolean{
    val createDate = SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault()).format(Date())
    val currentDate = SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault()).parse(createDate)
    return (this.after(currentDate))
}

//val Context.db: DbHelper
//    get() = DbHelper.getInstance(applicationContext)