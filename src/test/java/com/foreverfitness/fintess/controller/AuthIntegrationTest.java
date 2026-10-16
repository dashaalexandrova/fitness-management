package com.foreverfitness.fintess.controller;

import com.foreverfitness.fintess.entity.Member;
import com.foreverfitness.fintess.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    private void createMember() {
        Member member = new Member();
        member.setEmail("auth-test@example.com");
        member.setPassword("secret123");
        member.setFirstName("Тест");
        member.setLastName("Вход");
        member.setCreatedAt(LocalDateTime.now());
        memberService.save(member);
    }

    @Test
    void login_WithCorrectData_ShouldReturn200() throws Exception {
        createMember();
        String json = """
                {"email": "auth-test@example.com", "password": "secret123"}
                """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("auth-test@example.com"));
    }

    @Test
    void login_WithWrongPassword_ShouldReturn401() throws Exception {
        createMember();
        String json = """
                {"email": "auth-test@example.com", "password": "wrong"}
                """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void protectedEndpoint_WithoutLogin_ShouldReturn401() throws Exception {
        mockMvc.perform(get("/api/members"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void register_WithoutLogin_ShouldReturn201() throws Exception {
        String json = """
                {
                  "email": "register-test@example.com",
                  "password": "secret123",
                  "firstName": "Новый",
                  "lastName": "Клиент"
                }
                """;

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("register-test@example.com"))
                .andExpect(jsonPath("$.password").doesNotExist());
    }
}