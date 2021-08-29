package com.sisipapa.study.webflux.handler;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyExtractors.toFlux;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class HelloHandler {

    @Bean
    public RouterFunction<ServerResponse> routes() {
        return route(GET("/handler"), this::hello)
                .andRoute(GET("/handler/stream"), this::stream)
                .andRoute(POST("/handler/echo"), this::echo)
                .andRoute(POST("/handler/stream"), this::postStream);
    }

    public Mono<ServerResponse> hello(ServerRequest req) {
        return ok().body(Flux.just("Hello", "World!"), String.class);
    }

    public Mono<ServerResponse> stream(ServerRequest req) {
        Stream<Integer> stream = Stream.iterate(0, i -> i + 1);
        Flux<Map<String, Integer>> flux = Flux.fromStream(stream.limit(200l))
                .map(i -> Collections.singletonMap("value", i));
        return ok().contentType(MediaType.APPLICATION_NDJSON)
                .body(fromPublisher(flux, new ParameterizedTypeReference<Map<String, Integer>>(){}));
    }

    public Mono<ServerResponse> echo(ServerRequest req) {
        Mono<String> body = req.bodyToMono(String.class).map(String::toUpperCase);
        return ok().body(body, String.class);
    }

    public Mono<ServerResponse> postStream(ServerRequest req) {
        Flux<Map<String, Integer>> body = req.body(toFlux( // BodyExtractors.toFlux을 static import해야 한다.
                new ParameterizedTypeReference<Map<String, Integer>>(){}));

        return ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(fromPublisher(body.map(m -> Collections.singletonMap("double", m.get("value") * 2)),
                        new ParameterizedTypeReference<Map<String, Integer>>(){}));
    }

}
