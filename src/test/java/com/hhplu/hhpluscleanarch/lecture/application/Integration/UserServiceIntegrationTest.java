package com.hhplu.hhpluscleanarch.lecture.application.Integration;

import com.hhplu.hhpluscleanarch.lecture.application.UserService;
import com.hhplu.hhpluscleanarch.lecture.domain.dto.LectureHistoryWithLecture;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class UserServiceIntegrationTest {

    @Autowired
    UserService userService;

    @Test
    void 특강_신청_완료_목록_조회_성공() {
        // Given
        Long userId = 9997L;

        // When
        List<LectureHistoryWithLecture> applyStatus = userService.getApplyStatus(userId);

        // Then
        assertNotNull(applyStatus);
        assertFalse(applyStatus.isEmpty());
    }

}