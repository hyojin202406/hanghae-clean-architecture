package com.hhplu.hhpluscleanarch.lecture.application.unit;

import com.hhplu.hhpluscleanarch.lecture.application.UserService;
import com.hhplu.hhpluscleanarch.lecture.common.HistoryStatus;
import com.hhplu.hhpluscleanarch.lecture.domain.dto.LectureHistoryWithLecture;
import com.hhplu.hhpluscleanarch.lecture.exception.LectureHistoryNotFoundException;
import com.hhplu.hhpluscleanarch.lecture.infrastructure.LectureHistoryRepository;
import com.hhplu.hhpluscleanarch.lecture.infrastructure.UserRepository;
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
class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    LectureHistoryRepository lectureHistoryRepository;

    @InjectMocks
    UserService userService;

    @Test
    void 특강_신청_완료_목록_조회_성공() {
        // Given
        Long userId = 1L;

        LectureHistoryWithLecture history1 = new LectureHistoryWithLecture();
        history1.setId(1L);
        history1.setUserId(userId);
        history1.setLectureId(1L);
        history1.setAppliedAt(LocalDateTime.of(2024,9,20,15,30));
        history1.setLectureTitle("LectureA");
        history1.setLectureName("A");
        history1.setHistoryStatus(HistoryStatus.SUCCESS);

        LectureHistoryWithLecture history2 = new LectureHistoryWithLecture();
        history2.setId(1L);
        history2.setUserId(userId);
        history2.setLectureId(1L);
        history2.setAppliedAt(LocalDateTime.of(2024,9,20,15,30));
        history2.setLectureTitle("LectureB");
        history2.setLectureName("B");
        history2.setHistoryStatus(HistoryStatus.SUCCESS);

        when(lectureHistoryRepository.findCompletedLecturesByUserId(userId)).thenReturn(
                Arrays.asList(history1, history2)
        );

        // When
        List<LectureHistoryWithLecture> histories = userService.getApplyStatus(userId);

        // Then
        assertNotNull(histories);
        assertFalse(histories.isEmpty());
    }

    @Test
    void 특강_신청_완료_목록이_없을경우_예외처리() {
        // Given
        Long userId = 1L;
        when(lectureHistoryRepository.findCompletedLecturesByUserId(userId)).thenReturn(Collections.emptyList()); // 빈 리스트로 반환

        // When
        LectureHistoryNotFoundException exception = assertThrows(LectureHistoryNotFoundException.class, () -> userService.getApplyStatus(userId));

        // Then
        assertEquals("Lecture history not found", exception.getMessage());
    }

}