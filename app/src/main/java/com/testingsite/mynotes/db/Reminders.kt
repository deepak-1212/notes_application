package com.testingsite.mynotes.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity
data class Reminders(
    val reminder_title: String,
    val reminder_created_time: String,
    val record_status: String
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
