package com.metaphorce.assessment_final.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metaphorce.assessment_final.configuration.SecurityConfig;
import com.metaphorce.assessment_final.dto.AuthResponse;
import com.metaphorce.assessment_final.dto.LoginRequest;
import com.metaphorce.assessment_final.dto.RegisterRequest;
import com.metaphorce.assessment_final.security.AuthService;
import com.metaphorce.assessment_final.security.JwtService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = AuthController.class)
@Import({SecurityConfig.class})
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationProvider authenticationProvider;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenLogin() throws Exception {
        LoginRequest request = new LoginRequest("juan@example.coml", "juan1234");

        BDDMockito.given(authService.login(Mockito.any(LoginRequest.class)))
                .willReturn(new AuthResponse("token"));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .header(HttpHeaders.AUTHORIZATION, "tokenGenerate"));

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token", CoreMatchers.notNullValue()));
    }

    @Test
    void whenRegisterUser() throws Exception {

        RegisterRequest request = new RegisterRequest(
                "juan",
                "perez",
                "meza",
                "4771234567",
                "juan@example.com",
                "juan1234"
        );

        BDDMockito.given(authService.register(Mockito.any(RegisterRequest.class)))
                .willReturn(new AuthResponse("tokenSecret"));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token", CoreMatchers.notNullValue()));
    }
}
