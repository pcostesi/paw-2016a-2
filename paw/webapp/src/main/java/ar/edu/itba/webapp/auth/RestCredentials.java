package ar.edu.itba.webapp.auth;

import ar.edu.itba.webapp.auth.hmac.ScrumlrHMACRequestData;

public class RestCredentials {
    private ScrumlrHMACRequestData requestData;
    private String signature;

    public RestCredentials(ScrumlrHMACRequestData requestData, String signature) {
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
