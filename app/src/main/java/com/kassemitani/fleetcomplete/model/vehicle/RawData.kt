package com.kassemitani.fleetcomplete.model.vehicle

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RawData(

    @field:SerializedName("objectId")
    val objectId: Int? = null,

    @field:SerializedName("Longitude")
    val Longitude: Double? = null,

    @field:SerializedName("Latitude")
    val Latitude: Double? = null,

    @field:SerializedName("timestamp")
    val timestamp: String? = null,

    @field:SerializedName("Direction")
    val Direction: Double? = null,

    @field:SerializedName("Power")
    val Power: Double? = null,

    @field:SerializedName("CANDistance")
    val CANDistance: Double? = null,

    @field:SerializedName("Distance")
    val Distance: Double? = null

): Parcelable