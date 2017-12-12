package ar.edu.itba.webapp.auth.hmac;

import org.glassfish.jersey.internal.util.Base64;

import java.time.Instant;

public final class ScrumlrHMACRequestData {
    private static final int CLOCK_WINDOW = 30;

    private final Instant date;
    private final String method;
    private final String bodyDigest;


    private ScrumlrHMACRequestData(final Instant date, final String method, final String bodyDigest) {
        this.date = date;
        this.method = method;
        this.bodyDigest = bodyDigest;
    }

    public static ScrumlrHMACRequestDataBuilder builder() {
        return new ScrumlrHMACRequestDataBuilder();
    }

    private String epochAdjusted() {
        final long epoch = date.getEpochSecond();
        final long epochAdjusted = epoch - epoch % CLOCK_WINDOW + CLOCK_WINDOW;
        return String.valueOf(epochAdjusted);
    }

    @Override
    public String toString() {
        final String dateString = epochAdjusted();
        return Base64.encodeAsString(method + "\n" +
         dateString + "\n" +
         bodyDigest);
    }

    public static class ScrumlrHMACRequestDataBuilder {
        private Instant date;
        private String method;
        private String bodyDigest;

        public final ScrumlrHMACRequestDataBuilder date(final Instant date) {
            this.date = date;
            return this;
        }

        public final ScrumlrHMACRequestDataBuilder method(final String method) {
            this.method = method;
            return this;
        }

        public final ScrumlrHMACRequestDataBuilder bodyDigest(final String bodyDigest) {
            this.bodyDigest = bodyDigest;
            return this;
        }

        public final ScrumlrHMACRequestData build() {
            if (date == null) {
                throw new IllegalArgumentException("date has not been set");
            } else if (method == null) {
                throw new IllegalArgumentException("method has not been set");
            } else if (bodyDigest == null) {
                throw new IllegalArgumentException("Body digest has not been set");
            }
            return new ScrumlrHMACRequestData(date, method.toUpperCase(), bodyDigest);
        }
    }

}
