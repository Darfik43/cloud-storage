package com.darfik.cloudstorage.config;

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
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class MinioConfiguration {

    private final MinioProperties minioProperties;
    private final MinioClient minioClient;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioProperties.getUrl())
                .credentials(minioProperties.getAccessKey(),
                        minioProperties.getSecretKey())
                .build();
    }

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
