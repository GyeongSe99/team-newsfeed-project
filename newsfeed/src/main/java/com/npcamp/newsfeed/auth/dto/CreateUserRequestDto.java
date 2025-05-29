package com.npcamp.newsfeed.auth.dto;

import com.npcamp.newsfeed.common.constant.RegexPattern;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateUserRequestDto {

    @Size(min = 1, max = 10)
    @NotBlank
    private final String name;

    @Email
    private final String email;

    @NotBlank
    @Pattern(regexp = RegexPattern.PASSWORD, message = "비밀번호는 8~16자리이며, 영문, 숫자, 특수문자를 최소 1개 이상 포함해야 합니다.")
    private final String password;

}