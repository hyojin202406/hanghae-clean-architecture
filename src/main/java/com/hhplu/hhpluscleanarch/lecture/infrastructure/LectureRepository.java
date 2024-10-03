package com.hhplu.hhpluscleanarch.lecture.infrastructure;

import com.hhplu.hhpluscleanarch.lecture.common.LectureStatus;
import com.hhplu.hhpluscleanarch.lecture.domain.Lecture;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Lecture> findById(Long lectureId);

    @Query("SELECT l FROM Lecture l WHERE l.lectureStatus = :lectureStatus AND l.createdAt BETWEEN :startOfDay AND :endOfDay")
    List<Lecture> findLecturesByIdAndDateAndStatus(@Param("lectureStatus") LectureStatus lectureStatus,
                                                   @Param("startOfDay") LocalDateTime startOfDay,
                                                   @Param("endOfDay") LocalDateTime endOfDay);
}