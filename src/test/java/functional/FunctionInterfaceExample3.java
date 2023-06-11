package functional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

/**
 * Supplier<T>를 활용해서
 * 샘플 코드
 */
public class FunctionInterfaceExample3 {

    @Test
    void test(){
        System.out.println(createMnemonic());
    }

    @Test
    void test1(){
        System.out.println(getMnemonic());
    }

    private static String createMnemonic(){
        return Stream
                .generate(() -> getMnemonic())
                .limit(12)
                .collect(Collectors.joining(" "));
    }

    private static String getMnemonic(){
        List<String> mnemonic = Arrays.asList(
            "alpha", "bravo", "charlie"
            , "delta", "echo", "foxtrot"
            , "golf", "hotel", "india"
            , "juliet", "kilo", "lima"
            , "mike", "november", "oscar"
            , "papa", "quebec", "romeo"
            , "sierra", "tango", "uniform"
            , "victor", "whiskey", "xray"
            , "yankee", "zulu"
        );
        Collections.shuffle(mnemonic);
        return mnemonic.get(0);
    }
}
