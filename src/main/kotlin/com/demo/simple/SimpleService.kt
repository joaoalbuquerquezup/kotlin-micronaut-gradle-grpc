package com.demo.simple

import javax.inject.Singleton

@Singleton
class SimpleService {

    fun execute(name: String): String {
        return name.toUpperCase()
    }
}