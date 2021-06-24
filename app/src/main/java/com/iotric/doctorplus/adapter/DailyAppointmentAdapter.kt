package com.iotric.doctorplus.adapter

import android.telecom.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iotric.doctorplus.databinding.DailyAppointmentFragmentBinding
import com.iotric.doctorplus.databinding.DailyAppointmetAdapterItemBinding
import com.iotric.doctorplus.model.response.DataItem
import com.iotric.doctorplus.util.DateTimeUtil

class DailyAppointmentAdapter: ListAdapter<DataItem, DailyAppointmentAdapter.ItemViewHolder>(object :
    DiffUtil.ItemCallback<DataItem>()
     {
         override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
             return oldItem == newItem
         }

         override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
             return oldItem == newItem
         }

     }) {
    lateinit var binding:DailyAppointmetAdapterItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        binding = DailyAppointmetAdapterItemBinding.inflate(LayoutInflater.from(parent.context))
        val view = binding.root
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        val nextvisit = item.visit?.firstOrNull()
        if(item.iscaseopen == true && nextvisit?.isvisted == false) {
            holder.name.text = item.pname
            holder.phone.text = item.pphone
            holder.lastVisitDate.text = DateTimeUtil.getSimpleDateFromUtc(item.dayofvisit)
            holder.nextvisitTime.text = nextvisit.nextvisittime
        }
    }

    inner class ItemViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val name = binding.tvName
        val phone = binding.tvContact
        val lastVisitDate = binding.tvLastVisitDate
        val nextvisitTime = binding.tvVisitDate
    }

}
