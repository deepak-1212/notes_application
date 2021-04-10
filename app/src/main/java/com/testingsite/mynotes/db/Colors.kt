package com.testingsite.mynotes.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Colors(
    val color : String,
    val color_hex: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
