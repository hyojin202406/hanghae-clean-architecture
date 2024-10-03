package com.hhplu.hhpluscleanarch.lecture.application.Integration;

import com.hhplu.hhpluscleanarch.lecture.application.LectureService;
import com.hhplu.hhpluscleanarch.lecture.controller.request.ChoiceLectureRequest;
import com.hhplu.hhpluscleanarch.lecture.domain.Lecture;
import com.hhplu.hhpluscleanarch.lecture.infrastructure.LectureRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class LectureServiceIntegrationTest {

    @Autowired
    LectureService lectureService;

    @Autowired
    LectureRepository lectureRepository;

    @Test
    void 특강_선택_조회_성공() {

        // Given
        ChoiceLectureRequest request = new ChoiceLectureRequest();
        request.setDate(LocalDate.now());

        // When
        List<Lecture> lectures = lectureService.getLectures(request);

        // Then
        assertNotNull(lectures);
        assertFalse(lectures.isEmpty());
    }

}