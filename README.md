# Cookie reading and writing for the JDK Http Server

[![javadoc](https://javadoc.io/badge2/dev.mccue/jdk-httpserver-json/javadoc.svg)](https://javadoc.io/doc/dev.mccue/jdk-httpserver-json)
[![Tests](https://github.com/bowbahdoe/jdk-httpserver-json/actions/workflows/test.yml/badge.svg)](https://github.com/bowbahdoe/jdk-httpserver-json/actions/workflows/test.yml)

Utilities for reading and writing cookies with the JDK's [built-in HTTP server](https://docs.oracle.com/en/java/javase/21/docs/api/jdk.httpserver/module-summary.html).

Requires Java 21+

## Dependency Information

### Maven

```xml
<dependency>
    <groupId>dev.mccue</groupId>
    <artifactId>jdk-httpserver-cookies</artifactId>
    <version>2024.05.08</version>
</dependency>
```

### Gradle

```
dependencies {
    implementation("dev.mccue:jdk-httpserver-cookies:2024.05.08")
}
```


## Usage

### Read Cookie Values

```java
var cookies = Cookies.parse(exchange);
var name = cookies.get("name").orElse("Bob");
```

### Make a Set-Cookie header

```java
String header = SetCookieHeader.of("name", "value");
String otherHeader = SetCookieHeader.builder("name2", "value2")
        .sameSite(SameSite.STRICT)
        .secure(true)
        .build();
```

### Full Example

```java
import com.sun.net.httpserver.HttpServer;
import dev.mccue.jdk.httpserver.Body;
import dev.mccue.jdk.httpserver.HttpExchangeUtils;
import dev.mccue.jdk.httpserver.cookies.Cookies;
import dev.mccue.jdk.httpserver.cookies.SetCookieHeader;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

void main() throws IOException {
    var server = HttpServer.create(new InetSocketAddress(8000), 0);
    server.createContext("/", exchange -> {
        var cookies = Cookies.parse(exchange);
        var name = cookies.get("name").orElse("Bob");

        exchange.getResponseHeaders().put("Set-Cookie", List.of(
                SetCookieHeader.of("name", "Joe")
        ));

        HttpExchangeUtils.sendResponse(exchange, 200, Body.of(
                "Hello " + name
        ));
    });
    
    server.start();
}
```
