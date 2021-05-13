package com.iotric.doctorplus.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iotric.doctorplus.R
import com.iotric.doctorplus.ui.viewmodel.DataModel
import java.text.FieldPosition

abstract class PatinetListAdapter : ListAdapter<DataModel, PatinetListAdapter.ItemViewHolder>(
    object :
        DiffUtil.ItemCallback<DataModel>() {
        override fun areContentsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
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
        holder.tv_name.text = item.name
        holder.tv_contact.text = item.contact
        holder.tv_date.text = item.date
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_name = itemView.findViewById<AppCompatTextView>(R.id.tv_name)
        val tv_contact = itemView.findViewById<AppCompatTextView>(R.id.tv_contact)
        val tv_date = itemView.findViewById<AppCompatTextView>(R.id.tv_date)
        val iv_mage = itemView.findViewById<AppCompatImageView>(R.id.iv_image)
        val item_layout= itemView.findViewById<LinearLayoutCompat>(R.id.lLayout)

        init {
            item_layout.setOnClickListener {
                onItemLayoutClick(adapterPosition)
            }

        }
    }
    abstract fun onItemLayoutClick(position: Int)
}
