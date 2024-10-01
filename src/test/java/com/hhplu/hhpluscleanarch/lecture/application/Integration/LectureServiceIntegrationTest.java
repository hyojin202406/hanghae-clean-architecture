package com.hhplu.hhpluscleanarch.lecture.application.Integration;

import com.hhplu.hhpluscleanarch.lecture.application.LectureService;
import com.hhplu.hhpluscleanarch.lecture.controller.response.LectureResponse;
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
class LectureServiceIntegrationTest {

    @Autowired
    LectureService lectureService;

    @Test
    void 특강_선택_조회_성공() {

        // When
        ResponseEntity<List<LectureResponse>> response = lectureService.getLectures();

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

}