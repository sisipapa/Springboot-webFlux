package reactor.example9;

import java.time.Duration;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * Sinks를 사용하면 프로그래밍 코드를 통해 명시적으로 Signal을 전송할 수 있다.
 * generate(), create() Operator는 싱글스레드 기반
 * Sinks는 멀티스레드 방식
 *
 *
 * 스레드 풀의 크기와 동작:
 * Schedulers.parallel()은 스레드 풀의 크기를 사용 가능한 CPU 코어 수에 따라 동적으로 결정합니다. 각 CPU 코어에 하나의 스레드가 할당되며, 요청된 작업은 이 스레드들 중에서 실행됩니다. 이는 CPU 집약적인 작업에 적합합니다.
 * Schedulers.boundedElastic()은 한정된(elastic) 스레드 풀을 사용합니다. 이 스레드 풀은 블로킹 작업과 I/O 작업과 같은 비동기 작업에 최적화되어 있습니다. 스레드 수는 기반 런타임 환경에 따라 결정되지만, 보통 사용 가능한 CPU 코어 수와 관계 없이 스레드 풀의 크기가 제한됩니다.
 *
 * 스레드 재사용 및 소멸:
 * Schedulers.parallel()은 스레드 풀에서 작업이 완료되면 해당 스레드가 재사용됩니다. 작업 부하가 낮을 때 스레드 수가 감소하며, 스레드가 유지되는 시간이 상대적으로 짧습니다.
 * Schedulers.boundedElastic()은 스레드 풀에서 작업이 완료되면 해당 스레드는 재사용되지 않습니다. 대신 스레드는 풀에서 제거되고, 풀의 크기는 작업 부하에 따라 동적으로 증가 및 감소합니다.
 *
 * 적합한 사용 사례:
 * Schedulers.parallel()은 주로 CPU 집약적인 작업에 적합합니다. 예를 들어 계산, 변환 또는 데이터 처리와 같은 작업에 사용될 수 있습니다.
 * Schedulers.boundedElastic()은 I/O 작업이나 외부 서비스와의 상호작용, 블로킹 작업이 필요한 작업에 적합합니다. 예를 들어 네트워크 요청, 파일 I/O, 데이터베이스 쿼리 등이 이에 해당합니다.
 *
 *
 * subscribeOn - subscribeOn은 옵저버블(또는 퍼블리셔)의 데이터 흐름을 구독할 때,
 * 어떤 스레드 또는 스케줄러에서 해당 작업이 실행되어야 하는지를 지정하는 역할을 합니다.
 * 이를 통해 작업의 실행 환경을 제어하고, 다른 스레드나 스케줄러에서 비동기적으로 작업을 처리할 수 있습니다.
 */
@Slf4j
public class Example9_1 {

    @Test
    void test1() throws Exception {
        int tasks = 6;
        Flux
                .interval(Duration.ofMillis(1000L))
                .create(sink -> {
                    IntStream
                            .range(1, tasks)
                            .forEach(n -> sink.next(doTask(n)));
                })
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(n -> log.info("# create() : {}", n))
                .publishOn(Schedulers.parallel())
                .map(result -> result + " success!")
                .doOnNext(n -> log.info("# map(): {}", n))
                .publishOn(Schedulers.parallel())
                .subscribe(data -> log.info("# onNext: {}", data));
    }

    private static String doTask(int taskNumber) {
        return "task " + taskNumber + " result";
    }

    @Test
    void test2(){
        Flux.range(1, 10)
                .subscribeOn(Schedulers.boundedElastic())
                .map(i -> i * 2)
                .subscribe(System.out::println);
    }
}
