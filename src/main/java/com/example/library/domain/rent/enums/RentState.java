package com.example.library.domain.rent.enums;

import com.example.library.exception.ErrorCode;
import com.example.library.exception.exceptions.RentStateNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RentState {
    ON_RENT(0,"대여 중"),
    ON_OVERDUE(1,"연체 중"),
    NORMAL_RETURN(2,"정상 반납"),
    OVERDUE_RETURN(3,"연체 반납")
    ;

    private final Integer stateNum;
    private final String desc;

    public static RentState getRentState(Integer value){
        return Arrays.stream(RentState.values())
                .filter(rentState -> rentState.getStateNum().equals(value))
                .findFirst()
                .orElseThrow(()->new RentStateNotFoundException(ErrorCode.RENTSTATE_NOT_FOUND));
    }

}
