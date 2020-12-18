package com.demo.simple

import com.demo.simple.SimpleRequest
import com.demo.simple.SimpleResponse
import com.demo.simple.SimpleServiceGrpcKt
import javax.inject.Singleton

@Singleton
class SimpleEndpoint(private val service: SimpleService) : SimpleServiceGrpcKt.SimpleServiceCoroutineImplBase() {

    override suspend fun send(request: SimpleRequest): SimpleResponse {
        val execute = this.service.execute(request.name)
        println(execute)

        val name = request.name.toUpperCase()
        return SimpleResponse.newBuilder().setMessage(name).build()
    }

}