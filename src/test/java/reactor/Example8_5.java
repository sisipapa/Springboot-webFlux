package reactor;

import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Example8_5 {

    @Test
    void test1() throws Exception {
        Flux
                .interval(Duration.ofMillis(3000L))
                .doOnNext(data -> log.info("** emitted by original Flux: {}", data))
                .onBackpressureBuffer(
                        2,
                        dropped -> log.info("** Overflow & Dropped : {} **", dropped),
                        BufferOverflowStrategy.DROP_LATEST)
                .doOnNext(data -> log.info("[# emitted by Buffer : {}]", data))
                .publishOn(Schedulers.parallel(), false, 1)
                .subscribe(data -> {
                    try{
                        Thread.sleep(10000L);
                    }catch(Exception e){
                    }
                    log.info("# onNext:{}", data);
                },error -> {
                    log.info("# onError : {}", error);
                });
    }
}
