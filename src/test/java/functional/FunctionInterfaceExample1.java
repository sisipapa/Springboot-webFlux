package functional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.junit.jupiter.api.Test;

/**
 * Functional Interface 중 Predicate와 Consumer를 활용해서 샘플코드 작성
 */
public class FunctionInterfaceExample1 {

    @Test
    void test(){
        List<SampleData> lists = List.of(
                SampleData.builder().unit("unit1").name("name1").build()
                , SampleData.builder().unit("unit2").name("name2").build()
                , SampleData.builder().unit("unit3").name("name3").build()
                , SampleData.builder().unit("unit4").name("name4").build()
                , SampleData.builder().unit("unit4").name("name1").build()
                , SampleData.builder().unit("unit4").name("name1").build()
                , SampleData.builder().unit("unit4").name("name5").build()
        );

        List<SampleData> filteredList = filter(lists, sampleData -> sampleData.getName().equals("name1"));
        addData(filteredList, sampleData -> System.out.println(sampleData));

    }

    public static List<SampleData> filter(List<SampleData> list, Predicate<SampleData> predicate) {
        return list.stream().filter(predicate).collect(Collectors.toList());
    }

    public static void addData(List<SampleData> filteredList, Consumer<SampleData> consumer){
        filteredList.stream().forEach(sampleData -> consumer.accept(sampleData));
    }

    @ToString
    @Builder
    @Data
    public static class SampleData{
        private String unit;
        private String name;
    }
}
