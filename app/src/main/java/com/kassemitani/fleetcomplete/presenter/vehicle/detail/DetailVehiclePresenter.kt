package com.kassemitani.fleetcomplete.presenter.vehicle.detail

import com.google.android.gms.maps.model.LatLng
import com.kassemitani.fleetcomplete.contract.vehicle.detail.DetailVehicleContract
import com.kassemitani.fleetcomplete.model.vehicle.RawData
import com.kassemitani.fleetcomplete.model.vehicle.RawDataResponse
import com.kassemitani.fleetcomplete.model.vehicle.VehicleHistoryResponse
import com.kassemitani.fleetcomplete.repository.vehicle.VehicleRepository
import com.kassemitani.fleetcomplete.utility.DateHelper
import id.digisys.android.kotlinmvpsamples.utility.SchedulerInterface
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.verbose
import java.util.*


class DetailVehiclePresenter(val view: DetailVehicleContract.View, val repository: VehicleRepository, val scheduler: SchedulerInterface): DetailVehicleContract.Presenter {

    private val log = AnkoLogger(this.javaClass)
    private val compositeDisposable = CompositeDisposable()

    override fun onDestroy(){
        compositeDisposable.dispose()
    }

    override fun getRawData(key: String,objectId: Int, date: Date){
        view.showLoading()
        val endTimestamp = DateHelper.formatDateToString(date, "yyyy-MM-dd")
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DATE, -1)
        val begTimestamp = DateHelper.formatDateToString(calendar.time, "yyyy-MM-dd")
        compositeDisposable.add(repository.getRawData(key, objectId, begTimestamp, endTimestamp)
            .observeOn(scheduler.ui())
            .subscribeOn(scheduler.io())
            .subscribeWith(object : ResourceSubscriber<RawDataResponse>(){
                override fun onComplete() {
                    view.hideLoading()
                }

                override fun onNext(t: RawDataResponse?) {
                    var tripDistance = formatDistanceText(calculateTripDistance(t?.rawData) / 1000)
                    val latLngList = t?.rawData?.map { LatLng(it.Latitude?:0.0, it.Longitude?:0.0) }
                    view.displayRawData(latLngList ?: Collections.emptyList(),"Trip distance: $tripDistance km")
                }

                override fun onError(t: Throwable?) {
                    view.displayRawData(Collections.emptyList(), null)
                    view.hideLoading()
                }

            })
        )
    }

    fun calculateTripDistance(rawData: List<RawData>?) : Double {
        return rawData?.sumByDouble{ it.Distance?:0.0 } ?: 0.0
    }

    fun formatDistanceText(distance: Double): String {
        return String.format("%.2f", distance)
    }
}