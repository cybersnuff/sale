package com.movierepo.service

import com.movierepo.entity.Product
import com.movierepo.repository.ProductRepository
import jakarta.transaction.Transactional
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service

@Service
@Slf4j
@Transactional
class ProductService(
    private val productRepository: ProductRepository
) {

    fun getAll(title: String): List<Product> {

        if (title != null) productRepository.findByTitle(title)

        return productRepository.findAll()
    }

    fun save(product: Product) = productRepository.save(product)

    fun delete(id: Long) = productRepository.deleteById(id)

    fun getById(id: Long) = productRepository.findById(id).orElse(null)

    fun getAllWithoutTitle(): List<Product> = productRepository.findAll()

}
