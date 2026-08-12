package com.example.securitypractices.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
// Controller 파라미터에 이름만 있는 Principal 이 아닌 해당 어노테이션으로 작성한 도메인 객체로 정보를 부를 수 있음.
// expression 에서는 Principal 에 등록된 (UserAccount) 정보를 직접 꺼내오도록 지원
// 해당 길이가 너무 길어 custom 한 어노테이션으로 생성해서 운영 가능
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : account")
public @interface CurrentUser {}
