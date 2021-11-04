package com.testingsite.mynotes.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [Note::class, Colors::class, Reminder::class, Reminders::class, ScheduledReminder::class, Days::class],
    version = 6,
    exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

    companion object {

        val migration_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER Table Note ADD COLUMN archive INTEGER Default 0")
                /*val contentValues = ContentValues()
                contentValues.put("archive", 0)
                database.update("Note")*/
            }
        }

        val migration_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER Table Note ADD COLUMN color TEXT Default ''")
                /*val contentValues = ContentValues()
                contentValues.put("archive", 0)
                database.update("Note")*/
            }
        }

        val migration_3_4: Migration = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS Colors (id INTEGER PRIMARY KEY NOT NULL, color TEXT NOT NULL, color_hex TEXT NOT NULL)")

            }
        }

        val migration_4_5: Migration = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS Reminder (id INTEGER PRIMARY KEY NOT NULL, reminder_main_id INTEGER NOT NULL, reminder_sub_title TEXT NOT NULL, reminder_repeat TEXT NOT NULL)")
                database.execSQL("CREATE TABLE IF NOT EXISTS Reminders (id INTEGER PRIMARY KEY NOT NULL, reminder_title TEXT NOT NULL, reminder_created_time TEXT NOT NULL, record_status TEXT NOT NULL)")
                database.execSQL("CREATE TABLE IF NOT EXISTS ScheduledReminder (id INTEGER PRIMARY KEY NOT NULL, reminder_main_id INTEGER NOT NULL, reminder_day TEXT NOT NULL, reminder_time TEXT NOT NULL)")
                database.execSQL("CREATE TABLE IF NOT EXISTS Days (id INTEGER PRIMARY KEY NOT NULL, name TEXT NOT NULL)")
            }
        }

        //insert record in Days table- Not in use
        /*val migration_5_6: Migration = object : Migration(5, 6) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.insert("Days", 0, ContentValues().apply {
                    put("name", "Sunday")
                })
                database.insert("Days", 0, ContentValues().apply {
                    put("name", "Monday")
                })
                database.insert("Days", 0, ContentValues().apply {
                    put("name", "Tuesday")
                })
                database.insert("Days", 0, ContentValues().apply {
                    put("name", "Wednesday")
                })
                database.insert("Days", 0, ContentValues().apply {
                    put("name", "Thursday")
                })
                database.insert("Days", 0, ContentValues().apply {
                    put("name", "Friday")
                })
                database.insert("Days", 0, ContentValues().apply {
                    put("name", "Saturday")
                })
                database.insert("Days", 0, ContentValues().apply {
                    put("name", "Sunday")
                })
                *//*database.execSQL("INSERT INTO Days (`name`) VALUES ('Sunday')")
                database.execSQL("INSERT INTO Days (`name`) VALUES ('Monday')")
                database.execSQL("INSERT INTO Days (`name`) VALUES ('Tuesday')")
                database.execSQL("INSERT INTO Days (`name`) VALUES ('Wednesday')")
                database.execSQL("INSERT INTO Days (`name`) VALUES ('Thursday')")
                database.execSQL("INSERT INTO Days (`name`) VALUES ('Friday')")
                database.execSQL("INSERT INTO Days (`name`) VALUES ('Saturday')")*//*

            }
        }*/

        val migration_5_6: Migration = object : Migration(5, 6) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER Table Days ADD COLUMN position INTEGER NOT NULL DEFAULT 0")
            }
        }

        @Volatile
        private var instance: NoteDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: DatabaseBuilder(context).also {
                instance = it
            }
        }

        private fun DatabaseBuilder(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            "mynotes"
        )
            .addMigrations(
                migration_1_2,
                migration_2_3,
                migration_3_4,
                migration_4_5,
                migration_5_6
            )
            .build()


    }
}