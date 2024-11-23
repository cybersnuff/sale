package com.movierepo.controller

import com.movierepo.service.TelegramService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/feedback")
class FeedbackController(
    private val telegramService: TelegramService
) {

    @GetMapping
    fun showFeedbackForm(): String {
        return "feedbackForm"
    }

    @PostMapping
    fun submitFeedback(@ModelAttribute feedback: Feedback, model: Model): String {
        telegramService.sendMessage(feedback)
        model.addAttribute("message", "Ваше сообщение успешно отправлено!")
        return "confirmation"
    }

    @GetMapping("/all")
    fun showProducts(): String {
        return "all"
    }
}

data class Feedback(
    val name: String,
    val contact: String,
    val message: String
)