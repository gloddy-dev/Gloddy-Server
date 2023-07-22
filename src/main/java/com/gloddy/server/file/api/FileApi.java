package com.gloddy.server.file.api;

import com.gloddy.server.file.dto.FileResponse;
import com.gloddy.server.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FileApi {

	private final FileService fileService;

	@PostMapping(value = "/files", consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
	public FileResponse.Upload uploadFileList(@RequestPart List<MultipartFile> fileList) {
		return fileService.upload(fileList);
	}
}
