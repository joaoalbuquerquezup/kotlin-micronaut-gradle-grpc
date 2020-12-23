package com.demo.server.upload.unary

import com.demo.service.S3Service
import com.demo.upload.UploadStatus
import com.demo.upload.unary.UploadFileUnaryRequest
import com.demo.upload.unary.UploadFileUnaryResponse
import com.demo.upload.unary.UploadFileUnaryServiceGrpcKt
import javax.inject.Singleton

@Singleton
class UploadFileEndpointUnary(private val s3Service: S3Service) :
    UploadFileUnaryServiceGrpcKt.UploadFileUnaryServiceCoroutineImplBase() {

    override suspend fun upload(request: UploadFileUnaryRequest): UploadFileUnaryResponse {

        val content = request.content
        val bytes = content.toByteArray()

        val completeFileName = s3Service.storeFile(request.fileName, request.fileType, bytes)

        return UploadFileUnaryResponse.newBuilder()
            .setStatus(UploadStatus.SUCCESS)
            .setFileName(completeFileName)
            .build()
    }
}