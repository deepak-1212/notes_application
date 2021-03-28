package com.testingsite.mynotes.db

import androidx.room.*

//Data Access Object
@Dao
interface NoteDao {

    @Insert
    fun insertNote(note: Note)

    @Query("select * from note order by id desc")
    fun getNote() : List<Note>

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

}