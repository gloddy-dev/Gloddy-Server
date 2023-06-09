package com.gloddy.server.praise.domain.handler;

import com.gloddy.server.praise.domain.Praise;

public interface PraiseQueryHandler {
    Praise findByUserId(Long userId);
}
