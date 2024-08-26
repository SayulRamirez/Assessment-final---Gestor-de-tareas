package com.metaphorce.assessment_final.security;

import com.metaphorce.assessment_final.entities.User;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JWTServiceTest {

    @Mock
    private JwtService underTest;

    private User user;

    @BeforeEach
    void setup() {
        user = User.builder().id(1L)
                .email("juan@example.com").build();
    }
    @Test
    void whenGetToken() {

        when(underTest.getToken(any(User.class))).thenCallRealMethod();

        String token = underTest.getToken(user);

        assertNotNull(token);
    }

    @Test
    void whenGetClaimGetExpiration() {

        when(underTest.getClaim(anyString(), ArgumentMatchers.<Function<Claims, Date>>any()))
                .thenCallRealMethod();
        when(underTest.getToken(any(User.class))).thenCallRealMethod();

        String token = underTest.getToken(user);

        Date date = underTest.getClaim(token, Claims::getExpiration);

        Date expiration = Date.from(Instant.now().plus(24, ChronoUnit.HOURS));

        assertFalse(date.after(expiration));
    }

    @Test
    void wheGetEmailFromToken() {
        when(underTest.getToken(any(User.class))).thenCallRealMethod();
        when(underTest.getEmailFromToken(anyString())).thenCallRealMethod();

        when(underTest.getClaim(anyString(), ArgumentMatchers.<Function<Claims, Date>>any()))
                .thenCallRealMethod();

        String token = underTest.getToken(user);

        String emailFromToken = underTest.getEmailFromToken(token);

        assertEquals(user.getEmail(), emailFromToken);
    }

    @Test
    void whenIsTokenExpired() {

        boolean tokenValid = underTest.isTokenValid(underTest.getToken(user), user);

        assertFalse(tokenValid);
    }
}
