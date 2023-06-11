package functional;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.junit.jupiter.api.Test;

/**
 * Predicate<T>와 Function<T, R> 을 활용해서
 * List의 특정 name에 해당하는 price*count 의 sum 값을 구하는 샘플 코드
 */
public class FunctionInterfaceExample2 {

    @Test
    void test(){
        List<SampleData> lists = List.of(
                SampleData.builder().unit("unit1").name("name1").price(1000).count(1).build()
                , SampleData.builder().unit("unit2").name("name2").price(2000).count(3).build()
                , SampleData.builder().unit("unit3").name("name3").price(3000).count(4).build()
                , SampleData.builder().unit("unit4").name("name4").price(4000).count(2).build()
                , SampleData.builder().unit("unit5").name("name1").price(1000).count(5).build()
                , SampleData.builder().unit("unit6").name("name1").price(1000).count(7).build()
                , SampleData.builder().unit("unit7").name("name5").price(5000).count(3).build()
        );

        List<SampleData> filteredList = filter(lists, sampleData -> !sampleData.equals("name1"));
        System.out.println(getTotalPrice(filteredList, (sampleData) -> sampleData.getPrice() * sampleData.getCount()));
    }

    public static List<SampleData> filter(List<SampleData> list, Predicate<SampleData> predicate) {
        return list.stream().filter(predicate).collect(Collectors.toList());
    }

    public static Integer getTotalPrice(List<SampleData> filteredList, Function<SampleData, Integer> function){
        return filteredList.stream().mapToInt(sampleData -> function.apply(sampleData)).sum();
    }

    @ToString
    @Builder
    @Data
    public static class SampleData{
        private String unit;
        private String name;
        private Integer price;
        private Integer count;
    }
}
