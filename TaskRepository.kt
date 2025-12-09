package com.example.studentmind.data

import kotlinx.coroutines.flow.Flow

class TaskRepository(private val dao: TaskDao) {

    fun getAllTasks(): Flow<List<Task>> = dao.getAllTasks()

    suspend fun getTaskById(id: Int): Task? = dao.getTaskById(id)

    suspend fun insert(task: Task): Long = dao.insert(task)

    suspend fun update(task: Task) = dao.update(task)

    suspend fun delete(task: Task) = dao.delete(task)
}
