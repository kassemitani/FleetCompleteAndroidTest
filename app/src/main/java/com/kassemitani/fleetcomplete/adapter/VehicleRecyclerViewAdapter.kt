package com.kassemitani.fleetcomplete.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.kassemitani.fleetcomplete.view.vehicle.detail.ItemDetailActivity
import com.kassemitani.fleetcomplete.view.vehicle.detail.ItemDetailFragment
import com.kassemitani.fleetcomplete.R
import com.kassemitani.fleetcomplete.model.vehicle.VehicleHistory
import com.kassemitani.fleetcomplete.utility.DateHelper
import kotlinx.android.synthetic.main.item_list_content.view.*

class VehicleRecyclerViewAdapter(private val vehicleList: List<VehicleHistory>, private val context: Context?, private val twoPane: Boolean): RecyclerView.Adapter<VehicleRecyclerViewAdapter.VehicleViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        return VehicleViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list_content, parent, false))
    }

    override fun getItemCount(): Int = vehicleList.size

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as VehicleHistory
            if (twoPane) {
                val fragment = ItemDetailFragment()
                    .apply {
                    arguments = Bundle().apply {
                        putInt(ItemDetailFragment.ARG_ITEM_ID, item.objectId?:0)
                        putString(ItemDetailFragment.ARG_PLATE_NUMBER, item.plate)
                    }
                }
                (context as AppCompatActivity)?.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit()
            } else {
                val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                    putExtra(ItemDetailFragment.ARG_ITEM_ID, item.objectId)
                    putExtra(ItemDetailFragment.ARG_PLATE_NUMBER, item.plate)
                }
                v.context.startActivity(intent)
            }
        }
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        val event = vehicleList[position]
        holder.bind(event)
    }

    inner class VehicleViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(vehicle: VehicleHistory){
            itemView.tvDriverPlate.text = "${vehicle.plate} / ${vehicle.driverName ?: ""}"
            itemView.tvSpeed.text =  if (vehicle.speed != null) "${vehicle.speed} km/h" else "-"
            itemView.tvAddress.text = vehicle.address
            itemView.tvDateTimeSince.text = DateHelper.dateSince(vehicle.lastEngineOnTime)

            with(itemView) {
                tag = vehicle
                setOnClickListener(onClickListener)
            }
        }
    }
}