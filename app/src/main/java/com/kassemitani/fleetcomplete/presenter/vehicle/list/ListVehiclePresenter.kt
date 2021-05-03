package com.kassemitani.fleetcomplete.presenter.vehicle.list

import androidx.appcompat.view.menu.ListMenuPresenter
import com.kassemitani.fleetcomplete.contract.vehicle.list.ListVehicleContract
import com.kassemitani.fleetcomplete.model.vehicle.VehicleHistoryResponse
import com.kassemitani.fleetcomplete.repository.vehicle.VehicleRepository
import id.digisys.android.kotlinmvpsamples.utility.SchedulerInterface
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.verbose
import java.util.*

class ListVehiclePresenter(val view: ListVehicleContract.View, val repository: VehicleRepository, val scheduler: SchedulerInterface): ListVehicleContract.Presenter {

    private val log = AnkoLogger(this.javaClass)
    private val compositeDisposable = CompositeDisposable()

    override fun onDestroy(){
        compositeDisposable.dispose()
    }

    override fun getLastData(key: String){
        view.showLoading()
        compositeDisposable.add(repository.getLastData(key, json = true)
            .observeOn(scheduler.ui())
            .subscribeOn(scheduler.io())
            .subscribeWith(object : ResourceSubscriber<VehicleHistoryResponse>(){
                override fun onComplete() {
                    view.hideLoading()
                }

                override fun onNext(t: VehicleHistoryResponse?) {
                    log.verbose("return = ${t?.vehicleHistory.toString()}")
                    view.displayLastData(t?.vehicleHistory ?: Collections.emptyList())
                }

                override fun onError(t: Throwable?) {
                    log.error("getLastData : "+t!!.message)
                    view.displayLastData(Collections.emptyList())
                    view.hideLoading()
                }

            })
        )
    }
}