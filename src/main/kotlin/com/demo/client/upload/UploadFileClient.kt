package com.demo.client.upload

import com.demo.upload.UploadFileServiceGrpcKt
import io.grpc.ManagedChannelBuilder

fun main() {
    val channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build()
    val stub = UploadFileServiceGrpcKt.UploadFileServiceCoroutineStub(channel)
    // stub.upload()
}