package com.hhplu.hhpluscleanarch.lecture.infrastructure;

import com.hhplu.hhpluscleanarch.lecture.common.HistoryStatus;
import com.hhplu.hhpluscleanarch.lecture.domain.LectureHistory;
import com.hhplu.hhpluscleanarch.lecture.domain.dto.LectureHistoryWithLecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureHistoryRepository extends JpaRepository<LectureHistory, Long> {

    @Query("SELECT new com.hhplu.hhpluscleanarch.lecture.domain.dto.LectureHistoryWithLecture(" +
            "lh.id, lh.userId, lh.lectureId, lh.appliedAt, l.title, l.lecturerName, lh.historyStatus) " +
            "FROM LectureHistory lh JOIN Lecture l ON lh.lectureId = l.id " +
            "WHERE lh.userId = :userId AND lh.historyStatus = com.hhplu.hhpluscleanarch.lecture.common.HistoryStatus.SUCCESS")
    List<LectureHistoryWithLecture> findCompletedLecturesByUserId(@Param("userId") Long userId);

    long countByLectureIdAndHistoryStatus(Long lectureId, HistoryStatus historyStatus);

}