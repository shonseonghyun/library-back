package com.example.library.test.merge.domain;

import com.example.library.annotation.Merge;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;




@Builder
@Data
@AllArgsConstructor
public class TestDomain {

    @Merge(ignoreNull = false)
    public String a;

    @Merge(ignoreNull = false)
    public String b;

    @Merge
    public Long c;

    @Merge
    public Long d;

    public boolean e;
}
