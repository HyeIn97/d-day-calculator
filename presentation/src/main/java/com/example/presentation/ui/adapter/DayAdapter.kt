package com.example.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.DayModel
import com.example.presentation.databinding.ItemDayBinding
import com.example.presentation.util.ItemClickListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DayAdapter(
    private val days: ArrayList<DayModel>,
    private val onClick: ItemClickListener<DayModel>
) : RecyclerView.Adapter<DayAdapter.DayViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DayViewHolder(ItemDayBinding.inflate(LayoutInflater.from(parent.context), parent, false), onClick)

    override fun getItemCount() = days.size

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.onBind(days[position])
    }

    class DayViewHolder(
        private val binding: ItemDayBinding,
        private val onClick: ItemClickListener<DayModel>
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: DayModel) {
            var day = 0
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
            val endDay = dateFormat.parse(item.endDay)?.time
            val today = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
            }.time.time

            endDay?.let {
                day = ((today - endDay) / (60 * 60 * 24 * 1000)).toInt()
            }

            binding.run {
                titleTxt.text = item.title
                insertDayTxt.text = item.insertDay
                dayTxt.text = if (day > 0) "D+$day" else if (day == 0) "D+0" else "D" + day.toString()
            }

            binding.settingBtn.setOnClickListener {
                onClick.itemSettingClick(item)
            }

            binding.deleteBtn.setOnClickListener {
                onClick.itemDeleteClick(item)
            }
        }
    }
}