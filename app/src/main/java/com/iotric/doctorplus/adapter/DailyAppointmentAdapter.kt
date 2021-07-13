package com.iotric.doctorplus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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
        if(item.isvisited == false){
            item.patientid?.let {
                holder.uniqueId.text = it.id
                holder.phone.text = it.pphone
            }
            holder.visitDate.text = DateTimeUtil.getSimpleDateFromUtc(item.nextvisitdate) + " " + item.nextvisittime

        }
    }

    inner class ItemViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val uniqueId = binding.tvName
        val phone = binding.tvContact
        val visitDate = binding.tvVisitDate
       // val nextvisitTime = binding.tvVisitDate
    }

}
