package com.testingsite.mynotes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.testingsite.mynotes.`interface`.DaysSelected
import com.testingsite.mynotes.databinding.SingleDayBinding
import com.testingsite.mynotes.db.Days
import com.testingsite.mynotes.model.DaysSelectModel

class DaysSelectAdapter(
    applicationContext: Context?,
    private val days: MutableList<Days>,
    private var daysSelectedList: ArrayList<DaysSelectModel>,
    private var daySelectInterface: DaysSelected
) :
    RecyclerView.Adapter<DaysSelectAdapter.DaysSelectViewHolder>() {

    private lateinit var binding: SingleDayBinding
    private var context: Context? = applicationContext

    class DaysSelectViewHolder(binding: SingleDayBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysSelectViewHolder {
        binding = SingleDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DaysSelectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DaysSelectViewHolder, position: Int) {

        with(days[position]) {
            binding.cbDays.text = name + "s"

            binding.cbDays.setOnCheckedChangeListener { _, isChecked ->
                daysSelectedList.get(position).isSelected = isChecked
                daySelectInterface.respond(daysSelectedList)
            }

        }
    }

    override fun getItemCount() = days.size
}