package com.hhplu.hhpluscleanarch.lecture.application.Integration;

import com.hhplu.hhpluscleanarch.lecture.application.LectureHistoryService;
import com.hhplu.hhpluscleanarch.lecture.controller.request.LectureRequest;
import com.hhplu.hhpluscleanarch.lecture.controller.response.ApplyResponse;
import com.hhplu.hhpluscleanarch.lecture.domain.Lecture;
import com.hhplu.hhpluscleanarch.lecture.domain.LectureHistory;
import com.hhplu.hhpluscleanarch.lecture.domain.User;
import com.hhplu.hhpluscleanarch.lecture.infrastructure.LectureHistoryRepository;
import com.hhplu.hhpluscleanarch.lecture.infrastructure.LectureRepository;
import com.hhplu.hhpluscleanarch.lecture.infrastructure.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class LectureHistoryServiceIntegrationTest {

    @Autowired
    private LectureHistoryRepository lectureHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LectureRepository lectureRepository;

    private Lecture lecture;

    @Autowired
    LectureHistoryService lectureHistoryService;

    @Test
    void 특강_신청_성공() {
        // Given
        LectureRequest request = new LectureRequest(1L, 1L);

        // When
        LectureHistory lectureHistory = lectureHistoryService.apply(request);

        // Then
        assertNotNull(lectureHistory);
    }

    @Test
    public void testApplyConcurrently() throws InterruptedException {
//        // Test user 생성
//        User testUser = new User("testUser", "password");
//        userRepository.save(testUser);
//
//        // Test lecture 생성
//        Lecture lecture = new Lecture("Test Lecture", "Test Lecturer", 30);
//        lectureRepository.save(lecture);

        Long userId = testUser.getId(); // Test user ID
        Long lectureId = lecture.getId(); // Test lecture ID

        ExecutorService executor = Executors.newFixedThreadPool(40);
        CountDownLatch latch = new CountDownLatch(40);

        for (int i = 0; i < 40; i++) {
            executor.submit(() -> {
                try {
                    LectureRequest request = new LectureRequest(userId, lectureId);
                    lectureHistoryService.apply(request);
                } catch (Exception e) {
                    // 예외 처리 (선택적)
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // 모든 스레드가 작업을 마칠 때까지 대기

        // 최종적으로 신청 기록 검증
        List<LectureHistory> lectureHistories = lectureHistoryRepository.findAll();
        long successCount = lectureHistories.stream()
                .filter(history -> history.getHistoryStatus() == HistoryStatus.SUCCESS)
                .count();
        long failCount = lectureHistories.stream()
                .filter(history -> history.getHistoryStatus() == HistoryStatus.FAIL)
                .count();

        assertThat(successCount).isEqualTo(30); // 성공한 신청
        assertThat(failCount).isEqualTo(10); // 실패한 신청
    }
}