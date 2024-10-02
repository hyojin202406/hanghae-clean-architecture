package com.hhplu.hhpluscleanarch.lecture.application.Integration;

import com.hhplu.hhpluscleanarch.lecture.application.LectureHistoryService;
import com.hhplu.hhpluscleanarch.lecture.common.HistoryStatus;
import com.hhplu.hhpluscleanarch.lecture.controller.request.LectureRequest;
import com.hhplu.hhpluscleanarch.lecture.domain.LectureHistory;
import com.hhplu.hhpluscleanarch.lecture.domain.User;
import com.hhplu.hhpluscleanarch.lecture.infrastructure.LectureHistoryRepository;
import com.hhplu.hhpluscleanarch.lecture.infrastructure.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class LectureHistoryServiceIntegrationTest {

    @Autowired
    private LectureHistoryService lectureHistoryService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LectureHistoryRepository lectureHistoryRepository;

    private List<User> users;

    @BeforeEach
    public void setUp() {
        // 사용자 40명 생성
        users = IntStream.rangeClosed(1, 50)
                .mapToObj(i -> {
                    User user = new User();
                    user.setId(Long.valueOf(i));
                    user.setUserId("user" + i);
                    user.setPassword("password");
                    return userRepository.save(user);
                })
                .collect(Collectors.toList());
    }

    @Test
    void 특강_신청_성공() {
        // Given
        LectureRequest request = new LectureRequest(2L, 9998L);

        // When
        LectureHistory lectureHistory = lectureHistoryService.apply(request);

        // Then
        assertNotNull(lectureHistory);
    }

    @Test
    public void 동시에_40명_수강신청_시_30명만_수강신청_성공() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(40);
        CountDownLatch latch = new CountDownLatch(40);

        // When
        for (int i = 0; i < 40; i++) {
            final int userIndex = i;
            executor.submit(() -> {
                try {
                    Long id = users.get(userIndex).getId();
                    lectureHistoryService.apply(new LectureRequest(1L, id));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        List<LectureHistory> lectureHistories = lectureHistoryRepository.findByLectureId(1L);
        long successCount = lectureHistories.stream()
                .filter(history -> history.getHistoryStatus() == HistoryStatus.SUCCESS)
                .count();
        long failCount = lectureHistories.stream()
                .filter(history -> history.getHistoryStatus() == HistoryStatus.FAIL)
                .count();

        // Then
        assertThat(successCount).isEqualTo(30); // 성공한 신청이 30명인지 확인
        assertThat(failCount).isEqualTo(10); // 실패한 신청이 10명인지 확인
    }

    @Test
    public void 동일한_유저가_같은_특강을_5번_신청했을때_1번만_성공하는지_검증() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            executor.submit(() -> {
                try {
                    LectureRequest request = new LectureRequest(3L, 9999L);
                    lectureHistoryService.apply(request);
                } catch (Exception e) {
                    // 예외 처리 (선택적)
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // 모든 스레드가 작업을 마칠 때까지 대기
        executor.shutdown(); // 스레드 풀 종료

        // 최종적으로 신청 기록 검증
        List<LectureHistory> lectureHistories = lectureHistoryRepository.findByUserId(9999L);
        long successCount = lectureHistories.stream()
                .filter(history -> history.getHistoryStatus() == HistoryStatus.SUCCESS)
                .count();

        long failCount = lectureHistories.stream()
                .filter(history -> history.getHistoryStatus() == HistoryStatus.FAIL)
                .count();

        assertThat(successCount).isEqualTo(1); // 성공한 신청

        assertThat(failCount).isEqualTo(4); // 실패한 신청
    }

}