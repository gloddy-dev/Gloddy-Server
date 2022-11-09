package com.gloddy.server.file.service;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FileKeyGenerator {
	public String generate() {
		return UUID.randomUUID()
				   .toString();
	}
}
