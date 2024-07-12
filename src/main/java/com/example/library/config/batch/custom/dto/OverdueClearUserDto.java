package com.example.library.config.batch.custom.dto;

import com.example.library.domain.rent.infrastructure.entity.RentManagerEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OverdueClearUserDto {
    RentManagerEntity rentManagerEntity;
    String returnDt;
}
