package com.amoveo.amoveowallet.presenters.results;

import com.amoveo.amoveowallet.common.CacheBean;
import com.amoveo.amoveowallet.engine.results.ExceptionResult;

public class CacheResult extends ExceptionResult<CacheBean> {
    public CacheResult(Exception exception) {
        super(exception);
    }

    public CacheResult(CacheBean result) {
        super(result);
    }
}
