package com.example.studentmind.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentmind.data.AppDatabase
import com.example.studentmind.data.Task
import com.example.studentmind.data.TaskRepository
import com.example.studentmind.notifications.ReminderScheduler
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(app: Application) : AndroidViewModel(app) {

    private val repository: TaskRepository =
        TaskRepository(AppDatabase.getInstance().taskDao())

    val tasksFlow = repository.getAllTasks()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun saveTask(
        id: Int?,
        title: String,
        subject: String,
        deadlineMillis: Long,
        priority: Int
    ) {
        viewModelScope.launch {
            val task = Task(
                id = id ?: 0,
                title = title,
                subject = subject,
                deadlineMillis = deadlineMillis,
                priority = priority,
                isDone = false
            )

            val newId: Int = if (id == null) {
                repository.insert(task).toInt()
            } else {
                repository.update(task)
                id
            }

            // после вставки/обновления – планируем напоминание
            val finalTask = if (id == null) task.copy(id = newId) else task
            ReminderScheduler.scheduleReminder(getApplication(), finalTask)
        }
    }

    fun setTaskDone(task: Task, done: Boolean) {
        viewModelScope.launch {
            repository.update(task.copy(isDone = done))
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.delete(task)
        }
    }
}
