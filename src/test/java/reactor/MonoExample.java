package reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public class MonoExample {

    @Test
    void test1(){
        Mono.just("Hello Reactor")
                .subscribe(System.out::println);
    }

    @Test
    void test2() {
        Mono.empty()
                .subscribe(
                        none -> System.out.println("# onNext Signal")
                        , error -> {}
                        , () -> System.out.println("# onComplete Signal")
                );
    }
}
