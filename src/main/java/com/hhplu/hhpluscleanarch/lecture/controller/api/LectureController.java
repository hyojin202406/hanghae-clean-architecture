package com.hhplu.hhpluscleanarch.lecture.controller.api;

import com.hhplu.hhpluscleanarch.lecture.application.LectureHistoryService;
import com.hhplu.hhpluscleanarch.lecture.application.LectureService;
import com.hhplu.hhpluscleanarch.lecture.application.UserService;
import com.hhplu.hhpluscleanarch.lecture.controller.request.LectureRequest;
import com.hhplu.hhpluscleanarch.lecture.controller.response.ApplyResponse;
import com.hhplu.hhpluscleanarch.lecture.controller.response.LectureResponse;
import com.hhplu.hhpluscleanarch.lecture.infrastructure.UserHistoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lectures")
public class LectureController {

    private final LectureService lectureService;
    private final LectureHistoryService lectureHistoryService;
    private final UserService userService;

    public LectureController(LectureService lectureService, LectureHistoryService lectureHistoryService, UserService userService) {
        this.lectureService = lectureService;
        this.lectureHistoryService = lectureHistoryService;
        this.userService = userService;
    }

    /**
     * (핵심) 특강 신청 API
     * - 특정 userId 로 선착순으로 제공되는 특강을 신청하는 API 를 작성합니다.
     * - step4. 동일한 신청자는 동일한 강의에 대해서 한 번의 수강 신청만 성공할 수 있습니다.
     * - 특강은 선착순 30명만 신청 가능합니다.
     * - step.3 이미 신청자가 30명이 초과되면 이후 신청자는 요청을 실패합니다.
     * @param request
     * @return
     */
    @PostMapping("/apply")
    public ResponseEntity<ApplyResponse> apply(@RequestBody LectureRequest request) {
        return lectureHistoryService.apply(request);
    }

    /**
     * 특강 선택 API
     * - 날짜별로 현재 신청 가능한 특강 목록을 조회하는 API 를 작성합니다.
     * - 특강의 정원은 30명으로 고정이며, 사용자는 각 특강에 신청하기전 목록을 조회해볼 수 있어야 합니다.
     * @return
     */
    @GetMapping
    public ResponseEntity<List<LectureResponse>> getLectures() {
        return lectureService.getLectures();
    }

    /**
     * 특강 신청 완료 목록 조회 API
     * - 특정 userId 로 신청 완료된 특강 목록을 조회하는 API 를 작성합니다.
     * - 각 항목은 특강 ID 및 이름, 강연자 정보를 담고 있어야 합니다.
     * @param userId
     * @return
     */
    @GetMapping("/application/{userId}")
    public ResponseEntity<List<UserHistoryResponse>> getApplyStatus(@PathVariable Long userId) {
        return userService.getApplyStatus(userId);
    }

}
