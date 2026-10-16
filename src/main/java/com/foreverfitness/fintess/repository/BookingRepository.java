package com.foreverfitness.fintess.repository;

import com.foreverfitness.fintess.entity.Booking;
import com.foreverfitness.fintess.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByMemberId(Long memberId);
    List<Booking> findByWorkoutId(Long workoutId);
    List<Booking> findByMemberIdAndStatus(Long memberId, BookingStatus status);
}
