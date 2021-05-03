package com.kassemitani.fleetcomplete.model.vehicle

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VehicleHistory(

    @field:SerializedName("objectId")
    val objectId: Int? = null,

    @field:SerializedName("driverId")
    val driverId: Int? = null,

    @field:SerializedName("speed")
    val speed: Double? = null,

    @field:SerializedName("lastEngineOnTime")
    val lastEngineOnTime: String? = null,

    @field:SerializedName("plate")
    val plate: String? = null,

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("driverName")
    val driverName: String? = null

    ): Parcelable