package com.demo.upload

import com.demo.upload.FileServiceGrpc.FileServiceImplBase
import com.google.protobuf.ByteString
import io.grpc.stub.StreamObserver
import java.io.IOException
import java.io.OutputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.UUID

class FileUploadEndpoint : FileServiceImplBase() {

    override fun upload(responseObserver: StreamObserver<FileUploadResponse>): StreamObserver<FileUploadRequest> {

        return object : StreamObserver<FileUploadRequest> {

            var writer: OutputStream? = null // como melhorar isso?
            var status = UploadStatus.IN_PROGRESS
            var fileName: String? = null // como melhorar?

            override fun onNext(fileUploadRequest: FileUploadRequest) {
                try {
                    if (fileUploadRequest.hasMetadata()) { // garantir uma unica execucao
                        writer = getFilePath(fileUploadRequest)
                    } else {
                        writeFile(writer, fileUploadRequest.file.content)
                    }
                } catch (e: IOException) {
                    onError(e)
                }
            }

            override fun onError(throwable: Throwable) {
                status = UploadStatus.FAILED
                onCompleted()
            }

            override fun onCompleted() {
                closeFile(writer)
                status = if (UploadStatus.IN_PROGRESS == status) UploadStatus.SUCCESS else status
                val response = FileUploadResponse.newBuilder()
                    .setName(this.fileName)
                    .setStatus(status)
                    .build()
                responseObserver.onNext(response)
                responseObserver.onCompleted()
            }

            @Throws(IOException::class)
            private fun getFilePath(request: FileUploadRequest): OutputStream {
                val completeFileName =
                    "$request.metadata.fileName.$request.metadata.fileType" // Como concatenar melhor?
                val randomUUID = UUID.randomUUID()
                this.fileName = "$completeFileName-$randomUUID"
                return Files.newOutputStream(
                    SERVER_BASE_PATH.resolve(completeFileName),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
                )
            }
        }
    }

    @Throws(IOException::class)
    private fun writeFile(writer: OutputStream?, content: ByteString) {
        writer!!.write(content.toByteArray())
        writer.flush()
    }

    private fun closeFile(writer: OutputStream?) {
        try {
            writer!!.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private val SERVER_BASE_PATH = Paths.get("src/test/resources/output")
    }
}