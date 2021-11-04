package com.testingsite.mynotes.`interface`

import android.widget.TextView
import com.testingsite.mynotes.model.DaysSelectModel

interface DaysBottomSheetCloseInterface {
    fun respond(
        daysSelectedList: ArrayList<DaysSelectModel>,
        textViewCalled: TextView
    )
}