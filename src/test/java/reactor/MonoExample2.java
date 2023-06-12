package reactor;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import java.net.URI;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

public class MonoExample2 {

    @Test
    void test1(){
        URI worldTimeUri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("worldtimeapi.org")
                .port(80)
                .path("/api/timezone/Asia/Seoul")
                .build()
                .encode()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Mono.just(
                restTemplate
                        .exchange(worldTimeUri,
                                HttpMethod.GET,
                                new HttpEntity<String>(headers),
                                String.class)
        ).map(response -> {
            DocumentContext jsonContext = JsonPath.parse(response.getBody());
            String dateTime = jsonContext.read("$.datetime");
            return dateTime;
        }).subscribe(
                data -> System.out.println("emitted data : " + data),
                error -> {
                    System.out.println(error);
                },
                () -> System.out.println("# onComplete signal")
        );
    }
}
