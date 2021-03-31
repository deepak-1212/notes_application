package com.testingsite.mynotes.db

import android.content.ContentValues
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [Note::class],
    version = 3
)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao() : NoteDao

    companion object{

        val migration : Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER Table Note ADD COLUMN archive INTEGER Default 0")
                /*val contentValues = ContentValues()
                contentValues.put("archive", 0)
                database.update("Note")*/
            }
        }

        val migration1 : Migration = object : Migration(2,3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER Table Note ADD COLUMN color TEXT Default ''")
                /*val contentValues = ContentValues()
                contentValues.put("archive", 0)
                database.update("Note")*/
            }
        }

        @Volatile private var instance : NoteDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context : Context) = instance ?: synchronized(LOCK) {
            instance ?: DatabaseBuilder(context).also {
                instance = it
            }
        }

        private fun DatabaseBuilder(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            "mynotes"
        )
            .addMigrations(migration, migration1)
            .build()
    }



}