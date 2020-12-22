package com.demo.server.upload

import com.demo.upload.UploadFileRequest
import com.demo.upload.UploadFileResponse
import com.demo.upload.UploadFileServiceGrpcKt
import com.demo.upload.UploadStatus
import java.nio.file.Paths
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class UploadFileEndpoint : UploadFileServiceGrpcKt.UploadFileServiceCoroutineImplBase() {

    val SERVER_BASE_PATH = Paths.get("src/main/resources/output")

    override suspend fun upload(requests: Flow<UploadFileRequest>): UploadFileResponse {
        print("alooo")
        return UploadFileResponse.newBuilder().setName("teste").setStatus(UploadStatus.SUCCESS).build();
    }

}