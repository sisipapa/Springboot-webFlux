package reactor;

import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Example8_2 {

    @Test
    void test1() throws Exception {
        // interval Operator는 0부터 1씩 증가한 숫자를 0.001초에 한번씩 emit
        // doOnNext Operator는 Publisher가 emit한 데이터를 확인하거나 추가적인 동작을 정의하는 용도로 사용하는데 주로 디버깅 용도로 사용
        // subscribe가 전달받은 데이터를 0.005초 시간이 걸리도록 해서
        // Publisher의 emit하는 속도가 Subscriber가 처리하는 속도 차이가 나서 Backpresure 전략 테스트
        // publishOn Operator는 Sequence주 일부를 별도의 스레드에서 실행할 수 있도록 해주는 Operator
        Flux
                .interval(Duration.ofMillis(1L))
                .onBackpressureError()
                .doOnNext(data -> log.info("# doOnNext: {}", data))
                .publishOn(Schedulers.parallel())
                .subscribe(data -> {
                            try{
                                Thread.sleep(5L);
                            }catch(InterruptedException e){
                                log.info("# onNExt: {}", data);
                            }
                        },
                        error -> log.error("# onError"));

        Thread.sleep(2000L);
    }
}
