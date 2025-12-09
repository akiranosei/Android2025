package com.example.studentmind.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.studentmind.viewmodel.TaskViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(
    taskId: Int?,
    viewModel: TaskViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }

    var title by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf(1) } // default: medium
    var deadlineMillis by remember { mutableStateOf(calendar.timeInMillis) }

    // В реальном проекте можно подтянуть задачу из БД, если id != null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (taskId == null) "Новая задача" else "Редактировать задачу") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Название") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = subject,
                onValueChange = { subject = it },
                label = { Text("Предмет") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("Приоритет:")

            Row {
                FilterChip(
                    selected = priority == 0,
                    onClick = { priority = 0 },
                    label = { Text("Низкий") }
                )
                Spacer(modifier = Modifier.width(8.dp))
                FilterChip(
                    selected = priority == 1,
                    onClick = { priority = 1 },
                    label = { Text("Средний") }
                )
                Spacer(modifier = Modifier.width(8.dp))
                FilterChip(
                    selected = priority == 2,
                    onClick = { priority = 2 },
                    label = { Text("Высокий") }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            val dateTimeLabel = remember(deadlineMillis) {
                java.text.SimpleDateFormat("dd.MM.yyyy HH:mm").format(deadlineMillis)
            }

            Button(
                onClick = {
                    val c = Calendar.getInstance().apply { timeInMillis = deadlineMillis }
                    DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            c.set(Calendar.YEAR, year)
                            c.set(Calendar.MONTH, month)
                            c.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                            TimePickerDialog(
                                context,
                                { _, hourOfDay, minute ->
                                    c.set(Calendar.HOUR_OF_DAY, hourOfDay)
                                    c.set(Calendar.MINUTE, minute)
                                    deadlineMillis = c.timeInMillis
                                },
                                c.get(Calendar.HOUR_OF_DAY),
                                c.get(Calendar.MINUTE),
                                true
                            ).show()
                        },
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            ) {
                Text("Выбрать дату и время ($dateTimeLabel)")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.saveTask(taskId, title, subject, deadlineMillis, priority)
                    onBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Сохранить")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Назад")
            }
        }
    }
}
