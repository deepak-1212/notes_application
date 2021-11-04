package com.testingsite.mynotes.ui

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.switchmaterial.SwitchMaterial
import com.testingsite.mynotes.R
import com.testingsite.mynotes.`interface`.DaysBottomSheetCloseInterface
import com.testingsite.mynotes.databinding.FragmentAddReminderBinding
import com.testingsite.mynotes.model.DaysSelectModel

class AddReminderFragment : Fragment(R.layout.fragment_add_reminder) {

    private lateinit var binding: FragmentAddReminderBinding

    private val daysBottomSheetCloseInterface = object : DaysBottomSheetCloseInterface {
        override fun respond(
            daysSelectedList: ArrayList<DaysSelectModel>,
            textViewCalled: TextView
        ) {
            var daysString = ""

            for (days in daysSelectedList) {
                if (days.isSelected) {
                    daysString = daysString + "" + days.day + " "
                }
            }
            textViewCalled.text = daysString
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddReminderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.reminderList.setHasFixedSize(true)
        binding.reminderList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

      /*  addNewReminderView()
        binding.floatingActionButton.setOnClickListener {
            addNewReminderView()
        }*/

    }

    /*private fun addNewReminderView() {

        linearMainLayout = binding.singleReminder

        val linearSubLayout = LinearLayout(context)
        linearSubLayout.orientation = LinearLayout.VERTICAL

        linearSubLayout.addView(addEditText())
        linearSubLayout.addView(addSwitchRepeat())
        linearSubLayout.addView(addTextView("Nothing Selected"))
        linearMainLayout.addView(linearSubLayout)

    }

    private fun addEditText(): EditText {
        val editText = EditText(context)
        editText.hint = "Sub Title"
        editText.textSize = 17.5f
        editText.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        editText.setPadding(20, 20, 20, 20)

        return editText
    }

    private fun addSwitchRepeat(): SwitchMaterial? {
        val switchRepeat = context?.let { SwitchMaterial(it) }
        val layoutParams2 = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        switchRepeat?.layoutParams = layoutParams2
        switchRepeat?.text = getString(R.string.repeat)
        switchRepeat?.setPadding(20, 20, 20, 20)

        switchRepeat?.setOnCheckedChangeListener { _, isChecked ->
            // Responds to switch being checked/unchecked
            Log.i("TAG", "addSwitchRepeat: $isChecked")

            val linearLayout: LinearLayout = switchRepeat.parent as LinearLayout
            val index = linearLayout.indexOfChild(switchRepeat)

            val textViewDays: TextView = linearLayout.getChildAt(index + 1) as TextView
            textViewDays.isEnabled = isChecked

        }

        return switchRepeat
    }

    private fun addTextView(textHeading: String): TextView {
        val textView = TextView(context)
        textView.textSize = 13.5f
        textView.hint = textHeading
        textView.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        textView.setPadding(20, 20, 20, 20)

        textView.setOnClickListener {
            val bottomSheet = DaysSelectBottomSheet(daysBottomSheetCloseInterface, textView)
            bottomSheet.show(parentFragmentManager, "RepeatDays")
        }
        textView.isEnabled = false

        return textView
    }*/

    private fun addImageButton(): ImageView {
        val imageButton = ImageView(context)
        imageButton.setImageDrawable(context?.let {
            ContextCompat.getDrawable(
                it,
                R.drawable.ic_baseline_chevron_right_24
            )
        })
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.gravity = Gravity.END

        imageButton.layoutParams = layoutParams

        imageButton.setPadding(20, 20, 20, 20)

        imageButton.setOnClickListener {

        }


        return imageButton
    }

    private fun addButton(): Button {
        val button = Button(context)
        button.hint = "Sub Title"
        button.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        button.setPadding(20, 20, 20, 20)



        return button
    }

    private fun deleteReminder() {

    }


}