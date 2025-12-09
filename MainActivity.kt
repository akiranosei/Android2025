package com.example.studentmind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studentmind.ui.Screen
import com.example.studentmind.ui.screens.AiAssistantScreen
import com.example.studentmind.ui.screens.TaskListScreen
import com.example.studentmind.ui.screens.EditTaskScreen
import com.example.studentmind.ui.theme.StudentMindTheme
import com.example.studentmind.viewmodel.AiViewModel
import com.example.studentmind.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {

    private val taskViewModel: TaskViewModel by viewModels()
    private val aiViewModel: AiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StudentMindTheme {
                var currentScreen by remember { mutableStateOf<Screen>(Screen.TaskList) }

                when (val screen = currentScreen) {
                    is Screen.TaskList -> {
                        TaskListScreen(
                            viewModel = taskViewModel,
                            onAddTask = { currentScreen = Screen.EditTask(null) },
                            onEditTask = { id -> currentScreen = Screen.EditTask(id) },
                            onOpenAi = { currentScreen = Screen.AiAssistant }
                        )
                    }
                    is Screen.EditTask -> {
                        EditTaskScreen(
                            taskId = screen.taskId,
                            viewModel = taskViewModel,
                            onBack = { currentScreen = Screen.TaskList }
                        )
                    }
                    is Screen.AiAssistant -> {
                        AiAssistantScreen(
                            aiViewModel = aiViewModel,
                            onBack = { currentScreen = Screen.TaskList }
                        )
                    }
                }
            }
        }
    }
}
