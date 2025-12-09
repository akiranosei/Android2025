package com.example.studentmind.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val subject: String,
    val deadlineMillis: Long,
    val priority: Int, // 0 - low, 1 - medium, 2 - high
    val isDone: Boolean = false
)
