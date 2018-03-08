package org.superbiz.moviefun.albums;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.superbiz.moviefun.blobstore.BlobStore;
import org.superbiz.moviefun.blobstore.S3Store;

@SpringBootApplication
public class AlbumServiceApplication {
    public static void main(String... args) {
        SpringApplication.run(AlbumServiceApplication.class, args);
    }

    @Value("${s3.endpointUrl:x}") String s3EndpointUrl;
    @Value("${vcap.services.moviefun-s3.credentials.access_key_id}") String s3AccessKey;
    @Value("${vcap.services.moviefun-s3.credentials.secret_access_key}") String s3SecretKey;
    @Value("${vcap.services.moviefun-s3.credentials.bucket}") String s3BucketName;

    @Bean
    public BlobStore blobStore() {
        AWSCredentials credentials = new BasicAWSCredentials(s3AccessKey, s3SecretKey);
        AmazonS3Client s3Client = new AmazonS3Client(credentials);
        //s3Client.setEndpoint(s3EndpointUrl);

        return new S3Store(s3Client, s3BucketName);
    }
}
