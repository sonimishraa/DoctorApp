package com.iotric.doctorplus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iotric.doctorplus.databinding.PatientReportAdapterItemBinding
import com.iotric.doctorplus.databinding.PriscripAdapterItemBinding
import com.iotric.doctorplus.model.response.PrecriptionItem

class ViewPrescripAdapter(val listener: ItemClickListener) :
    ListAdapter<PrecriptionItem, ViewPrescripAdapter.ItemViewHolder>(
        object : DiffUtil.ItemCallback<PrecriptionItem>() {
            override fun areItemsTheSame(
                oldItem: PrecriptionItem,
                newItem: PrecriptionItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: PrecriptionItem,
                newItem: PrecriptionItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    ) {
    lateinit var binding: PriscripAdapterItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        binding = PriscripAdapterItemBinding.inflate(LayoutInflater.from(parent.context))
        val view = binding.root
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.name.text = item.title
        item.image?.forEach {
            Glide.with(this.binding.root).load(it).into(holder.imageView)
        }
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = binding.ivPrescrip
        val name = binding.prescripName
        val btnDelete = binding.ivDelete

        init {
            imageView.setOnClickListener {
                listener.onImageViewClick(getItem(position))
            }
         /*   btnDelete.setOnClickListener {
                listener.onDeleteClick(getItem(position))
            }*/
        }
    }


    interface ItemClickListener {
        fun onImageViewClick(item: PrecriptionItem)
        //fun onDeleteClick(item: PrecriptionItem)
    }
}
