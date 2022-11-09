package com.gloddy.server.file.service;

import com.gloddy.server.core.error.handler.exception.FileBusinessException;
import com.gloddy.server.file.dto.FileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static com.gloddy.server.core.error.handler.errorCode.ErrorCode.FILE_EXTENSION_NOT_FOUND;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toUnmodifiableList;

@Service
@RequiredArgsConstructor
public class FileService {
	private static final String DOT = ".";
	private static final String EXTENSION_DELIMITER = "\\.";
	private static final String BUCKET_FOLDER_PATH = "file/";

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
