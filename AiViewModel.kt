package com.example.studentmind.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentmind.ai.AiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AiViewModel : ViewModel() {

    private val _answer = MutableStateFlow<String?>(null)
    val answer: StateFlow<String?> = _answer

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun askForStudyPlan(userInput: String) {
        viewModelScope.launch {
            _loading.value = true
            _answer.value = null
            try {
                val prompt = """
                    Ты — умный ассистент для студента.
                    На основе описания ниже составь учебный план по дням с короткими рекомендациями:

                    $userInput
                """.trimIndent()

                val result = AiClient.ask(prompt)
                _answer.value = result
            } catch (e: Exception) {
                _answer.value = "Ошибка: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun askForMotivation() {
        viewModelScope.launch {
            _loading.value = true
            _answer.value = null
            try {
                val prompt = """
                    Ты — поддерживающий и мотивирующий ассистент.
                    У студента много дедлайнов и просроченных задач.
                    Напиши короткий, добрый и немного юмористичный текст поддержки (5–7 предложений).
                """.trimIndent()

                val result = AiClient.ask(prompt)
                _answer.value = result
            } catch (e: Exception) {
                _answer.value = "Ошибка: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}
