package com.kassemitani.fleetcomplete.contract.vehicle.detail

import com.google.android.gms.maps.model.LatLng
import com.kassemitani.fleetcomplete.model.vehicle.RawData
import java.util.*


interface DetailVehicleContract {
    interface View{
        fun displayRawData(latLngList: List<LatLng>, tripDistance: String?)
        fun hideLoading()
        fun showLoading()
    }
    interface Presenter{
        fun onDestroy()
        fun getRawData(key: String, objectId: Int, date: Date)
    }
}