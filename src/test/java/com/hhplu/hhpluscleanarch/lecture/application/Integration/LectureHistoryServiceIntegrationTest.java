package com.hhplu.hhpluscleanarch.lecture.application.Integration;

import com.hhplu.hhpluscleanarch.lecture.application.LectureHistoryService;
import com.hhplu.hhpluscleanarch.lecture.controller.request.LectureRequest;
import com.hhplu.hhpluscleanarch.lecture.controller.response.ApplyResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class LectureHistoryServiceIntegrationTest {

    @Autowired
    LectureHistoryService lectureHistoryService;

    @Test
    void 특강_신청_성공() {
        // Given
        LectureRequest request = new LectureRequest(1L, 1L);

        // When
        ResponseEntity<ApplyResponse> response = lectureHistoryService.apply(request);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

}