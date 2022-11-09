package com.concorn.ozjejakso.application.service;

import static com.ozjejakso.core.error.handler.code.ErrorCode.FILE_INTERNAL_ERROR;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ozjejakso.core.error.handler.exception.FileBusinessException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class S3Service {

	private final AmazonS3Client s3Client;
	private final String s3BucketName;

	public S3Service(AmazonS3Client s3Client, @Value("${aws.s3.bucket}") String s3BucketName) {
		this.s3Client = s3Client;
		this.s3BucketName = s3BucketName;
	}

	public String upload(String fileKey, MultipartFile file) {
		s3Client.putObject(new PutObjectRequest(s3BucketName,
			fileKey,
			getBytesArrayInputStream(file),
			getObjectMetadata(file))
		);
		return s3Client.getUrl(s3BucketName, fileKey).toString();
	}

	private InputStream getBytesArrayInputStream(MultipartFile file) {
		try {
			return new ByteArrayInputStream(file.getBytes());
		} catch (IOException e) {
			throw new FileBusinessException(FILE_INTERNAL_ERROR);
		}
	}

	private ObjectMetadata getObjectMetadata(MultipartFile file) {
		ObjectMetadata fileMetadata = new ObjectMetadata();
		fileMetadata.setContentLength(file.getSize());
		fileMetadata.setContentType(file.getContentType());
		return fileMetadata;
	}
}
