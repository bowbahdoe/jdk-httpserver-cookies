module dev.mccue.jdk.httpserver.cookies {
    requires static org.jspecify;

    requires transitive dev.mccue.jdk.httpserver;
    requires jdk.httpserver;

    exports dev.mccue.jdk.httpserver.cookies;
}