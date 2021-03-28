package com.testingsite.mynotes.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Note(
    val title : String,
    val message : String
):Serializable{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}