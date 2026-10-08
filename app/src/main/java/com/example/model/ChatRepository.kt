package com.example.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ChatRepository {

    suspend fun sendMessage(
        userMessage: String,
        history: List<ChatMessage>
    ): ChatResponseResult = withContext(Dispatchers.IO) {
        // If Gemini API is available and key is configured, call Gemini API
        if (GeminiClient.hasValidApiKey()) {
            try {
                val apiKey = GeminiClient.getApiKey()
                val geminiContents = mutableListOf<GeminiContent>()

                // Add prior messages for conversation context (up to last 6)
                val recentHistory = history.takeLast(6)
                for (msg in recentHistory) {
                    val role = if (msg.sender == MessageSender.USER) "user" else "model"
                    geminiContents.add(
                        GeminiContent(
                            role = role,
                            parts = listOf(GeminiPart(text = msg.text))
                        )
                    )
                }

                // Add current user prompt
                geminiContents.add(
                    GeminiContent(
                        role = "user",
                        parts = listOf(GeminiPart(text = userMessage))
                    )
                )

                val request = GeminiGenerateContentRequest(
                    contents = geminiContents,
                    systemInstruction = GeminiContent(
                        parts = listOf(GeminiPart(text = TransmissionKnowledgeBase.SYSTEM_PROMPT))
                    ),
                    generationConfig = GeminiGenerationConfig(
                        temperature = 0.2f,
                        maxOutputTokens = 1200
                    )
                )

                val response = GeminiClient.apiService.generateContent(
                    model = GeminiClient.MODEL_NAME,
                    apiKey = apiKey,
                    request = request
                )

                if (response.isSuccessful) {
                    val body = response.body()
                    val candidate = body?.candidates?.firstOrNull()
                    val textPart = candidate?.content?.parts?.firstOrNull()?.text
                    if (!textPart.isNullOrBlank()) {
                        val nav = TransmissionKnowledgeBase.findSuggestedNavigation(userMessage, textPart)
                        return@withContext ChatResponseResult(
                            answer = textPart,
                            navigation = nav
                        )
                    }
                }
            } catch (_: Exception) {
                // Gracefully fallback to local knowledge base if network or API error occurs
            }
        }

        // Offline / Built-in High-Accuracy EPRI/IEEE Knowledge Base
        return@withContext TransmissionKnowledgeBase.getLocalOfflineResponse(userMessage)
    }
}
