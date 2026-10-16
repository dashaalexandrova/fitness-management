package com.foreverfitness.fintess.controller;

import com.foreverfitness.fintess.dto.BookingDTO;
import com.foreverfitness.fintess.dto.CreateBookingDTO;
import com.foreverfitness.fintess.entity.Booking;
import com.foreverfitness.fintess.entity.BookingStatus;
import com.foreverfitness.fintess.entity.Member;
import com.foreverfitness.fintess.entity.Workout;
import com.foreverfitness.fintess.service.BookingService;
import com.foreverfitness.fintess.service.MemberService;
import com.foreverfitness.fintess.service.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final MemberService memberService;
    private final WorkoutService workoutService;

    public BookingController(BookingService bookingService,
                             MemberService memberService,
                             WorkoutService workoutService) {
        this.bookingService = bookingService;
        this.memberService = memberService;
        this.workoutService = workoutService;
    }

    @GetMapping
    public List<BookingDTO> getAll() {
        return bookingService.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getById(@PathVariable Long id) {
        return bookingService.findById(id)
                .map(b -> ResponseEntity.ok(toDTO(b)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Все записи одного клиента
    @GetMapping("/member/{memberId}")
    public List<BookingDTO> getByMember(@PathVariable Long memberId) {
        return bookingService.findByMemberId(memberId).stream()
                .map(this::toDTO)
                .toList();
    }

    // Все записи на одно занятие
    @GetMapping("/workout/{workoutId}")
    public List<BookingDTO> getByWorkout(@PathVariable Long workoutId) {
        return bookingService.findByWorkoutId(workoutId).stream()
                .map(this::toDTO)
                .toList();
    }

    @PostMapping
    public ResponseEntity<BookingDTO> create(@Valid @RequestBody CreateBookingDTO dto) {
        Optional<Member> member = memberService.findById(dto.getMemberId());
        Optional<Workout> workout = workoutService.findById(dto.getWorkoutId());
        if (member.isEmpty() || workout.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Считаем, сколько мест уже занято (отменённые записи не считаются)
        long taken = bookingService.findByWorkoutId(dto.getWorkoutId()).stream()
                .filter(b -> b.getStatus() != BookingStatus.CANCELLED)
                .count();
        if (taken >= workout.get().getMaxParticipants()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Booking booking = new Booking();
        booking.setMember(member.get());
        booking.setWorkout(workout.get());
        booking.setStatus(BookingStatus.BOOKED);
        booking.setBookedAt(LocalDateTime.now());

        Booking saved = bookingService.save(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
    }

    // Отменить запись
    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingDTO> cancel(@PathVariable Long id) {
        Optional<Booking> booking = bookingService.findById(id);
        if (booking.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        booking.get().setStatus(BookingStatus.CANCELLED);
        return ResponseEntity.ok(toDTO(bookingService.save(booking.get())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (bookingService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        bookingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private BookingDTO toDTO(Booking booking) {
        BookingDTO dto = new BookingDTO();
        dto.setId(booking.getId());
        dto.setBookedAt(booking.getBookedAt());
        dto.setStatus(booking.getStatus());
        if (booking.getMember() != null) {
            dto.setMemberId(booking.getMember().getId());
            dto.setMemberName(booking.getMember().getFirstName() + " "
                    + booking.getMember().getLastName());
        }
        if (booking.getWorkout() != null) {
            dto.setWorkoutId(booking.getWorkout().getId());
            dto.setWorkoutTitle(booking.getWorkout().getTitle());
        }
        return dto;
    }
}