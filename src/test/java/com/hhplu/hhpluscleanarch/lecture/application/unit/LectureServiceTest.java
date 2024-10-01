package com.hhplu.hhpluscleanarch.lecture.application.unit;

import com.hhplu.hhpluscleanarch.lecture.application.LectureService;
import com.hhplu.hhpluscleanarch.lecture.common.LectureStatus;
import com.hhplu.hhpluscleanarch.lecture.domain.Lecture;
import com.hhplu.hhpluscleanarch.lecture.exception.LectureNotFoundException;
import com.hhplu.hhpluscleanarch.lecture.infrastructure.LectureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LectureServiceTest {

    @Mock
    LectureRepository lectureRepository;

    @InjectMocks
    LectureService lectureService;

    @Test
    void 특강_선택_조회_성공() {
        // Given
        Lecture lectureEntity1 = new Lecture();
        lectureEntity1.setId(1L);
        lectureEntity1.setTitle("LectureA");
        lectureEntity1.setLecturerName("A");
        lectureEntity1.setCapacity(10);
        lectureEntity1.setCreatedAt(LocalDateTime.of(2024,9,20,15,30));
        lectureEntity1.setLectureStatus(LectureStatus.OPENED);

        Lecture lectureEntity2 = new Lecture();
        lectureEntity2.setId(2L);
        lectureEntity2.setTitle("LectureB");
        lectureEntity2.setLecturerName("B");
        lectureEntity2.setCapacity(20);
        lectureEntity2.setCreatedAt(LocalDateTime.of(2024,9,1,11,10));
        lectureEntity2.setLectureStatus(LectureStatus.CLOSED);

        when(lectureRepository.findByCapacityLessThanAndLectureStatus(30, LectureStatus.OPENED)).thenReturn(
                Arrays.asList(lectureEntity1, lectureEntity2)
        );

        // When
        List<Lecture> lectures = lectureService.getLectures();

        // Then
        assertNotNull(lectures);
        assertFalse(lectures.isEmpty());
    }

    @Test
    void 신청_가능한_특강이_없을경우_예외처리() {
        // Given
        when(lectureRepository.findByCapacityLessThanAndLectureStatus(30, LectureStatus.OPENED)).thenReturn(Collections.emptyList()); // 빈 리스트로 반환

        // When
        LectureNotFoundException exception = assertThrows(LectureNotFoundException.class, () -> lectureService.getLectures());

        // Then
        assertEquals("Lecture not found", exception.getMessage());
    }

}