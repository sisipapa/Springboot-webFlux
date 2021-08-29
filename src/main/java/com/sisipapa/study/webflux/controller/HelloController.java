package com.sisipapa.study.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@RestController
public class HelloController {

    @GetMapping("/")
    Flux<String> hello() {
        return Flux.just("Hello", "World");
    }

    @GetMapping("/stream")
    Flux<Map<String, Integer>> stream() {
        Stream<Integer> stream = Stream.iterate(0, i -> i + 1); // Java8의 무한Stream
        return Flux
                .fromStream(stream.limit(200))
                .map(i -> Collections.singletonMap("value", i));
    }

    @GetMapping("/stream2")
    Flux<Map<String, Integer>> stream2() {
        Stream<Integer> stream = Stream.iterate(0, i -> i + 1);
        return Flux
                .fromStream(stream.limit(200))
                .zipWith(Flux.interval(Duration.ofMillis(100)))
                .map(tuple -> Collections.singletonMap("value", tuple.getT1() /* 튜플의 첫 번째 요소 = Stream<Integer> 요소 */));
    }

    @PostMapping("/echo")
    Mono<String> echo(@RequestBody Mono<String> body) {
        return body.map(String::toUpperCase);
    }

    @PostMapping("/stream")
    Flux<Map<String, Double>> postStream(@RequestBody Flux<Map<String, Double>> body) {
        return Flux.fromStream(body.toStream())
                .map(bodyMap -> Collections.singletonMap("value", bodyMap.get("double")));

    }
}
