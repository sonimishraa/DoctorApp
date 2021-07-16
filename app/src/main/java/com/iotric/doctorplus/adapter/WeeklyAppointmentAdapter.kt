package com.iotric.doctorplus.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.WeeklyAppointmentAdapterItemBinding
import com.iotric.doctorplus.model.response.DataItem
import com.iotric.doctorplus.util.DateTimeUtil

class WeeklyAppointmentAdapter(val listener: ItemClickListener): ListAdapter<DataItem, WeeklyAppointmentAdapter.ItemViewHolder>(
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
        Log.i("jaishreeRam","${item.isvisited} id:${item.patientid?.id} position:$position ")
        if(item.isvisited == false) {
            item.patientid?.let{
                //Log.i("PatientId","${it.id}")
                holder.name.text = it.id
                holder.phone.text = it.pphone
            }
            holder.visitDate.text = DateTimeUtil.getSimpleDateFromUtc(item.nextvisitdate) + " " + item.nextvisittime
            holder.moreOption.setOnClickListener {
                val popup = PopupMenu(it.context, holder.moreOption)
                popup.menuInflater.inflate(R.menu.apointment_more_option_menu, popup.menu)
                popup.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.update_appoint -> {
                            listener.onUpadetAppointClick(item)
                        }
                        R.id.delete_appoint -> {
                            listener.onDeleteAppointClick(item)
                        }
                    }

                    true
                }
                popup.show()
            }

        }
    }
    inner class ItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val name = binding.tvName
        val email = binding.tvEmail
        val phone = binding.tvContact
        val moreOption = binding.moreOption
        val visitDate = binding.tvVisitDate

    }
    interface ItemClickListener{
        fun onUpadetAppointClick(item: DataItem)
        fun onDeleteAppointClick(item: DataItem)

    }
}
