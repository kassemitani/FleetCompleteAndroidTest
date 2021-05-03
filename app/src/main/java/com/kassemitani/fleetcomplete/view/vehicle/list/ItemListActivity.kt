package com.kassemitani.fleetcomplete.view.vehicle.list

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.kassemitani.fleetcomplete.R
import com.kassemitani.fleetcomplete.adapter.VehicleRecyclerViewAdapter
import com.kassemitani.fleetcomplete.api.service.RestApi
import com.kassemitani.fleetcomplete.api.service.VehiclesService
import com.kassemitani.fleetcomplete.contract.vehicle.list.ListVehicleContract
import com.kassemitani.fleetcomplete.model.vehicle.VehicleHistory
import com.kassemitani.fleetcomplete.presenter.vehicle.list.ListVehiclePresenter
import com.kassemitani.fleetcomplete.repository.vehicle.VehicleRepository
import com.kassemitani.fleetcomplete.utility.AlertDialogHelper
import com.kassemitani.fleetcomplete.utility.hide
import com.kassemitani.fleetcomplete.utility.show
import id.digisys.android.kotlinmvpsamples.utility.SchedulerProvider
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*


/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemListActivity : AppCompatActivity(), ListVehicleContract.View {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false
    private var apiKey : String = ""
    private lateinit var mPresenter : ListVehiclePresenter
    private var vehicleMutableList : MutableList<VehicleHistory> = mutableListOf()
    private lateinit var vehicleRecyclerViewAdapter: VehicleRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        supportActionBar?.setCustomView(R.layout.action_bar_layout);
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_refresh_page_option);// set drawable icon
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable( ColorDrawable(Color.WHITE))
        supportActionBar?.customView?.findViewById<TextView>(R.id.tvTitle)?.text = getString(R.string.title_item_list)

        if (findViewById<NestedScrollView>(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        val scheduler = SchedulerProvider()
        val service = RestApi.vehiclesService().create(VehiclesService::class.java)
        val vehicleRepository = VehicleRepository(service)
        mPresenter = ListVehiclePresenter(this, vehicleRepository, scheduler)
        if (apiKey.isNotBlank()) {
            mPresenter.getLastData(apiKey)
        }

        vehicleRecyclerViewAdapter = VehicleRecyclerViewAdapter(vehicleMutableList, this, twoPane)
        item_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        item_list.adapter = vehicleRecyclerViewAdapter
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.key, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {//refresh Data
                mPresenter.getLastData(apiKey)
                true
            }
            R.id.key -> {
                showApiKeyDialog()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun showApiKeyDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.enter_api_key)

        val editText = TextInputEditText(this)
        editText.setText(apiKey)
        val constraintLayout = AlertDialogHelper.getEditTextLayout(this, editText)
        builder.setView(constraintLayout)

        builder.setPositiveButton(getString(R.string.ok),
            DialogInterface.OnClickListener {
                dialog, which -> updateAPIkey(editText.text.toString())
            })
        builder.setNegativeButton(getString(R.string.cancel),
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }

    private fun updateAPIkey(newApiKey: String) {
        apiKey = newApiKey
        mPresenter.getLastData(apiKey)
    }

    override fun displayLastData(dataList: List<VehicleHistory>) {
        vehicleMutableList.clear()
        vehicleMutableList.addAll(dataList)
        vehicleRecyclerViewAdapter.notifyDataSetChanged()
    }

    override fun hideLoading() {
        progressBar.hide()
        item_list.visibility = View.VISIBLE
    }

    override fun showLoading() {
        progressBar.show()
        item_list.visibility = View.GONE
    }
}