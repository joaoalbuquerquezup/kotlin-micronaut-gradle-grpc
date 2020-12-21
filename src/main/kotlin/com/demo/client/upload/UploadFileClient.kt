package com.demo.client.upload

import UploadFileObserver
import com.demo.upload.File
import com.demo.upload.FileServiceGrpc
import com.demo.upload.FileUploadRequest
import com.demo.upload.Metadata
import com.google.protobuf.ByteString
import io.grpc.ManagedChannelBuilder
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

@Throws(IOException::class)
fun main() {
    val channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build()
    val fileServiceStub = FileServiceGrpc.newStub(channel)
    val requestObserver = fileServiceStub.upload(UploadFileObserver())

    val metadata = buildMetadata()
    requestObserver.onNext(metadata)

    // upload bytes
    val path = Paths.get("src/main/resources/input/java_input.pdf")
    val inputStream = Files.newInputStream(path)
    val bytes = ByteArray(4096)
    var size: Int
    while (inputStream.read(bytes).also { size = it } > 0) {
        val uploadRequest = buildFileContent(bytes, size)
        requestObserver.onNext(uploadRequest)
    }
    inputStream.close()
    requestObserver.onCompleted()
    channel.shutdown()
}

fun buildFileContent(bytes: ByteArray, size: Int): FileUploadRequest {
    // Ver ByteString.readChunk etc
    val content = ByteString.copyFrom(bytes, 0, size)
    val file = File.newBuilder().setContent(content).build()
    return FileUploadRequest.newBuilder()
        .setFile(file)
        .build()
}

fun buildMetadata(): FileUploadRequest {
    val fileMetadata = Metadata.newBuilder()
        .setFileName("output")
        .setFileType("pdf")
        .build()

    return FileUploadRequest.newBuilder()
        .setMetadata(fileMetadata)
        .build()
}