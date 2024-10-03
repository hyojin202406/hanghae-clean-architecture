package com.hhplu.hhpluscleanarch.lecture.application;

import com.hhplu.hhpluscleanarch.lecture.common.LectureStatus;
import com.hhplu.hhpluscleanarch.lecture.controller.request.ChoiceLectureRequest;
import com.hhplu.hhpluscleanarch.lecture.domain.Lecture;
import com.hhplu.hhpluscleanarch.lecture.exception.LectureNotFoundException;
import com.hhplu.hhpluscleanarch.lecture.infrastructure.LectureRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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
    public List<Lecture> getLectures(ChoiceLectureRequest date) {

        LocalDateTime startOfDay = date.getDate().atStartOfDay();
        LocalDateTime endOfDay = date.getDate().atTime(LocalTime.MAX);

        // 강의 목록 조회
        List<Lecture> lectures = lectureRepository.findLecturesByIdAndDateAndStatus(LectureStatus.OPENED, startOfDay, endOfDay);

        if (lectures == null || lectures.isEmpty()) {
            throw new LectureNotFoundException("Lecture not found");
        }

        return lectures;
    }

}
