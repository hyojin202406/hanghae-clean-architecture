package com.hhplu.hhpluscleanarch.lecture.application.unit;

import com.hhplu.hhpluscleanarch.lecture.application.LectureService;
import com.hhplu.hhpluscleanarch.lecture.common.LectureStatus;
import com.hhplu.hhpluscleanarch.lecture.controller.request.ChoiceLectureRequest;
import com.hhplu.hhpluscleanarch.lecture.domain.Lecture;
import com.hhplu.hhpluscleanarch.lecture.exception.LectureNotFoundException;
import com.hhplu.hhpluscleanarch.lecture.infrastructure.LectureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        ChoiceLectureRequest request = new ChoiceLectureRequest();
        request.setDate(LocalDate.of(2024, 10, 4));
        LocalDate date = LocalDate.of(2024, 10, 4);

        Lecture lecture1 = new Lecture();
        lecture1.setId(1L);
        lecture1.setTitle("LectureA");
        lecture1.setLecturerName("A");
        lecture1.setCapacity(10);
        lecture1.setCreatedAt(LocalDateTime.of(2024,10,4,15,30));
        lecture1.setLectureStatus(LectureStatus.OPENED);

        Lecture lecture2 = new Lecture();
        lecture2.setId(1L);
        lecture2.setTitle("LectureB");
        lecture2.setLecturerName("B");
        lecture2.setCapacity(20);
        lecture2.setCreatedAt(LocalDateTime.of(2024,10,4,11,10));
        lecture2.setLectureStatus(LectureStatus.OPENED);

        // 수정된 부분: 하루의 시작과 끝 시간을 설정
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        when(lectureRepository.findLecturesByIdAndDateAndStatus(LectureStatus.OPENED, startOfDay, endOfDay)).thenReturn(
                Arrays.asList(lecture1, lecture2)
        );

        // When
        List<Lecture> lectures = lectureService.getLectures(request);

        // Then
        assertNotNull(lectures);
        assertFalse(lectures.isEmpty());
    }

    @Test
    void 신청_가능한_특강이_없을경우_예외처리() {
        // Given
        ChoiceLectureRequest request = new ChoiceLectureRequest();
        LocalDate date = LocalDate.now().plusDays(1);
        request.setDate(LocalDate.now().plusDays(1));
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        when(lectureRepository.findLecturesByIdAndDateAndStatus(LectureStatus.OPENED, startOfDay, endOfDay)).thenReturn(Collections.emptyList()); // 빈 리스트로 반환

        // When
        LectureNotFoundException exception = assertThrows(LectureNotFoundException.class, () -> lectureService.getLectures(request));

        // Then
        assertEquals("Lecture not found", exception.getMessage());
    }

}