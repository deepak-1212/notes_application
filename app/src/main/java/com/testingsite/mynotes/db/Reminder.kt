package com.testingsite.mynotes.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Reminder(
    val reminder_main_id: Int,
    val reminder_sub_title: String,
    val reminder_repeat: String
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
