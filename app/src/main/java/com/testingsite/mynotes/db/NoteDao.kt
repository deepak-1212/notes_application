package com.testingsite.mynotes.db

import androidx.room.*

//Data Access Object
@Dao
interface NoteDao {

    @Insert
    fun insertNote(note: Note)

    @Query("select * from note where archive=0 order by id desc")
    fun getNote() : MutableList<Note>

    @Query("select * from note where archive=1 order by id desc")
    fun getArchivedNote() : MutableList<Note>

    @Query("select * from note where archive=1 and id=:auto_id")
    fun getQueriedArchivedNote(auto_id: Int): MutableList<Note>

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Insert
    fun insertColor(colors: Colors)

    //Days Table
    @Insert
    fun insertDay(days: Days)

    @Query("select count(*) from days")
    fun getDaysCount(): Int

    @Query("select * from days order by position")
    fun getDays(): MutableList<Days>

    @Query("delete from days")
    fun deleteDays()


}