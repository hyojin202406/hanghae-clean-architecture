package com.hhplu.hhpluscleanarch.lecture.application;

import com.hhplu.hhpluscleanarch.lecture.common.HistoryStatus;
import com.hhplu.hhpluscleanarch.lecture.controller.request.LectureRequest;
import com.hhplu.hhpluscleanarch.lecture.controller.response.ApplyResponse;
import com.hhplu.hhpluscleanarch.lecture.domain.Lecture;
import com.hhplu.hhpluscleanarch.lecture.domain.LectureHistory;
import com.hhplu.hhpluscleanarch.lecture.domain.User;
import com.hhplu.hhpluscleanarch.lecture.infrastructure.LectureHistoryRepository;
import com.hhplu.hhpluscleanarch.lecture.infrastructure.LectureRepository;
import com.hhplu.hhpluscleanarch.lecture.infrastructure.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
    public ResponseEntity<ApplyResponse> apply(LectureRequest request) {
        Long userId = request.getUserId();
        Long lectureId = request.getLectureId();

        // 사용자 검증
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 특강 검증
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("특강을 찾을 수 없습니다."));

        // 현재 신청된 인원 수 확인
        long currentApplications = lectureHistoryRepository.countByLectureIdAndHistoryStatus(lectureId, HistoryStatus.SUCCESS);
        if (currentApplications >= 30) {
            throw new IllegalArgumentException("특강은 선착순 30명만 신청 가능합니다.");
        }

        // LectureHistory 객체 생성
        LectureHistory lectureHistory = new LectureHistory();
        lectureHistory.setUserId(userId);
        lectureHistory.setLectureId(lectureId);
        lectureHistory.setAppliedAt(LocalDateTime.now());
        lectureHistory.setHistoryStatus(HistoryStatus.SUCCESS);

        // 신청 기록 저장
        LectureHistory savedLectureHistory = lectureHistoryRepository.save(lectureHistory);

        // ApplyResponse 생성
        ApplyResponse response = new ApplyResponse();
        response.setLectureId(savedLectureHistory.getLectureId());
        response.setUserId(savedLectureHistory.getUserId());
        response.setAppliedAt(savedLectureHistory.getAppliedAt());
        response.setStatus(savedLectureHistory.getHistoryStatus());

        return ResponseEntity.ok(response);
    }

}