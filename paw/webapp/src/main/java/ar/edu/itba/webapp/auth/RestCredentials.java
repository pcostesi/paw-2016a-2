package ar.edu.itba.webapp.auth;

import ar.edu.itba.webapp.auth.hmac.ScrumlrHMACRequestData;

public class RestCredentials {
    private final ScrumlrHMACRequestData requestData;
    private final String signature;

    public RestCredentials(final ScrumlrHMACRequestData requestData, final String signature) {
        this.requestData = requestData;
        this.signature = signature;
    }

    public ScrumlrHMACRequestData getRequestData() {
        return requestData;
    }

    public String getSignature() {
        return signature;
    }
}
