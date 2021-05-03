package com.kassemitani.fleetcomplete.api.service

import com.kassemitani.fleetcomplete.model.vehicle.RawDataResponse
import com.kassemitani.fleetcomplete.model.vehicle.VehicleHistoryResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query


interface VehiclesService {
    @GET("getLastData")
    fun getLastData(@Query("key") key: String, @Query("json") json: Boolean) : Flowable<VehicleHistoryResponse>

    @GET("getRawData")
    fun getRawData(@Query("key") key: String, @Query("json") json: Boolean, @Query("objectId") objectId: Int, @Query("begTimestamp") begTimestamp: String, @Query("endTimestamp") endTimestamp: String): Flowable<RawDataResponse>

}