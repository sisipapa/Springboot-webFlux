package functional;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

public class HelloReactor {

    @Test
    void test(){
        Flux<String> sequences = Flux.just("Hello", "Reactor");
        sequences.map(data -> data.toLowerCase())
                .subscribe(System.out::println);
    }
}
