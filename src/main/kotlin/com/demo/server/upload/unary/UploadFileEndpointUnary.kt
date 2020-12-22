package com.demo.server.upload.unary

import com.demo.upload.UploadStatus
import com.demo.upload.unary.UploadFileUnaryRequest
import com.demo.upload.unary.UploadFileUnaryResponse
import com.demo.upload.unary.UploadFileUnaryServiceGrpcKt
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import java.util.UUID
import javax.inject.Singleton

@Singleton
class UploadFileEndpointUnary : UploadFileUnaryServiceGrpcKt.UploadFileUnaryServiceCoroutineImplBase() {

    // checar se é static no bytecode
    // construir o Path melhor File.pathSeparator + String Join
    companion object {
        @JvmField
        val OUTPUT_PATH: Path = Paths.get("src/main/resources/output")
    }

    override suspend fun upload(request: UploadFileUnaryRequest): UploadFileUnaryResponse {

        val completeFileName = "${request.fileName}-${UUID.randomUUID()}.${request.fileType}"
        val pathName = "$OUTPUT_PATH/$completeFileName"

        val content = request.content
        val bytes = content.toByteArray()

        File(pathName).writeBytes(bytes)

        return UploadFileUnaryResponse.newBuilder()
            .setStatus(UploadStatus.SUCCESS)
            .setFileName(completeFileName)
            .build()
    }
}