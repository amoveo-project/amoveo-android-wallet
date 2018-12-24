package com.amoveo.amoveowallet.presenters.results;

import com.amoveo.amoveowallet.common.ContentBean;
import com.amoveo.amoveowallet.engine.results.ExceptionResult;

public class ContentResult extends ExceptionResult<ContentBean> {
    public ContentResult(ContentBean result) {
        super(result);
    }

    public ContentResult(Exception exception) {
        super(exception);
    }
}