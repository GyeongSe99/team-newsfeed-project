package com.npcamp.newsfeed.auth.dto;

import com.npcamp.newsfeed.common.constant.RegexPattern;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = "비밀번호를 입력해 주세요!")
    @Pattern(regexp = RegexPattern.PASSWORD, message = "비밀번호는 8~16자리이며, 영문, 숫자, 특수문자를 최소 1개 이상 포함해야 합니다.")
    private final String password;

    @Email(message = "이메일 형식을 확인해 주세요!")
    @NotBlank(message = "이메일을 입력해 주세요!")
    private final String email;

}
