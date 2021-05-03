package com.kassemitani.fleetcomplete.model.vehicle
import com.google.gson.annotations.SerializedName

data class VehicleHistoryResponse(@SerializedName("response") val vehicleHistory: List<VehicleHistory>?)
