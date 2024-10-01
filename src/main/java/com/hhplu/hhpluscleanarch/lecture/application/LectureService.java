package com.hhplu.hhpluscleanarch.lecture.application;

import com.hhplu.hhpluscleanarch.lecture.domain.Lecture;
import com.hhplu.hhpluscleanarch.lecture.exception.LectureNotFoundException;
import com.hhplu.hhpluscleanarch.lecture.infrastructure.LectureRepository;
import com.hhplu.hhpluscleanarch.lecture.controller.response.LectureResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LectureService {

    private LectureRepository lectureRepository;

    public LectureService(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    /**
     * 날짜별로 현재 신청 가능한 특강 목록을 조회하는 API
     * @return
     */
    public ResponseEntity<List<LectureResponse>> getLectures() {
        // 강의 목록 조회
        List<Lecture> lectures = lectureRepository.findAvailableLectures();

        if (lectures == null || lectures.isEmpty()) {
            throw new LectureNotFoundException("Lecture not found");
        }

        // Lecture 엔티티를 LectureResponse로 변환
        List<LectureResponse> lectureResponses = lectures.stream()
                .map(lecture -> new LectureResponse(
                        lecture.getId(),
                        lecture.getTitle(),
                        lecture.getLecturerName(),
                        lecture.getCapacity(),
                        lecture.getLectureStatus()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(lectureResponses);
    }

}
