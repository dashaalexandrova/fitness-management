package com.foreverfitness.fintess.service;

import com.foreverfitness.fintess.entity.Booking;
import java.util.List;
import java.util.Optional;

public interface BookingService {
    List<Booking> findAll();
    Optional<Booking> findById(Long id);
    Booking save(Booking booking);
    void deleteById(Long id);
    List<Booking> findByMemberId(Long memberId);
    List<Booking> findByWorkoutId(Long workoutId);
}
