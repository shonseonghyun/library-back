package com.example.library.global.utils;

import com.example.library.annotation.Merge;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class MergeUtil {

    //1. 특정 대상 필드만 수정 대상 ->Merge어노테이션
    //2. null 인입 시 아래 조건에 해당되는 케이스로 변환
//        true)  null인입 시 이전 소스필드 유지
//        false) null인입 시 null 입력
    public static boolean merge(Object source,Object target) {
        if(source.getClass()!=target.getClass()){
            throw new IllegalStateException("The two parameter objects should be same.");
        }
        boolean updated =false;
        List<String> mergedList = new LinkedList<>();

        try{
            for(Field field:source.getClass().getDeclaredFields()){ //각 필드 접근
                //Merge 어노테이션 체크
                Annotation annotation = field.getAnnotation(Merge.class);

                //존재하지 않느다면 해당 필드 체크 패스
                if(annotation==null){
                    log.info("Merge 대상이 아닙니다 => "+field.getName());
                    continue;
                }

                //필드 접근 가능
                field.setAccessible(true);
                Object oldValue = field.get(source);
                Object newValue = field.get(target);
                boolean canMerge = false;

                //true) null 인입 시 이전 소스필드 유지
                if(((Merge)annotation).ignoreNull()){
                    if(canUpdate(oldValue,newValue)){
                        canMerge=true;
                    }
                }

                //false) 들어온 그대로 입력, 만약 null인입 시 null
                else{
                    //
                    if(canUpdateNull(oldValue,newValue) ){
                        canMerge=true;
                    }
                }

                //머지 가능한 경우
                if(canMerge){
                    field.set(source,newValue); //소스엔티티에 newValue세팅
                    updated=true;
                    mergedList.add(String.format("%s : %s -> %s", field.getName(), oldValue, newValue));
                }
            }
        }catch (Exception e){
            log.error("error occurs during Merge fields of "+target.getClass().toString() + e);
        }

        if(updated) {
            log.info(String.join("\n", mergedList));
        }

        return updated;
    }

    private static boolean canUpdate(Object oldValue,Object newValue){
        //null인입 시 이전필드 유지!!!!!

        /*
            case1) oldValue="s", newValue= "s" -> 업데이트 제외
            case2) oldValue=null, newValue= "s" -> s업데이트
            case3) oldValue=null, newValue=null -> 업데이트 제외
            case4) oldValue="s", newValue=null -> 업데이트 제외
        */
        //newValue가 null이 아니면서 newValue!=Object 다르면 true리턴

        if(newValue!=null && !newValue.equals(oldValue)){
            return true;
        }
        return false;
    }

    private static boolean canUpdateNull(Object oldValue,Object newValue){
        //들어온 그대로 인입!!!!!

        /*
            case1) oldValue="s", newValue="s" -> 유지
            case2) oldValue=null, newValue= "s" ->"업데이트
            case3) oldValue=null, newValue=null -> 유지
            case4) oldValue="s", newValue=null -> 업데이트
        */
        if(newValue==null){
            return oldValue!=null;
        }
        return !newValue.equals(oldValue);
    }
}
