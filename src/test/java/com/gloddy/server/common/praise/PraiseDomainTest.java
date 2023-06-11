package com.gloddy.server.common.praise;

import com.gloddy.server.UnitTest;
import com.gloddy.server.praise.domain.Praise;

public abstract class PraiseDomainTest extends UnitTest {

    protected Praise getInitPraise() {
        Praise praise = Praise.empty();
        praise.init(getMockUser());
        return praise;
    }
}
