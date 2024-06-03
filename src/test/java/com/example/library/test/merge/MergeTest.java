package com.example.library.test.merge;

import com.example.library.global.utils.MergeUtil;
import com.example.library.test.merge.domain.TestDomain;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

public class MergeTest {

    @Test
    public void mergeUtilTest(){
        TestDomain source =TestDomain.builder()
                .a("oldA")
                .b("oldB")
                .c(1L)
                .d(1L)
                .e(true)
                .build()
                ;

        TestDomain target =TestDomain.builder()
                .a(null) //-> null
                .b("newB") //->새롭게 변경
                .c(5L) //-> 5
//                .d() -> 1유지
                .e(false) //->true
                .build()
                ;

        MergeUtil.merge(source,target);

        Assertions.assertEquals(source.getA(),null);
        Assertions.assertEquals(source.getB(),"newB");
        Assertions.assertEquals(source.getC(),5L);
        Assertions.assertEquals(source.getD(),1L);
        Assertions.assertEquals(source.isE(),true);
    }
}
