package com.movierepo.controller


import com.movierepo.entity.Product
import com.movierepo.repository.ProductRepository
import com.movierepo.service.ProductService
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Controller
@Transactional
class ProductController(
    private val productService: ProductService,
    private val productRepository: ProductRepository
) {

    @GetMapping("/product/{id}")
    fun getProduct(@PathVariable id: Long, model: Model): String {
        val product = productService.getById(id)
        model.addAttribute("product", product)
        return "product-info"
    }

    @GetMapping("/")
    fun product(@RequestParam(name = "title", required = false) title: String?, model: Model): String {
        val products = title?.let { productService.getAll(it) }
        model.addAttribute("products", products)
        return "products"
    }

    @PostMapping("product/create")
    fun create(
        @RequestParam title: String,
        @RequestParam description: String,
        @RequestParam price: Int,
        @RequestParam city: String,
        @RequestParam author: String,
        @RequestParam image: MultipartFile?
    ): String {
        val imageBytes = image?.bytes?.let { convertImageToByteArray(it) }

        val product = Product(
            title = title,
            description = description,
            price = price,
            city = city,
            author = author,
            image = imageBytes
        )

        productService.save(product)

        return "redirect:/"
    }

    @DeleteMapping("/products/delete/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        productService.delete(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @GetMapping("/product/all")
    @Transactional
    fun getAllProducts(model: Model): String {
        val products = productService.getAllWithoutTitle()
        model.addAttribute("products", products)
        return "all"
    }

    @GetMapping("/{id}/image")
    fun getImage(@PathVariable id: Long): ResponseEntity<ByteArray> {
        val product = productRepository.findById(id).orElseThrow { RuntimeException("Product not found") }

        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_JPEG)
            .body(product.image)
    }

    fun convertImageToByteArray(imageData: ByteArray): ByteArray {
        return imageData
    }

    @GetMapping("/products/{id}/image")
    @ResponseBody
    fun getProductImage(@PathVariable id: Long): ResponseEntity<ByteArray> {
        val product = productRepository.getById(id)
        return if (product?.image != null) {
            ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(product.image)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}