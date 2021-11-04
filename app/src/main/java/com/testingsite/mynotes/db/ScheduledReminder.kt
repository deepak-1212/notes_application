package com.testingsite.mynotes.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class ScheduledReminder(
    val reminder_main_id: Int,
    val reminder_day: String,
    val reminder_time: String,
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
