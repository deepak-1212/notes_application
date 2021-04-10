package com.testingsite.mynotes.db

import androidx.room.Insert

interface ColorsDao {
    @Insert
    fun insertNote(colors: Colors)
}