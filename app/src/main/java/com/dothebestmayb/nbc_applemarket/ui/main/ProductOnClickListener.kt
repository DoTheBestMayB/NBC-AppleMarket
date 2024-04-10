package com.dothebestmayb.nbc_applemarket.ui.main

import com.dothebestmayb.nbc_applemarket.model.Product

interface ProductOnClickListener {

    fun onClick(product: Product)

    fun onLongClick(product: Product)
}