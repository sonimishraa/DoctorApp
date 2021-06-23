package com.iotric.doctorplus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iotric.doctorplus.databinding.WeeklyAppointmentAdapterItemBinding
import com.iotric.doctorplus.model.response.DataItem
import com.iotric.doctorplus.util.DateTimeUtil

class WeeklyAppointmentAdapter: ListAdapter<DataItem, WeeklyAppointmentAdapter.ItemViewHolder>(
    object : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }) {
    lateinit var binding: WeeklyAppointmentAdapterItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        binding = WeeklyAppointmentAdapterItemBinding.inflate(LayoutInflater.from(parent.context))
        val view = binding.root
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        val visitItem = item.visit?.firstOrNull()
        if(item.iscaseopen == true) {
            holder.name.text = item.pname
            holder.phone.text = item.pphone
            holder.lastVisit.text = DateTimeUtil.getSimpleDateFromUtc(item.dayofvisit)
            if (visitItem?.isvisted == false) {
                holder.appointmentDate.text = visitItem.nextvisitdate + " " + visitItem.nextvisittime
            }
        }
    }
    inner class ItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val name = binding.tvName
        val email = binding.tvEmail
        val phone = binding.tvContact
        val lastVisit = binding.tvLastVisitDate
        val appointmentDate = binding.tvVisitDate

    }

}
