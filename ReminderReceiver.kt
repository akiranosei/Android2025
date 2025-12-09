package com.example.studentmind.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.studentmind.data.AppDatabase
import com.example.studentmind.data.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val taskId = intent.getIntExtra("task_id", -1)
        if (taskId == -1) return

        val db = AppDatabase.getInstance()
        val dao = db.taskDao()

        CoroutineScope(Dispatchers.IO).launch {
            val task: Task? = dao.getTaskById(taskId)
            task?.let {
                NotificationUtil.showTaskNotification(context, it)
            }
        }
    }
}
