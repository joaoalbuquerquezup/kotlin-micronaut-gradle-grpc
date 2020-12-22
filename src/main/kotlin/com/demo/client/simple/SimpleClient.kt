package com.demo.client.simple

import com.demo.simple.SimpleRequest
import com.demo.simple.SimpleServiceGrpc
import com.demo.simple.SimpleServiceGrpcKt
import io.grpc.ManagedChannelBuilder

suspend fun main() {
    val channel = ManagedChannelBuilder.forAddress("localhost", 50051)
        .usePlaintext()
        .build()
    val stub = SimpleServiceGrpcKt.SimpleServiceCoroutineStub(channel)
    val helloResponse = stub.send(SimpleRequest.newBuilder().setName("testando o client").build())
    val message = helloResponse.message
    println(message)
    channel.shutdown()
}

fun mainJavaStub() {
    val channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                                        .usePlaintext()
                                        .build()
    val stub = SimpleServiceGrpc.newBlockingStub(channel)
    val helloResponse = stub.send(SimpleRequest.newBuilder().setName("testando o client").build())
    val message = helloResponse.message
    println(message)
    channel.shutdown()
}