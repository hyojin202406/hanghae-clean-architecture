package com.hhplu.hhpluscleanarch.lecture.application.Integration;

import com.hhplu.hhpluscleanarch.lecture.application.UserService;
import com.hhplu.hhpluscleanarch.lecture.infrastructure.UserHistoryResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class UserServiceIntegrationTest {

    @Autowired
    UserService userService;

    @Test
    void 특강_신청_완료_목록_조회_성공() {
        // Given
        Long userId = 1L;

        // When
        ResponseEntity<List<UserHistoryResponse>> response = userService.getApplyStatus(userId);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

    }

}