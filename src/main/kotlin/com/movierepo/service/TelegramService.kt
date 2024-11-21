package com.movierepo.service

import com.movierepo.controller.Feedback
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class TelegramService {

    @Value("\${bot.token}")
    private lateinit var botToken: String

    @Value("\${bot.chatId}")
    private lateinit var chatId: String

    private val restTemplate = RestTemplate()

    fun sendMessage(feedback: Feedback) {
        val message = """
            Новое сообщение обратной связи:
            Имя: ${feedback.name}
            Контакт: ${feedback.contact}
            Сообщение: ${feedback.message}
        """.trimIndent()

        val url = "https://api.telegram.org/bot$botToken/sendMessage"
        val params = mapOf(
            "chat_id" to chatId,
            "text" to message
        )

        restTemplate.postForObject(url, params, String::class.java)
    }
}