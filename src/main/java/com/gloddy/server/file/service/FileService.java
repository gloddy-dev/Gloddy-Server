package com.concorn.ozjejakso.application.service;

import static com.ozjejakso.core.error.handler.code.ErrorCode.*;
import static java.util.Objects.*;
import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ozjejakso.core.dto.external.file.FileResponse;
import com.ozjejakso.core.error.handler.exception.FileBusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {
	private static final String DOT = ".";
	private static final String EXTENSION_DELIMITER = "\\.";
	private static final String BUCKET_FOLDER_PATH = "oz/";

	private final FileKeyGenerator fileKeyGenerator;
	private final S3Service s3Service;

	public FileResponse.Upload upload(List<MultipartFile> fileList) {
		List<String> fileUrlList = fileList.stream()
										   .map(this::uploadImageToS3)
										   .collect(toUnmodifiableList());

		return new FileResponse.Upload(fileUrlList);
	}

	private String uploadImageToS3(MultipartFile file) {
		return s3Service.upload(getFileKey(file), file);
	}

	private String getFileKey(MultipartFile file) {
		return BUCKET_FOLDER_PATH + fileKeyGenerator.generate() + DOT + getExtension(file);
	}

	private String getExtension(MultipartFile file) {
		return Arrays.stream(requireNonNull(file.getOriginalFilename()).split(EXTENSION_DELIMITER))
					 .reduce((a, b) -> b)
					 .orElseThrow(() -> new FileBusinessException(FILE_EXTENSION_NOT_FOUND));
	}
}
