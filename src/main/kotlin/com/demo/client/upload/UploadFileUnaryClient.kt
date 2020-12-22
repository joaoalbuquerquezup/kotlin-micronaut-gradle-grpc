package com.demo.client.upload

import com.demo.upload.unary.UploadFileUnaryRequest
import com.demo.upload.unary.UploadFileUnaryServiceGrpcKt
import com.google.protobuf.ByteString
import io.grpc.ManagedChannelBuilder
import java.nio.file.Files
import java.nio.file.Paths

suspend fun main() {
    val channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build()
    val stub = UploadFileUnaryServiceGrpcKt.UploadFileUnaryServiceCoroutineStub(channel)

    val path = Paths.get("src/main/resources/input/java_input.pdf")
    val inputStream = Files.newInputStream(path)
    val bytes = inputStream.readAllBytes() // APENAS PARA TESTE. Não é pra usar alocando todos os bytes na memória
    inputStream.close()

    val request = buildRequest(bytes)
    val response = stub.upload(request)

    println(response.fileName)
    println(response.status)

    channel.shutdown()
}

private fun buildRequest(bytes: ByteArray): UploadFileUnaryRequest {
    val content = ByteString.copyFrom(bytes)
    return UploadFileUnaryRequest.newBuilder()
        .setFileName("output")
        .setFileType("pdf")
        .setContent(content)
        .build()
}