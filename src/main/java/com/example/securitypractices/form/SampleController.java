package com.example.securitypractices.form;

import com.example.securitypractices.common.SecurityLogger;
import java.security.Principal;
import java.util.concurrent.Callable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("message", "Hello, " + principal.getName());
        } else {
            model.addAttribute("message", "Hello, Spring Security");
        }
        return "index";
    }

    @GetMapping("/info")
    public String info(Model model) {
        model.addAttribute("message", "Info");
        return "info";
    }

    // 인가된 사용자가 접근 > Principal
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("message", "Hello, " + principal.getName());
        return "admin";
    }

    // 인가된 사용자가 접근 > Principal
    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        model.addAttribute("message", "Hello Admin, " + principal.getName());
        return "admin";
    }

    @GetMapping("/user")
    public String user(Model model, Principal principal) {
        model.addAttribute("message", "Hello User, " + principal.getName());
        return "user";
    }

    @GetMapping("/async-handler")
    @ResponseBody
    public Callable<String> asyncHandler() {
        // async 로 별도 쓰레드로 로직 처리를 한다고 하더라도
        // WebAsyncManagerIntegrationFilter 를 통해서 같은 Principal 정보를 참조 가능
        SecurityLogger.log("MVC");

        return () -> {
            SecurityLogger.log("Callable");
            return "Async Handler";
        };
    }
}
