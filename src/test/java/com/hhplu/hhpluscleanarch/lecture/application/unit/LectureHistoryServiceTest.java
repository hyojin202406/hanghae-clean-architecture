package com.hhplu.hhpluscleanarch.lecture.application.unit;

import com.hhplu.hhpluscleanarch.lecture.application.LectureHistoryService;
import com.hhplu.hhpluscleanarch.lecture.common.HistoryStatus;
import com.hhplu.hhpluscleanarch.lecture.controller.request.LectureRequest;
import com.hhplu.hhpluscleanarch.lecture.controller.response.ApplyResponse;
import com.hhplu.hhpluscleanarch.lecture.domain.Lecture;
import com.hhplu.hhpluscleanarch.lecture.domain.LectureHistory;
import com.hhplu.hhpluscleanarch.lecture.domain.User;
import com.hhplu.hhpluscleanarch.lecture.infrastructure.LectureHistoryRepository;
import com.hhplu.hhpluscleanarch.lecture.infrastructure.LectureRepository;
import com.hhplu.hhpluscleanarch.lecture.infrastructure.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LectureHistoryServiceTest {

    @Mock
    LectureRepository lectureRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    LectureHistoryRepository lectureHistoryRepository;

    @InjectMocks
    LectureHistoryService lectureHistoryService;

    @Test
    void 특강_신청_성공() {
        // Given
        Long userId = 1L;
        Long lectureId = 1L;

        User user = new User();
        user.setId(userId);

        Lecture lecture = new Lecture();
        lecture.setId(lectureId);

        LectureHistory lectureHistory = new LectureHistory();
        lectureHistory.setUserId(userId);
        lectureHistory.setLectureId(lectureId);
        lectureHistory.setAppliedAt(LocalDateTime.now());
        lectureHistory.setHistoryStatus(HistoryStatus.SUCCESS);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(lectureRepository.findById(lectureId)).thenReturn(Optional.of(lecture));
        when(lectureHistoryRepository.countByLectureIdAndHistoryStatus(lectureId, HistoryStatus.SUCCESS)).thenReturn(10L);
        when(lectureHistoryRepository.save(any(LectureHistory.class))).thenReturn(lectureHistory);

        LectureRequest request = new LectureRequest(userId, lectureId);

        // When
        ResponseEntity<ApplyResponse> response = lectureHistoryService.apply(request);

        // Then
        assertNotNull(response);
        assertEquals(lectureId, response.getBody().getLectureId());
        assertEquals(userId, response.getBody().getUserId());
        assertEquals(HistoryStatus.SUCCESS, response.getBody().getStatus());
    }

    @Test
    void testApplyLectureFull() {
        // Given
        Long userId = 1L;
        Long lectureId = 1L;

        User user = new User();
        user.setId(userId);

        Lecture lecture = new Lecture();
        lecture.setId(lectureId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(lectureRepository.findById(lectureId)).thenReturn(Optional.of(lecture));
        when(lectureHistoryRepository.countByLectureIdAndHistoryStatus(lectureId, HistoryStatus.SUCCESS)).thenReturn(30L);

        LectureRequest request = new LectureRequest(userId, lectureId);

        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            lectureHistoryService.apply(request);
        });

        // Then
        assertEquals("특강은 선착순 30명만 신청 가능합니다.", exception.getMessage());
    }
}