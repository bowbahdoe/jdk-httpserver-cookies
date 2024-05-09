package dev.mccue.jdk.httpserver.cookies;

public enum SameSite {
    STRICT("Strict"),
    LAX("Lax"),
    NONE("None");

    final String value;

    SameSite(String value) {
        this.value = value;
    }
}
