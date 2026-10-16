package com.foreverfitness.fintess.service.impl;

import com.foreverfitness.fintess.entity.Booking;
import com.foreverfitness.fintess.repository.BookingRepository;
import com.foreverfitness.fintess.service.BookingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }

    @Override
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public List<Booking> findByMemberId(Long memberId) {
        return bookingRepository.findByMemberId(memberId);
    }

    @Override
    public List<Booking> findByWorkoutId(Long workoutId) {
        return bookingRepository.findByWorkoutId(workoutId);
    }
}
