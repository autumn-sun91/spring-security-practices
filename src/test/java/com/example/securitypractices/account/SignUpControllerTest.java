package com.example.securitypractices.account;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class SignUpControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void signupForm() throws Exception {
        mockMvc.perform(get("/signup"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("_csrf")));
    }

    // 테스트 시 form 페이지가 아니므로 csrf 토큰 존재X
    @Test
    public void signupFormSubmit() throws Exception {
        mockMvc.perform(post("/signup")
                        .param("username", "thk")
                        .param("password", "123")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }
}
