import com.demo.upload.FileUploadResponse
import io.grpc.stub.StreamObserver

class UploadFileObserver : StreamObserver<FileUploadResponse> {
    override fun onNext(fileUploadResponse: FileUploadResponse) {
        println(
            "File upload status :: " + fileUploadResponse.status
        )
    }

    override fun onError(throwable: Throwable) {}
    override fun onCompleted() {}
}