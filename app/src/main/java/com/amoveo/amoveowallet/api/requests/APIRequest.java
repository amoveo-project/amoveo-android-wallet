package com.amoveo.amoveowallet.api.requests;

public abstract class APIRequest<O> {
    public abstract O getBody();
}