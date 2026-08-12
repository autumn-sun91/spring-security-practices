package com.example.securitypractices.form;

import com.example.securitypractices.common.SecurityLogger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SampleService {

    // 기본적으로 @Async 어노테이션은 Principal 이 공유X
    // SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL); 로 해결 가능
    @Async
    public void asyncService() {
        SecurityLogger.log("Async Service");
        System.out.println("Async service called");
    }
}
