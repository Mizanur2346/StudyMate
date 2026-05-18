package com.example.studymate.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assignments")
data class Assignment(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val subject: String,
    val dueDate: String,
    val priority: String, // "High", "Medium", "Low"
    val isCompleted: Boolean = false
)