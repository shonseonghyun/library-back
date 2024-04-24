package com.example.library.domain.user.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {

    @NotNull
    private String userPwd;

    @NotNull
    private String userName;

    private String tel;

    @NotNull
    private String email;

    @NotNull
    private String gender;

    private Integer useFlg;
}
