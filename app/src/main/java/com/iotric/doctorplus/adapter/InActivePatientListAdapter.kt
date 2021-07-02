package com.iotric.doctorplus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iotric.doctorplus.databinding.InactivePatientAdapterItemBinding
import com.iotric.doctorplus.model.response.Patient
import com.iotric.doctorplus.model.response.PatientsItems
import com.iotric.doctorplus.util.DateTimeUtil

class InActivePatientListAdapter(val listener: ItemClickListener) :
    ListAdapter<Patient, InActivePatientListAdapter.ItemViewHolder>(
        object :
            DiffUtil.ItemCallback<Patient>() {
            override fun areContentsTheSame(
                oldItem: Patient,
                newItem: Patient
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: Patient, newItem: Patient): Boolean {
                return oldItem == newItem
            }
        }) {
    lateinit var binding: InactivePatientAdapterItemBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        binding = InactivePatientAdapterItemBinding.inflate(LayoutInflater.from(parent.context))
        val view = binding.root
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        val nextAppointment = item.visit?.firstOrNull()
        val date = item.createdAt
        if (item.iscaseopen == false) {
            holder.tv_name.text = item.pname
            holder.tv_contact.text = item.pphone
            holder.email.text = item.pemail
            /*holder.tv_nextVisitDate.text = DateTimeUtil.getSimpleDateFromUtc(item.dayofvisit)
            holder.tv_date.text = item.createdAt
            holder.tv_date.text = DateTimeUtil.getSimpleDateFromUtc(date)*/
        }
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_name = binding.tvName
        val tv_contact = binding.tvContact
        val email = binding.tvEmail
        val tv_date = binding.tvLastVisitDate
        val tv_nextVisitDate = binding.tvVisitDate
        val item_layout = binding.lLayout
        val changeStatusToggle = binding.changeStatusToggle

         init {
             changeStatusToggle.setOnClickListener {
                 listener.onChangeStatus(getItem(position))
             }
            /* moreOption.setOnClickListener {
                 //listener.onDeleteClick(getItem(position))
             }*/
         }

    }

    interface ItemClickListener {
       /* fun onDeleteClick(result: Patient)
        fun onPatientProfileClick(result: Patient)
        fun onUpdateProfile(result: Patient)*/
        fun onChangeStatus(result: Patient)
    }
}
