package com.movierepo.repository

import com.movierepo.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface ProductRepository : JpaRepository<Product, Long>{

    fun findByTitle(title: String): Optional<Product>
}