package com.dothebestmayb.nbc_applemarket.ui.main

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

class SimpleLifecycleOwner: LifecycleOwner {
    private val _lifecycle = LifecycleRegistry(this)

    override val lifecycle: Lifecycle
        get() = _lifecycle

    fun handleLifecycleEvent(event: Lifecycle.Event) {
        _lifecycle.handleLifecycleEvent(event)
    }
}