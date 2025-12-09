package com.example.studentmind.ai

import com.google.ai.client.generativeai.GenerativeModel
import com.example.studentmind.BuildConfig

object AiClient {

    private val model by lazy {
        GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = BuildConfig.GOOGLE_AI_API_KEY
        )
    }

    suspend fun ask(prompt: String): String {
        val response = model.generateContent(prompt)
        return response.text ?: "Не удалось получить ответ ИИ 😅"
    }
}
