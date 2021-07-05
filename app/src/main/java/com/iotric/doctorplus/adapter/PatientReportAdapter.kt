package com.iotric.doctorplus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iotric.doctorplus.databinding.PatientReportAdapterItemBinding
import com.iotric.doctorplus.model.response.LabreportsItem
import okhttp3.HttpUrl.Companion.toHttpUrl

class PatientReportAdapter : ListAdapter<LabreportsItem, PatientReportAdapter.ItemViewHolder>(
    object : DiffUtil.ItemCallback<LabreportsItem>() {
        override fun areItemsTheSame(oldItem: LabreportsItem, newItem: LabreportsItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: LabreportsItem, newItem: LabreportsItem): Boolean {
            return oldItem == newItem
        }
    }
) {
    lateinit var binding: PatientReportAdapterItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        binding = PatientReportAdapterItemBinding.inflate(LayoutInflater.from(parent.context))
        val view = binding.root
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        val image = item.images?.firstOrNull()?.toString()
        Glide.with(this.binding.root).load(image).into(holder.imageView)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = binding.ivReport
    }
}
