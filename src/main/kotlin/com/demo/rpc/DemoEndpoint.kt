package com.demo.rpc

import com.demo.SimpleRequest
import com.demo.SimpleResponse
import com.demo.SimpleServiceGrpcKt
import javax.inject.Singleton

@Singleton
class DemoEndpoint: SimpleServiceGrpcKt.SimpleServiceCoroutineImplBase() {

    override suspend fun send(request: SimpleRequest): SimpleResponse {
        val name = request.name.toUpperCase()
        return SimpleResponse.newBuilder().setMessage(name).build()
    }


}