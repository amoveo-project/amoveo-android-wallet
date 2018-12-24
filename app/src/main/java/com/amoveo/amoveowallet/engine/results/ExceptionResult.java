package com.amoveo.amoveowallet.engine.results;

public abstract class ExceptionResult<T> extends Result<T> {
    private Exception mException = null;

    public ExceptionResult(Exception exception) {
        super(null);
        mException = exception;
    }

    public ExceptionResult(T result) {
        super(result);
    }

    public Exception getException() {
        return mException;
    }

    public String getExceptionMessage() {
        if (null != mException && null != mException.getLocalizedMessage()) {
            String message = mException.getLocalizedMessage();

            int index = message.indexOf(" ");
            if (message.substring(0, index).contains("xception")) {
                message = message.substring(index).trim();
            }

            return message;
        }

        return "";
    }
}