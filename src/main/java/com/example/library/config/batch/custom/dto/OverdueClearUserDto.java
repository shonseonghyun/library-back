package com.example.library.config.batch.custom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OverdueClearUserDto {
    private Long managerNo;

    private Long userNo;

    private int currentRentNumber;

    private boolean overdueFlg;

    private String returnDt;
}
