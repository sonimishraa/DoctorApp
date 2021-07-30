package com.iotric.doctorplus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iotric.doctorplus.databinding.PatientReportAdapterItemBinding
import com.iotric.doctorplus.model.response.ReportItem
import com.iotric.doctorplus.util.DateTimeUtil

class PatientReportAdapter : ListAdapter<ReportItem, PatientReportAdapter.ItemViewHolder>(
    object : DiffUtil.ItemCallback<ReportItem>() {
        override fun areItemsTheSame(oldItem: ReportItem, newItem: ReportItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ReportItem, newItem: ReportItem): Boolean {
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
        item.labreports?.forEach {
            holder.name.text = it?.reportname
            holder.date.text = DateTimeUtil.getSimpleDateFromUtc(it?.dateofreport)
                it?.images?.forEach {
                Glide.with(this.binding.root).load(it).into(holder.imageView)
            }
        }
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = binding.ivReport
        val date = binding.reportDate
        val name = binding.reportName
    }
}
