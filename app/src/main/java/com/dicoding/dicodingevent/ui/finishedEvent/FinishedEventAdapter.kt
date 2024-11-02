package com.dicoding.dicodingevent.ui.finishedEvent

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.dicodingevent.data.remote.response.ListEventsItem
import com.dicoding.dicodingevent.databinding.EventItemBinding
import com.dicoding.dicodingevent.databinding.FragmentFinishedEventBinding

class FinishedEventAdapter : ListAdapter<ListEventsItem, FinishedEventAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ListEventsItem)
    }

    class MyViewHolder(val binding : EventItemBinding, val context : Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event : ListEventsItem) {
            binding.tvItemTitleEvent.text = event.name
            binding.tvItemEventSummary.text = event.summary
//            binding.tvItemEventLocation.text = "Lokasi : ${event.cityName}"
            Glide.with(context).load(event.imageLogo).into(binding.imageView)
        }
    }

    companion object {
        val DIFF_CALLBACK  = object :DiffUtil.ItemCallback<ListEventsItem>(){
            override fun areItemsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = EventItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.itemView.setOnClickListener{onItemClickCallback.onItemClicked(getItem(position))}
        return holder.bind(event)
    }

}