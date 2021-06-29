package com.iotric.doctorplus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.PatientAdaterItemBinding
import com.iotric.doctorplus.model.response.PatientsItems
import com.iotric.doctorplus.util.DateTimeUtil


class PatinetListAdapter(val listener: ItemClickListener) :
    ListAdapter<PatientsItems, PatinetListAdapter.ItemViewHolder>(
        object :
            DiffUtil.ItemCallback<PatientsItems>() {
            override fun areContentsTheSame(
                oldItem: PatientsItems,
                newItem: PatientsItems
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: PatientsItems, newItem: PatientsItems): Boolean {
                return oldItem == newItem
            }
        }) {
    lateinit var binding: PatientAdaterItemBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        binding = PatientAdaterItemBinding.inflate(LayoutInflater.from(parent.context))
        val view = binding.root
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        val nextAppointment = item.visit?.firstOrNull()
        val date = item.createdAt
        if (item.iscaseopen == true) {
            holder.tv_name.text = item.pname
            holder.tv_contact.text = item.pphone
            holder.email.text = item.pemail
            holder.tv_nextVisitDate.text = DateTimeUtil.getSimpleDateFromUtc(item.dayofvisit)
            holder.tv_date.text = item.createdAt
            holder.tv_date.text = DateTimeUtil.getSimpleDateFromUtc(date)
        }
        holder.moreOption.setOnClickListener {
            val popup = PopupMenu(it.context, holder.moreOption)
            popup.menuInflater.inflate(R.menu.more_option_menu, popup.menu)
            popup.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.patient_profile -> {
                        listener.onPatientProfileClick(getItem(position))
                    }
                    R.id.update_profile -> {
                        listener.onUpdateProfile(getItem(position))
                    }
                    R.id.Update_Status -> {
                        listener.onChangeStatus(getItem(position))
                    }
                    R.id.delete_Profile -> {
                        listener.onDeleteClick(getItem(position))
                    }
                }

                true
            }
            popup.show()
        }
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_name = binding.tvName
        val tv_contact = binding.tvContact
        val email = binding.tvEmail
        val tv_date = binding.tvLastVisitDate
        val tv_nextVisitDate = binding.tvVisitDate
        val item_layout = binding.lLayout
        val moreOption = binding.moreOption

        /* init {
             item_layout.setOnClickListener {
                 listener.onItemLayoutClick(getItem(position))
             }
             moreOption.setOnClickListener {
                 //listener.onDeleteClick(getItem(position))
             }
         }*/

    }

    interface ItemClickListener {
        fun onDeleteClick(result: PatientsItems)
        fun onPatientProfileClick(result: PatientsItems)
        fun onUpdateProfile(result: PatientsItems)
        fun onChangeStatus(result: PatientsItems)

    }

}


