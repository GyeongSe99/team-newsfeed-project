package com.npcamp.newsfeed.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RegexPattern {

    // 비밀번호는 8~16자리이며, 영문, 숫자, 특수문자를 최소 1개 이상 포함해야 합니다.
    public static final String PASSWORD = "[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

}