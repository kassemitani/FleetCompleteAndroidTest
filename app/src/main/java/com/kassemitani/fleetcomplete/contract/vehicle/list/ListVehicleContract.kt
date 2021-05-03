package com.kassemitani.fleetcomplete.contract.vehicle.list

import com.kassemitani.fleetcomplete.model.vehicle.VehicleHistory

interface ListVehicleContract {
    interface View{
        fun displayLastData(dataList: List<VehicleHistory>)
        fun hideLoading()
        fun showLoading()
    }
    interface Presenter{
        fun onDestroy()
        fun getLastData(key: String)
    }
}