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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val infalter = LayoutInflater.from(parent.context)
        val view = infalter.inflate(R.layout.patient_adater_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.tv_name.text = item.pname
        holder.tv_contact.text = item.pphone
        holder.tv_nextVisitDate.text = item.nextvisit.toString()
        holder.tv_date.text = item.dayofvisit
        val date = item.createdAt
        holder.tv_date.text = DateTimeUtil.getSimpleDateFromUtc(date)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_name = itemView.findViewById<AppCompatTextView>(R.id.tv_name)
        val tv_contact = itemView.findViewById<AppCompatTextView>(R.id.tv_contact)
        val tv_date = itemView.findViewById<AppCompatTextView>(R.id.tv_visitDate)
        val tv_nextVisitDate = itemView.findViewById<AppCompatTextView>(R.id.tv_nextVisitDate)
        val iv_image = itemView.findViewById<AppCompatImageView>(R.id.iv_image)
        val item_layout = itemView.findViewById<RelativeLayout>(R.id.lLayout)
        val btnDelete = itemView.findViewById<AppCompatImageView>(R.id.btndelete)

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
