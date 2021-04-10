package com.testingsite.mynotes.db

import android.content.ContentValues
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [Note::class, Colors::class],
    version = 4
)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao() : NoteDao

    companion object{

        /*val migration_1_2 : Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER Table Note ADD COLUMN archive INTEGER Default 0")
                *//*val contentValues = ContentValues()
                contentValues.put("archive", 0)
                database.update("Note")*//*
            }
        }

        val migration_2_3 : Migration = object : Migration(2,3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER Table Note ADD COLUMN color TEXT Default ''")
                *//*val contentValues = ContentValues()
                contentValues.put("archive", 0)
                database.update("Note")*//*
            }
        }*/

        val migration_3_4 : Migration = object : Migration(3,4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS Colors (id INTEGER PRIMARY KEY NOT NULL, color TEXT NOT NULL, color_hex TEXT NOT NULL)")

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
            .addMigrations(/*migration_1_2, migration_2_3, */migration_3_4)
            .build()
    }



}