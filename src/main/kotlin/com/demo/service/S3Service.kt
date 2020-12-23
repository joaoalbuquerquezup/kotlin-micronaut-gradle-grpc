package com.demo.service

import com.amazonaws.AmazonServiceException
import com.amazonaws.SdkClientException
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import java.io.ByteArrayInputStream
import java.util.UUID
import javax.annotation.PostConstruct
import javax.inject.Singleton

@Singleton
class S3Service() {

    private lateinit var s3Client: AmazonS3

    @PostConstruct
    fun init() {
        // Ler por variável de ambiente
        val endpointConfiguration = AwsClientBuilder.EndpointConfiguration("http://localhost:4566", "us-east-1")
        this.s3Client = AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(endpointConfiguration)
            .withPathStyleAccessEnabled(true)
            .build()
    }

    fun storeFile(fileName: String, fileType: String, bytes: ByteArray): String {
        val completeFileName = "${fileName}-${UUID.randomUUID()}.${fileType}" // talvez colocar timestamp ou data eh melhor

        val objectMetadata = ObjectMetadata()
        objectMetadata.contentLength = bytes.size.toLong()

        val byteArrayInputStream = ByteArrayInputStream(bytes)
        // setar por variavel de ambiente
        val putObjectRequest = PutObjectRequest("mybucket", completeFileName, byteArrayInputStream, objectMetadata)

        try {
            val result = this.s3Client.putObject(putObjectRequest)
        } catch (e: AmazonServiceException) {
            // criar log
            e.printStackTrace();
        } catch (e: SdkClientException) {
            // criar log
            e.printStackTrace();
        }

        // retornar url de acesso do arquivo
        return completeFileName;
    }
}