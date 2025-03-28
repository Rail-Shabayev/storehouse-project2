package com.example.rail.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.example.rail.configuration.S3ConfigProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {
    private final AmazonS3 amazonS3;
    private final S3ConfigProperties amazonS3Properties;
    private final String GLOBAL_PATH = "src/main/resources/images";

    public void uploadFileToBucket(String key, MultipartFile file) {
        String bucketName = amazonS3Properties.getBucketName();
        if (!amazonS3.doesBucketExistV2(bucketName)) {
            amazonS3.createBucket(bucketName);
        }
        PutObjectRequest putObjectRequest = generatePutObjectRequest(key, bucketName, file);
        amazonS3.putObject(putObjectRequest);
    }

    public void downloadFilesFromBucket(List<String> keys, UUID productId) {
        String bucketName = amazonS3Properties.getBucketName();
        List<S3Object> s3ObjectList = keys.stream()
                .map(key -> amazonS3.getObject(bucketName, key))
                .toList();
        String path = String.format("%s/%s.zip", GLOBAL_PATH, productId);
        try (FileOutputStream fileOutputStream = new FileOutputStream(path);
             ZipOutputStream zipOut = new ZipOutputStream(fileOutputStream)) {
            HashMap<String, Integer> names = new HashMap<>();

            for (S3Object s3Object : s3ObjectList) {
                S3ObjectInputStream s3InputStream = s3Object.getObjectContent();
                String decodeFilename = URLDecoder.decode(s3Object.getObjectMetadata().getUserMetaDataOf("filename"), StandardCharsets.UTF_8);
                if (names.containsKey(decodeFilename)) {
                    int lastIndex = decodeFilename.lastIndexOf(".");
                    String duplicateDecodeFilename = decodeFilename.substring(0, lastIndex) +
                            " (" + names.get(decodeFilename) + ")" +
                            decodeFilename.substring(lastIndex);
                    zipOut.putNextEntry(new ZipEntry(duplicateDecodeFilename));
                } else {
                    zipOut.putNextEntry(new ZipEntry(decodeFilename));
                }
                names.merge(decodeFilename, 1, Integer::sum);

                byte[] bytes = new byte[1024];
                int length;
                while ((length = s3InputStream.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }
            }
        } catch (IOException e) {
            log.error("Error while downloading zip: {}", e);
        }

        try (InputStream in = new FileInputStream(path)) {
            log.info("Zip was successfully downloaded");
        } catch (IOException e) {
            log.error("Error while downloading zip: {}", e);
        }
    }

    private PutObjectRequest generatePutObjectRequest(String key, String bucketName, MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        // Хранение картинок с русскими названиями
        String urlEncodedUTF8Filename = URLEncoder.encode(file.getOriginalFilename(), StandardCharsets.UTF_8);
        metadata.addUserMetadata("filename", urlEncodedUTF8Filename);

        PutObjectRequest putObjectRequest;
        try {
            putObjectRequest = new PutObjectRequest(bucketName, key, file.getInputStream(), metadata);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return putObjectRequest;
    }
}
