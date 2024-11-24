package com.movierepo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@Configuration
class UserConfig {

    @Bean
    fun userDetailsService(passwordEncoder: PasswordEncoder): UserDetailsService {
        val user = User.withUsername("user")
            .password(passwordEncoder.encode("password"))
            .roles("USER")
            .build()

        val admin = User.withUsername("admin")
            .password(passwordEncoder.encode("admin"))
            .roles("ADMIN")
            .build()

        return InMemoryUserDetailsManager(user, admin)
    }
}