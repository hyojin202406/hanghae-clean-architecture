package com.hhplu.hhpluscleanarch.lecture.application.Integration;

import com.hhplu.hhpluscleanarch.lecture.application.LectureService;
import com.hhplu.hhpluscleanarch.lecture.common.LectureStatus;
import com.hhplu.hhpluscleanarch.lecture.domain.Lecture;
import com.hhplu.hhpluscleanarch.lecture.infrastructure.LectureRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
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
        Lecture lecture1 = new Lecture();
        lecture1.setId(999L);
        lecture1.setTitle("Lecture 1");
        lecture1.setLecturerName("A");
        lecture1.setCapacity(15);
        lecture1.setCreatedAt(LocalDateTime.now());
        lecture1.setLectureStatus(LectureStatus.OPENED);
        lectureRepository.save(lecture1);

        // When
        List<Lecture> lectures = lectureService.getLectures();

        // Then
        assertNotNull(lectures);
        assertFalse(lectures.isEmpty());
    }

}