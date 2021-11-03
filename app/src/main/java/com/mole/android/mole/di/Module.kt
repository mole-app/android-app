package com.mole.android.mole.di

import java.util.concurrent.ConcurrentHashMap

abstract class Module {

    private val instanceMap: MutableMap<String, Any> = ConcurrentHashMap()

    @Suppress("UNCHECKED_CAST")
    protected fun <T> single(tag: String, factory: () -> T): () -> T {
        return {
            instanceMap.getOrPut(tag) {
                val newInstance = factory()
                newInstance as Any
            } as T
        }
    }
}