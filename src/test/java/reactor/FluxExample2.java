package reactor;

import java.time.Duration;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

@Slf4j
public class FluxExample2 {

    /**
     * Cold Sequence - 구독이 발생할 때마다 Sequence의 타임라인이 처음부터...
     * Cold는 무언가를 새로 시작하고, Hot은 무언가를 새로 시작하지 않는다.
     * @throws Exception
     */
    @Test
    void test1() throws Exception{
        Flux<String> coldFlux = Flux.fromIterable(Arrays.asList("AAA", "BBB", "CCC"))
                .map(String::toLowerCase);

        coldFlux.subscribe(alphabet -> log.info("# Subscriber1 : {}", alphabet));
        System.out.println("----------------------------------------");
        Thread.sleep(2000L);
        coldFlux.subscribe(alphabet -> log.info("# Subscriber2 : {}", alphabet));
    }

    @Test
    void test2() throws Exception {
        String[] singers = {"Singer A", "Singer B", "Singer C", "Singer D", "Singer E"};

        log.info("# Begin concert");
        Flux<String> concertFlux = Flux.fromArray(singers)
                .delayElements(Duration.ofSeconds(1))
                .share(); // 원본 Flux를 멀티캐스트하는 새로운 Flux를 리턴한다.

        concertFlux.subscribe(
                singer -> log.info("# Subscriber1 is watching {}'s song", singer)
        );

        Thread.sleep(2500);

        concertFlux.subscribe(
                singer -> log.info("# Subscriber2 is watching {}'s song", singer)
        );

        Thread.sleep(3000);
    }
}
