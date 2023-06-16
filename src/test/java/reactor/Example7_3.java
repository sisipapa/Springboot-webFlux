package reactor;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Slf4j
public class Example7_3 {

    @Test
    void test1() throws Exception{
        URI worldTimeUri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("worldtimeapi.org")
                .port(80)
                .path("/api/timezone/Asia/Seoul")
                .build()
                .encode()
                .toUri();

        Mono<String> mono = getWorldTIme(worldTimeUri);
        mono.subscribe(dateTime -> System.out.println("# dateTime 1: " + dateTime));
        Thread.sleep(2000);
        mono.subscribe(dateTime -> System.out.println("# dateTime 2: " + dateTime));
        Thread.sleep(2000);

    }

    private Mono<String> getWorldTIme(URI worldTimeUri) {
        return WebClient.create()
                .get()
                .uri(worldTimeUri)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    DocumentContext jsonContext = JsonPath.parse(response);
                    return jsonContext.read("$.datetime");
                });
    }
}
