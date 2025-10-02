package kpaas.dogcat.global.objectStorage;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;
import java.net.URL;

@Component
@Slf4j
@RequiredArgsConstructor
public class ObjectStorageUtil {

    private final S3Client s3Client;

    @Value("${ncp.storage.bucket-name}")
    private String bucketName;

    public String upload(MultipartFile file) {
        // 추후 더 생각해볼점!!!
        if (file == null || file.isEmpty()) {
            return null; // 혹은 default image url 반환
        }

        String fileName = file.getOriginalFilename();

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            log.info("[ File '{}' uploaded to bucket '{}' ]", fileName, bucketName);

            return getFileUrl(fileName); // 업로드 후 접근 가능한 URL 반환
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실패: " + file.getOriginalFilename(), e);
        }
    }

    // 업로드된 파일의 접근 URL 반환
    public String getFileUrl(String key) {
        S3Utilities utilities = s3Client.utilities();
        GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        URL url = utilities.getUrl(getUrlRequest);
        return url.toString();
    }

    // 파일 삭제
    public void delete(String key) {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
            log.info("File [{}] deleted from bucket [{}]", key, bucketName);
        } catch (S3Exception e) {
            throw new RuntimeException("파일 삭제 실패: " + key, e);
        }
    }

}
