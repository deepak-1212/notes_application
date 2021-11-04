package com.testingsite.mynotes.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Days(
    val name: String,
    val position: Int
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
