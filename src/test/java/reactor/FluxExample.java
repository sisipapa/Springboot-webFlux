package reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxExample {

    @Test
    void test1() {
        Flux.just(6, 9, 13)
                .map(num -> num % 2)
                .subscribe(System.out::println);
    }

    @Test
    void test2() {
        Flux.fromArray(new Integer[]{3, 6, 7, 9})
                .filter(num -> num > 6)
                .map(num -> num * 2)
                .subscribe(System.out::println);
    }

    @Test
    void test3() {
        // 두개의 Mono를 연결
        // concatWith는 java의 concat처럼 두개의 문자열을 붙여서 하나의 데이터를 emit하는게 아니라
        // emit할 테이터를 일렬로 줄 세워서 하나의 데이터 소스를 만든 후에 차례로 데이터를 emit한다.
        Flux<String> flux = Mono.justOrEmpty("One")
                .concatWith(Mono.justOrEmpty("Two"));
        flux.subscribe(System.out::println);
    }

    @Test
    void test4() {
        // Flux.concat -> Flux
        // collectList -> 하나의 List를 갖는 Mono
        Flux.concat(
                        Flux.just("one", "two", "three"),
                        Flux.just("four", "five", "six"),
                        Flux.just("seven", "eight", "nine")
                ).collectList()
                .subscribe(System.out::println);
    }
}
