package com.example.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.DayModel
import com.example.presentation.databinding.ItemDayBinding

class DayAdapter (private val days: ArrayList<DayModel>): RecyclerView.Adapter<DayAdapter.DayViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DayViewHolder(ItemDayBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = days.size

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.onBind(days[position])
    }

    class DayViewHolder(private val binding: ItemDayBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(item: DayModel){
            binding.run {
                titleTxt.text = item.title
                insertDayTxt.text = item.startDay
                dayTxt.text = "+50"
            }
        }
    }
}