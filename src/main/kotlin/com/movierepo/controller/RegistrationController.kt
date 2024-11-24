package com.movierepo.controller

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class RegistrationController(
    private val userDetailsService: UserDetailsService,
    private val passwordEncoder: PasswordEncoder
) {

    @GetMapping("/register")
    fun showRegistrationForm(): String {
        return "register"
    }

    @PostMapping("/register")
    fun registerUser(@RequestParam username: String, @RequestParam password: String): String {
        val encodedPassword = passwordEncoder.encode(password)
        val newUser = User.withUsername(username)
            .password(encodedPassword)
            .roles("USER")
            .build()

        val manager = userDetailsService as InMemoryUserDetailsManager
        manager.createUser(newUser)

        return "redirect:/login"
    }
}