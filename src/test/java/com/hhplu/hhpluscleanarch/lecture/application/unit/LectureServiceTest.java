package com.hhplu.hhpluscleanarch.lecture.application.unit;

import com.hhplu.hhpluscleanarch.lecture.application.LectureService;
import com.hhplu.hhpluscleanarch.lecture.common.LectureStatus;
import com.hhplu.hhpluscleanarch.lecture.controller.response.LectureResponse;
import com.hhplu.hhpluscleanarch.lecture.domain.Lecture;
import com.hhplu.hhpluscleanarch.lecture.exception.LectureNotFoundException;
import com.hhplu.hhpluscleanarch.lecture.infrastructure.LectureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        ResponseEntity<List<LectureResponse>> response = lectureService.getLectures();

        // Then
        assertEquals(200, response.getStatusCodeValue()); // HTTP 상태 코드 확인
        assertEquals(2, response.getBody().size()); // 반환된 강의 개수 확인
        assertEquals("LectureA", response.getBody().get(0).getTitle()); // 첫 번째 강의 제목 확인
        assertEquals(LectureStatus.OPENED, response.getBody().get(0).getLectureStatus()); // 첫 번째 강의 상태 확인
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