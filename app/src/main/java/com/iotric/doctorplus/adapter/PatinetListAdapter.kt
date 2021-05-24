package com.iotric.doctorplus.adapter

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
import com.iotric.doctorplus.model.ResultsItem
import com.iotric.doctorplus.model.User

class PatinetListAdapter(val listener: ItemClickListener) :
    ListAdapter<ResultsItem, PatinetListAdapter.ItemViewHolder>(
        object :
            DiffUtil.ItemCallback<ResultsItem>() {
            override fun areContentsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
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
        holder.tv_name.text = item.title
            //holder.tv_contact.text = item.contact
        //holder.tv_date.text = item.date
    }


    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_name = itemView.findViewById<AppCompatTextView>(R.id.tv_name)
        val tv_contact = itemView.findViewById<AppCompatTextView>(R.id.tv_contact)
        val tv_date = itemView.findViewById<AppCompatTextView>(R.id.tv_date)
        val iv_image = itemView.findViewById<AppCompatImageView>(R.id.iv_image)
        val item_layout = itemView.findViewById<LinearLayoutCompat>(R.id.lLayout)
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
        fun onItemLayoutClick(result: ResultsItem)
        fun onDeleteClick(result: ResultsItem)

    }
}
