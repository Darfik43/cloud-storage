package com.darfik.cloudstorage.config.minio;

import com.darfik.cloudstorage.domain.s3storage.props.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@Configuration
@RequiredArgsConstructor
public class MinioBucketConfiguration {

    private final MinioProperties minioProperties;

    @Lazy
    private final MinioClient minioClient;

    @EventListener(ContextRefreshedEvent.class)
    private void createBucket() {
        try {
            if (isBucketExists()) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(minioProperties.getBucket())
                        .build());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error creating bucket", e);
        }
    }

    private boolean isBucketExists() {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Error creating bucket", e);
        }
    }

}
