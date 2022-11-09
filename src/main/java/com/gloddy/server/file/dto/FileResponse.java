package com.gloddy.server.file.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class FileResponse {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Upload extends FileResponse {

        private List<String> fileUrlList;
    }
}
