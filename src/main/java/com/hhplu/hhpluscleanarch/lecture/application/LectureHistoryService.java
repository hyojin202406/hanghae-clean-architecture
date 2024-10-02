package com.hhplu.hhpluscleanarch.lecture.application;

import com.hhplu.hhpluscleanarch.lecture.common.HistoryStatus;
import com.hhplu.hhpluscleanarch.lecture.controller.request.LectureRequest;
import com.hhplu.hhpluscleanarch.lecture.domain.Lecture;
import com.hhplu.hhpluscleanarch.lecture.domain.LectureHistory;
import com.hhplu.hhpluscleanarch.lecture.exception.CapacityFullException;
import com.hhplu.hhpluscleanarch.lecture.exception.LectureAlreadyAppliedException;
import com.hhplu.hhpluscleanarch.lecture.infrastructure.LectureHistoryRepository;
import com.hhplu.hhpluscleanarch.lecture.infrastructure.LectureRepository;
import com.hhplu.hhpluscleanarch.lecture.infrastructure.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LectureHistoryService {


    private final LectureHistoryRepository lectureHistoryRepository;
    private final UserRepository userRepository;
    private final LectureRepository lectureRepository;

    public LectureHistoryService(LectureHistoryRepository lectureHistoryRepository,
                                 UserRepository userRepository,
                                 LectureRepository lectureRepository) {
        this.lectureHistoryRepository = lectureHistoryRepository;
        this.userRepository = userRepository;
        this.lectureRepository = lectureRepository;
    }

    /**
     * (핵심) 특강 신청 API
     * - 특정 userId 로 선착순으로 제공되는 특강을 신청하는 API 를 작성합니다.
     * - 특강은 선착순 30명만 신청 가능합니다.
     * @param request
     * @return
     */
    @Transactional
    public LectureHistory apply(LectureRequest request) {
        Long userId = request.getUserId();
        Long lectureId = request.getLectureId();

        // 사용자 검증
        userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 특강 검증
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("특강을 찾을 수 없습니다."));

        LectureHistory lectureHistory = null;

        try {
            Optional<LectureHistory> existingHistory = lectureHistoryRepository.findByUserIdAndLectureIdAndHistoryStatus(userId, lectureId, HistoryStatus.SUCCESS);
            if (existingHistory.isPresent())
                throw new LectureAlreadyAppliedException("이미 해당 특강에 신청한 사용자입니다.");
            lecture.addStudent();
            lectureHistory = LectureHistory.create(userId, lectureId, HistoryStatus.SUCCESS);
        } catch (CapacityFullException | LectureAlreadyAppliedException e) {
            lectureHistory = LectureHistory.create(userId, lectureId, HistoryStatus.FAIL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 신청 기록 저장
        lectureHistoryRepository.save(lectureHistory);

        return lectureHistory;
    }

}