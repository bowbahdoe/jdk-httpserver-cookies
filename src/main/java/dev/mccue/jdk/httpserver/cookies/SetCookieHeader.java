package dev.mccue.jdk.httpserver.cookies;

import org.jspecify.annotations.Nullable;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

//https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Set-Cookie
public final class SetCookieHeader {
    static final DateTimeFormatter RFC822_FORMATTER =  DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z")
            .withZone(ZoneId.of("UTC"))
            .withLocale(Locale.US);

    private SetCookieHeader() {}

    public static String of(String name, String value) {
        return builder(name, value).build();
    }

    public static Builder builder(String name, String value) {
        return new Builder(name, value);
    }


    public static final class Builder {
        @Nullable SameSite sameSite;
        @Nullable ZonedDateTime expires;
        @Nullable Duration maxAge;
        boolean secure;
        boolean httpOnly;
        boolean partitioned;
        String name;
        String value;
        @Nullable String path;
        @Nullable String domain;

        Builder(String name, String value) {
            this.sameSite = null;
            this.expires = null;
            this.maxAge = null;
            this.secure = false;
            this.httpOnly = false;
            this.partitioned = false;
            this.name = Objects.requireNonNull(name);
            this.value = Objects.requireNonNull(value);
            this.path = null;
            this.domain = null;
        }

        public Builder sameSite(SameSite sameSite) {
            this.sameSite = sameSite;
            return this;
        }

        public Builder expires(ZonedDateTime expires) {
            this.expires = expires;
            return this;
        }

        public Builder maxAge(Duration maxAge) {
            this.maxAge = maxAge;
            return this;
        }

        public Builder secure(boolean secure) {
            this.secure = secure;
            return this;
        }

        public Builder httpOnly(boolean httpOnly) {
            this.httpOnly = httpOnly;
            return this;
        }

        public Builder partitioned(boolean partitioned) {
            this.partitioned = partitioned;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder domain(String domain) {
            this.domain = domain;
            return this;
        }

        public String build() {
            var sb = new StringBuilder();
            sb.append(name);
            sb.append("=");
            sb.append(URLEncoder.encode(value, StandardCharsets.UTF_8));

            if (sameSite != null) {
                sb.append("; SameSite=");
                sb.append(sameSite.value);
            }
            if (expires != null) {
                sb.append("; Expires=");
                sb.append(RFC822_FORMATTER.format(expires));
            }

            if (maxAge != null) {
                sb.append("; Max-Age=");
                sb.append(maxAge.toSeconds());
            }
            if (secure) {
                sb.append("; Secure");
            }
            if (httpOnly) {
                sb.append("; HttpOnly");
            }
            if (partitioned) {
                sb.append("; Partitioned");
            }
            if (path != null) {
                sb.append("; Path=");
                sb.append(path);
            }
            if (domain != null) {
                sb.append("; Domain=");
                sb.append(domain);
            }

            return sb.toString();
        }

        @Override
        public String toString() {
            return "SetCookieHeader$Builder[" +
                   "sameSite=" + sameSite +
                   ", expires=" + expires +
                   ", maxAge=" + maxAge +
                   ", secure=" + secure +
                   ", httpOnly=" + httpOnly +
                   ", partitioned=" + partitioned +
                   ", name='" + name + '\'' +
                   ", value='" + value + '\'' +
                   ", path='" + path + '\'' +
                   ", domain='" + domain + '\'' +
                   ']';
        }
    }
}
