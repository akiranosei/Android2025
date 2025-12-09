package com.example.studentmind.ui

sealed class Screen {
    object TaskList : Screen()
    data class EditTask(val taskId: Int?) : Screen()
    object AiAssistant : Screen()
}
