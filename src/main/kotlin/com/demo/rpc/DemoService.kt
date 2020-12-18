package com.demo.rpc

import javax.inject.Singleton

@Singleton
class DemoService {

    fun execute(name: String): String {
        return name.toUpperCase()
    }
}