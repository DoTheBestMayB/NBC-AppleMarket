package com.dothebestmayb.nbc_applemarket.data

import com.dothebestmayb.nbc_applemarket.model.Product

object ProductManager {
    private val products = hashMapOf<Int, Product>()

    fun addProduct(newProducts: List<Product>) {
        for (product in newProducts) {
            products[product.uuid] = product
        }
    }

    fun addProduct(product: Product) {
        products[product.uuid] = product
    }

    fun getAllProducts() = products.values.toList()
    fun removeProduct(product: Product) {
        products.remove(product.uuid)
    }
}