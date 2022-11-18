package com.numble.backend.common.config.cache;

import lombok.Getter;

@Getter
public enum CacheType {
	GAME("game", 10 * 60, 2000),
	CAFE("cafe", 10 * 60, 500);

	private final String name;
	private final int expireAfterWrite;
	private final int maxSize;

	CacheType(String name, int expireAfterWrite, int maxSize) {
		this.name = name;
		this.expireAfterWrite = expireAfterWrite;
		this.maxSize = maxSize;
	}
}
