package com.kassemitani.fleetcomplete.repository.vehicle

import com.kassemitani.fleetcomplete.model.vehicle.RawDataResponse
import com.kassemitani.fleetcomplete.model.vehicle.VehicleHistoryResponse
import io.reactivex.Flowable

interface VehicleDataSource {
    fun getLastData(key: String, json: Boolean): Flowable<VehicleHistoryResponse>
    fun getRawData(key: String, objectId: Int, begTimestamp: String, endTimestamp: String): Flowable<RawDataResponse>
}