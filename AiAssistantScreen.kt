package com.example.studentmind.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studentmind.viewmodel.AiViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiAssistantScreen(
    aiViewModel: AiViewModel,
    onBack: () -> Unit
) {
    val answer by aiViewModel.answer.collectAsState()
    val loading by aiViewModel.loading.collectAsState()

    var userInput by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ИИ-помощник") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("< Назад")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            Text("Опиши свои экзамены / дедлайны и свободное время:")

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = userInput,
                onValueChange = { userInput = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                placeholder = {
                    Text("Например: экзамен по дискретке 15 числа, по Android 20, свободно 3 часа в день...")
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Button(
                    onClick = { aiViewModel.askForStudyPlan(userInput) },
                    enabled = !loading && userInput.isNotBlank()
                ) {
                    Text("Составить план")
                }

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedButton(
                    onClick = { aiViewModel.askForMotivation() },
                    enabled = !loading
                ) {
                    Text("Нужна мотивация")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (loading) {
                CircularProgressIndicator()
            }

            if (!answer.isNullOrBlank()) {
                Text(
                    text = answer ?: "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(top = 16.dp)
                )
            }
        }
    }
}
