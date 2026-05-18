package com.example.studymate.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.studymate.data.dao.AssignmentDao
import com.example.studymate.data.dao.FlashcardDao
import com.example.studymate.data.model.Assignment
import com.example.studymate.data.model.Flashcard

@Database(
    entities = [Assignment::class, Flashcard::class],
    version = 1,
    exportSchema = false
)
abstract class StudyMateDatabase : RoomDatabase() {

    abstract fun assignmentDao(): AssignmentDao
    abstract fun flashcardDao(): FlashcardDao

    companion object {
        @Volatile
        private var INSTANCE: StudyMateDatabase? = null

        fun getDatabase(context: Context): StudyMateDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudyMateDatabase::class.java,
                    "studymate_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}