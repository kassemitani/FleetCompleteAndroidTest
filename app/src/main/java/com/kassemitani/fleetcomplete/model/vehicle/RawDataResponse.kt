package com.kassemitani.fleetcomplete.model.vehicle

import com.google.gson.annotations.SerializedName

data class RawDataResponse(@SerializedName("response") val rawData: List<RawData>?)
