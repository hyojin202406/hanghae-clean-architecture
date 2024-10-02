package com.hhplu.hhpluscleanarch.lecture.infrastructure;

import com.hhplu.hhpluscleanarch.lecture.common.LectureStatus;
import com.hhplu.hhpluscleanarch.lecture.domain.Lecture;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {

    List<Lecture> findByCapacityLessThanAndLectureStatus(int capacity, LectureStatus lectureStatus);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Lecture> findById(Long lectureId);
}