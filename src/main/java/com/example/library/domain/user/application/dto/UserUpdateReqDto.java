package com.example.library.domain.user.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateReqDto {

    private String userPwd;

    private String tel;

    private String gender;

    private String email;

    private Integer useFlg;
}
