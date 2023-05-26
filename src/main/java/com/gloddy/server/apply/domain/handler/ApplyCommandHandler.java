package com.gloddy.server.apply.domain.handler;

import com.gloddy.server.apply.domain.Apply;

public interface ApplyCommandHandler {
    Apply save(Apply apply);
}
