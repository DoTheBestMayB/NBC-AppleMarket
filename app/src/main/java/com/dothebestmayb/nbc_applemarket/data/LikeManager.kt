package com.dothebestmayb.nbc_applemarket.data

import com.dothebestmayb.nbc_applemarket.model.Product
import com.dothebestmayb.nbc_applemarket.model.User

object LikeManager {
    private val likes: HashMap<String, LinkedHashSet<Product>> = hashMapOf() // key: user nickname

    fun add(user: User, product: Product): Product {
        val updatedProduct = product.copy(like = product.like + 1)
        likes[user.nickname] = likes.getOrDefault(user.nickname, linkedSetOf()).apply {
            add(updatedProduct)
        }
        return updatedProduct
    }

    fun remove(user: User, product: Product): Product {
        likes[user.nickname]?.remove(product)
        return product.copy(like = product.like - 1)
    }

    fun checkLike(user: User, product: Product): Boolean {
        return likes[user.nickname]?.contains(product) == true
    }
}