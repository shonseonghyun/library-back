package com.example.library.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Merge {

    /* null인 필드 무시
     *   true)  null인입 시 이전 소스필드 유지
     *   false) null인입 시 null 입력-> 들어온 그대로 입력
     * */
    boolean ignoreNull() default true;
}
