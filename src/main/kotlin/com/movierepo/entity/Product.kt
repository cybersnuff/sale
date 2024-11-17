package com.movierepo.entity

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data

@Data
@AllArgsConstructor
@Entity
@Table(name = "product")
data class Product(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "title")
    var title: String,

    @Column(name = "description", columnDefinition = "text")
    var description: String,

    @Column(name = "price")
    val price: Int,

    @Column(name = "city")
    var city: String,

    @Column(name = "author")
    var author: String,

    @Lob
    @Column(name = "image")
    var image: ByteArray? = null
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Product

        if (id != other.id) return false
        if (title != other.title) return false
        if (description != other.description) return false
        if (price != other.price) return false
        if (city != other.city) return false
        if (author != other.author) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + title.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + price
        result = 31 * result + city.hashCode()
        result = 31 * result + author.hashCode()
        result = 31 * result + (image?.contentHashCode() ?: 0)
        return result
    }
}
