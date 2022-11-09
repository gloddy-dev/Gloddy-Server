package com.concorn.ozjejakso.application.service;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class FileKeyGenerator {
	public String generate() {
		return UUID.randomUUID()
				   .toString();
	}
}
