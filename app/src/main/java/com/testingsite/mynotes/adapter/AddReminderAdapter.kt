package com.testingsite.mynotes.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.testingsite.mynotes.databinding.ReminderSingleBinding

class AddReminderAdapter(private val views: ArrayList<Int>) : RecyclerView.Adapter<AddReminderAdapter.ViewHolder>() {

    private lateinit var binding: ReminderSingleBinding

    class ViewHolder(binding: ReminderSingleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddReminderAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: AddReminderAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}