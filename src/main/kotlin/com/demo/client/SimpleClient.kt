package com.demo.client

import com.demo.simple.SimpleRequest
import com.demo.simple.SimpleServiceGrpc
import io.grpc.ManagedChannelBuilder

fun main() {
    val channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                                        .usePlaintext()
                                        .build()
    val stub = SimpleServiceGrpc.newBlockingStub(channel)
    val helloResponse = stub.send(SimpleRequest.newBuilder().setName("testando o client").build())
    val message = helloResponse.message
    println(message)
    channel.shutdown()
}