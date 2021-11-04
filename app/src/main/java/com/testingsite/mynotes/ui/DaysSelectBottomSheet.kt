package com.testingsite.mynotes.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.testingsite.mynotes.R
import com.testingsite.mynotes.`interface`.DaysBottomSheetCloseInterface
import com.testingsite.mynotes.`interface`.DaysSelected
import com.testingsite.mynotes.adapter.DaysSelectAdapter
import com.testingsite.mynotes.databinding.DaysSelectBottomSheetBinding
import com.testingsite.mynotes.db.NoteDatabase
import com.testingsite.mynotes.model.DaysSelectModel
import java.util.concurrent.Executors

class DaysSelectBottomSheet(
    private var daysBottomSheetCloseInterface: DaysBottomSheetCloseInterface,
    private var textViewCalled: TextView
) : BottomSheetDialogFragment() {

    private lateinit var binding: DaysSelectBottomSheetBinding
    private lateinit var daysSelectedList: ArrayList<DaysSelectModel>
    private lateinit var daysSelectedFinal: String

    private val daySelectInterface = object : DaysSelected {
        override fun respond(daysSelectedList: ArrayList<DaysSelectModel>) {
            Log.i("TAG", "respond: Called")
            try {
                this@DaysSelectBottomSheet.daysSelectedList = daysSelectedList

                for (item in this@DaysSelectBottomSheet.daysSelectedList) {
                    if (item.isSelected) {
                        daysSelectedFinal = daysSelectedFinal + "," + item.day
                    }
                }

            } catch (e: Exception) {

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return super.onCreateView(inflater, container, savedInstanceState)
        val myDrawerView = layoutInflater.inflate(R.layout.days_select_bottom_sheet, null)
        binding =
            DaysSelectBottomSheetBinding.inflate(layoutInflater, myDrawerView as ViewGroup, false)

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.rvDays
        binding.rvDays.layoutManager = LinearLayoutManager(context)
        binding.rvDays.setHasFixedSize(true)

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute {
            NoteDatabase(requireActivity()).getNoteDao().deleteDays()
            val days = NoteDatabase(requireActivity()).getNoteDao().getDays()
            daysSelectedList = ArrayList()
            for (item in days) {
                daysSelectedList.add(DaysSelectModel(item.name, false))
            }

            handler.post {
                binding.rvDays.adapter =
                    DaysSelectAdapter(
                        activity?.applicationContext,
                        days,
                        daysSelectedList,
                        daySelectInterface
                    )
            }
        }

        binding.crossIb.setOnClickListener {
            closeSheet()
        }

        binding.apbSubmit.setOnClickListener {
            closeSheet()

        }


    }

    private fun closeSheet() {
        daysBottomSheetCloseInterface.respond(daysSelectedList, textViewCalled)
        this.dismiss()
    }
}