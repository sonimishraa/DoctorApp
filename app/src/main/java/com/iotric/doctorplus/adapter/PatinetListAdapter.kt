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
import com.iotric.doctorplus.model.response.PatientsItem
import com.iotric.doctorplus.util.DateTimeUtil


class PatinetListAdapter(val listener: ItemClickListener) :
    ListAdapter<PatientsItem, PatinetListAdapter.ItemViewHolder>(
        object :
            DiffUtil.ItemCallback<PatientsItem>() {
            override fun areContentsTheSame(
                oldItem: PatientsItem,
                newItem: PatientsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: PatientsItem, newItem: PatientsItem): Boolean {
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
        //val nextAppointment = item.visit?.firstOrNull()
       // val date = item.createdAt
        if (item.iscaseopen == true) {
            holder.tv_name.text = item.pname
            holder.tv_contact.text = item.pphone
            holder.email.text = item.pemail
            holder.age.text = item.age + " " + "Years"
            if(item.gender == "m"){
              holder.gender.text = "Male"
            }else
            holder.gender.text = "Female"
            holder.patientId.text = item.uniqueid
        }
        holder.moreOption.setOnClickListener {
            val popup = PopupMenu(it.context, holder.moreOption)
            popup.menuInflater.inflate(R.menu.more_option_menu, popup.menu)
            popup.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.patient_profile -> {
                        listener.onPatientProfileClick(item)
                    }
                    R.id.update_profile -> {
                        listener.onUpdateProfile(item)
                    }
                    R.id.Update_Status -> {
                        listener.onChangeStatus(item)
                    }
                    R.id.delete_Profile -> {
                        listener.onDeleteClick(item)
                    }
                    R.id.book_appointment -> {
                        listener.onBookAppointmentClick(item)
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
        val gender = binding.tvGender
        val age = binding.tvAge
        val patientId = binding.tvPatientId
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
        fun onDeleteClick(result: PatientsItem)
        fun onPatientProfileClick(result: PatientsItem)
        fun onUpdateProfile(result: PatientsItem)
        fun onChangeStatus(result: PatientsItem)
        fun onBookAppointmentClick(result: PatientsItem)
    }

}


