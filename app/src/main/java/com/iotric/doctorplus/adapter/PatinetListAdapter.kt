package com.iotric.doctorplus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.PatientAdaterItemBinding
import com.iotric.doctorplus.model.response.PatientItem
import com.iotric.doctorplus.model.response.PatientsItem
import com.iotric.doctorplus.util.DateTimeUtil

class PatinetListAdapter(val listener: ItemClickListener) :
    ListAdapter<PatientsItem, PatinetListAdapter.ItemViewHolder>(
        object :
            DiffUtil.ItemCallback<PatientsItem>() {
            override fun areContentsTheSame(oldItem: PatientsItem, newItem: PatientsItem): Boolean {
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
        val nextAppointment = item.visit?.firstOrNull()
        holder.tv_name.text = item.pname
        holder.tv_contact.text = item.pphone
        holder.tv_nextVisitDate.text = DateTimeUtil.getSimpleDateFromUtc(item.dayofvisit)
        holder.tv_date.text = item.createdAt
        val date = item.createdAt
        holder.tv_date.text = DateTimeUtil.getSimpleDateFromUtc(date)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_name = binding.tvName
        val tv_contact = binding.tvContact
        val tv_date = binding.tvLastVisitDate
        val tv_nextVisitDate = binding.tvVisitDate
        //val iv_image = binding.ivImage
        val item_layout = binding.lLayout
        val btnDelete = binding.btndelete

        init {
            item_layout.setOnClickListener {
                listener.onItemLayoutClick(getItem(position))
            }
            btnDelete.setOnClickListener {
                listener.onDeleteClick(getItem(position))
            }
        }
    }

    interface ItemClickListener {
        fun onItemLayoutClick(result: PatientsItem)
        fun onDeleteClick(result: PatientsItem)

    }
}
