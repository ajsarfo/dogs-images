package com.sarftec.dogs.tools.extra

class Event<out T>(private val item: T) {

    private var isHandled: Boolean = false

    fun getIfNotHandled() : T? {
        if(isHandled) return null
        isHandled = true
        return item
    }

    fun peek() : T = item
}