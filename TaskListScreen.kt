package com.example.studentmind.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studentmind.data.Task
import com.example.studentmind.viewmodel.TaskViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    viewModel: TaskViewModel,
    onAddTask: () -> Unit,
    onEditTask: (Int) -> Unit,
    onOpenAi: () -> Unit
) {
    val tasks by viewModel.tasksFlow.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("StudentMind — задачи") },
                actions = {
                    IconButton(onClick = onOpenAi) {
                        Text("ИИ")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTask) {
                Text("+")
            }
        }
    ) { padding ->
        if (tasks.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
            ) {
                Text(
                    text = "Пока задач нет. Самое время что-нибудь запланировать 👀",
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(tasks) { task ->
                    TaskItem(
                        task = task,
                        onClick = { onEditTask(task.id) },
                        onDoneChanged = { done -> viewModel.setTaskDone(task, done) },
                        onDelete = { viewModel.deleteTask(task) }
                    )
                }
            }
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onClick: () -> Unit,
    onDoneChanged: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(task.title, style = MaterialTheme.typography.titleMedium)
                Text(
                    when (task.priority) {
                        2 -> "Высокий"
                        1 -> "Средний"
                        else -> "Низкий"
                    },
                    style = MaterialTheme.typography.labelMedium
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text("Предмет: ${task.subject}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Дедлайн: ${java.text.SimpleDateFormat("dd.MM HH:mm").format(task.deadlineMillis)}")

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row {
                    Checkbox(
                        checked = task.isDone,
                        onCheckedChange = onDoneChanged
                    )
                    Text("Выполнено")
                }
                TextButton(onClick = onDelete) {
                    Text("Удалить")
                }
            }
        }
    }
}
