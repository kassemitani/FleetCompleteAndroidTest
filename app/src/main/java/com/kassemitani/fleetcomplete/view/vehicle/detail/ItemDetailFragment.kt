package com.kassemitani.fleetcomplete.view.vehicle.detail

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.kassemitani.fleetcomplete.R
import com.kassemitani.fleetcomplete.api.service.RestApi
import com.kassemitani.fleetcomplete.api.service.VehiclesService
import com.kassemitani.fleetcomplete.contract.vehicle.detail.DetailVehicleContract
import com.kassemitani.fleetcomplete.presenter.vehicle.detail.DetailVehiclePresenter
import com.kassemitani.fleetcomplete.repository.vehicle.VehicleRepository
import com.kassemitani.fleetcomplete.utility.DateHelper
import com.kassemitani.fleetcomplete.utility.hide
import com.kassemitani.fleetcomplete.utility.show
import id.digisys.android.kotlinmvpsamples.utility.SchedulerProvider
import kotlinx.android.synthetic.main.fragment_item_detail.*
import java.util.*

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListActivity]
 * in two-pane mode (on tablets) or a [ItemDetailActivity]
 * on handsets.
 */
class ItemDetailFragment : Fragment(), OnMapReadyCallback, DetailVehicleContract.View {

    private var itemId: Int = 0
    private var apiKey : String = "home.assignment.2-1230927"
    private lateinit var mPresenter : DetailVehiclePresenter
    private val onClickListener: View.OnClickListener
    private lateinit var googleMap: GoogleMap

    init {
        onClickListener = View.OnClickListener { v ->
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog =
                context?.let {
                    DatePickerDialog(it, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        cal.set(Calendar.YEAR, year)
                        cal.set(Calendar.MONTH, monthOfYear)
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        updateDatePicker(cal.time)
                    }, year, month, day)
                }
            datePickerDialog?.datePicker?.maxDate = System.currentTimeMillis() //disable selecting future date
            datePickerDialog?.show()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                itemId = it.getInt(ARG_ITEM_ID)
                var plateNumber = it.getString(ARG_PLATE_NUMBER)
                (activity as AppCompatActivity).supportActionBar?.customView?.findViewById<TextView>(
                    R.id.tvTitle
                )?.text = getString(R.string.location_history, plateNumber)
            }
        }


    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_item_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val scheduler = SchedulerProvider()
        val service = RestApi.vehiclesService().create(VehiclesService::class.java)
        val vehicleRepository = VehicleRepository(service)
        mPresenter = DetailVehiclePresenter(this, vehicleRepository, scheduler)
        updateDatePicker()
        btnCalendar.setOnClickListener(onClickListener)

        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)
    }

    private fun updateDatePicker(date: Date = Date()) {
        tvDate.text = DateHelper.formatDateToString(date, "dd/MM/yyyy")
        itemId?.let {
            mPresenter.getRawData(apiKey, itemId,date)
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            googleMap = map
        }
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
        const val ARG_PLATE_NUMBER = "plate_number"
    }

    override fun displayRawData(latLngList: List<LatLng>, tripDistance: String?) {
        tvTripDistance.text = tripDistance
        if (latLngList.isNotEmpty()) {
            googleMap?.apply {
                addMarker(
                    MarkerOptions()
                        .position(latLngList.first())
                        .title(getString(R.string.start))
                )
                addMarker(
                    MarkerOptions()
                        .position(latLngList.last())
                        .title(getString(R.string.end))
                )
                animateCamera(CameraUpdateFactory.newLatLngZoom(latLngList.first(), 9f))
                addPolygon(PolygonOptions().addAll(latLngList))
            }
        }

    }

    override fun hideLoading() {
        progressBar.hide()
        llItemContainer.visibility = View.VISIBLE
    }

    override fun showLoading() {
        progressBar.show()
        llItemContainer.visibility = View.GONE
    }
}