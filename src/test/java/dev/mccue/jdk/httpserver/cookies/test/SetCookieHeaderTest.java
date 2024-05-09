package dev.mccue.jdk.httpserver.cookies.test;

import dev.mccue.jdk.httpserver.cookies.SameSite;
import dev.mccue.jdk.httpserver.cookies.SetCookieHeader;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SetCookieHeaderTest {
    @Test
    public void testBasic() {
        assertEquals(
                "id=abc123",
                SetCookieHeader.of("id", "abc123")
        );
    }

    @Test
    public void testMaxAge() {
        assertEquals(
                "id=abc123; Max-Age=123",
                SetCookieHeader.builder("id", "abc123")
                        .maxAge(Duration.ofSeconds(123))
                        .build()
        );
    }

    @Test
    public void testExpires() {
        assertEquals(
                "id=abc123; Expires=Mon, 20 Nov 2023 01:22:07 +0000",
                SetCookieHeader.builder("id", "abc123")
                        .expires(
                                ZonedDateTime.of(
                                        LocalDateTime.of(
                                                2023,
                                                Month.NOVEMBER,
                                                19,
                                                20,
                                                22,
                                                7,
                                                0
                                        ),
                                        ZoneId.of("America/New_York")
                                )
                                // Mon, 20 Nov 2023 01:21:07 +0000
                        )
                        .build()
        );
    }

    @Test
    public void testOtherValues() {
        var cookie = SetCookieHeader.builder("id", "abc")
                .domain("a")
                .path("/a/b/c")
                .httpOnly(true)
                .secure(true)
                .partitioned(true)
                .sameSite(SameSite.STRICT)
                .build();
        assertEquals(
                "id=abc; SameSite=Strict; Secure; HttpOnly; Partitioned; Path=/a/b/c; Domain=a",
                cookie
        );
    }
}