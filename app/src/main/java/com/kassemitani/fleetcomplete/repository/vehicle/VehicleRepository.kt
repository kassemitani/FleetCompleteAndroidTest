package com.kassemitani.fleetcomplete.repository.vehicle

import com.kassemitani.fleetcomplete.api.service.VehiclesService
import com.kassemitani.fleetcomplete.model.vehicle.RawDataResponse
import com.kassemitani.fleetcomplete.model.vehicle.VehicleHistoryResponse
import io.reactivex.Flowable

class VehicleRepository(var vehiclesService: VehiclesService): VehicleDataSource {
    override fun getLastData(key: String, json: Boolean): Flowable<VehicleHistoryResponse> {
       return vehiclesService.getLastData(key, json)
    }

    override fun getRawData(key: String, objectId: Int, begTimestamp: String, endTimestamp: String): Flowable<RawDataResponse> {
        return vehiclesService.getRawData(key, json = true,
            objectId = objectId, begTimestamp = begTimestamp, endTimestamp = endTimestamp)
    }

}