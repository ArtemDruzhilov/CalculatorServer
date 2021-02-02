package com.test.calc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class IntegrationTest {

    private static final String HOST = "localhost";

    @Value("${tcp.server.port}")
    private int port;

    @Test
    public void shouldPerformCalculationsForSingleClient() throws IOException {
        // given
        final var expressions = List.of("5+6", "some incorrect expression", "34-2", "1/0");
        final var client = createClient(HOST, port);

        // when
        final var result = client.executeExpressions(expressions);

        // then
        assertEquals(List.of("11.0", "Expression error", "32.0", "NaN"), result);
    }

    @Test
    public void shouldPerformCalculationsForMultipleClients() throws ExecutionException, InterruptedException {
        // given
        final var expressions = List.of("5+6", "some incorrect expression", "34-2", "1/0");
        final var clients = IntStream.range(0, 100)
                .mapToObj(i -> createClient(HOST, port))
                .collect(Collectors.toList());
        final var customThreadPool = new ForkJoinPool();

        // when
        final var results = customThreadPool.submit(() -> clients.parallelStream()
                .map(client -> executeExpressions(client, expressions))
                .collect(Collectors.toList()))
                .get();

        // then
        assertEquals(100, results.size());
        results.forEach(result -> assertEquals(List.of("11.0", "Expression error", "32.0", "NaN"), result));
    }

    private List<String> executeExpressions(TelnetClient client, List<String> expressions) {
        try {
            return client.executeExpressions(expressions);
        } catch (IOException e) {
            throw new RuntimeException("Connection lost");
        }
    }

    private TelnetClient createClient(String host, int port) {
        try {
            return new TelnetClient(host, port);
        } catch (IOException e) {
            throw new RuntimeException("Can't establish connection to remote server");
        }
    }
}
