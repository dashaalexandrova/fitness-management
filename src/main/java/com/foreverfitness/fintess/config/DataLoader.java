package com.foreverfitness.fintess.config;

import com.foreverfitness.fintess.entity.*;
import com.foreverfitness.fintess.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    private final TrainerRepository trainerRepository;
    private final WorkoutTypeRepository workoutTypeRepository;
    private final WorkoutRepository workoutRepository;
    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;
    private final EquipmentRepository equipmentRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(TrainerRepository trainerRepository,
                      WorkoutTypeRepository workoutTypeRepository,
                      WorkoutRepository workoutRepository,
                      MemberRepository memberRepository,
                      MembershipRepository membershipRepository,
                      EquipmentRepository equipmentRepository,
                      PasswordEncoder passwordEncoder) {
        this.trainerRepository = trainerRepository;
        this.workoutTypeRepository = workoutTypeRepository;
        this.workoutRepository = workoutRepository;
        this.memberRepository = memberRepository;
        this.membershipRepository = membershipRepository;
        this.equipmentRepository = equipmentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Шифруем старые пароли, которые лежат открытым текстом
        for (Member existing : memberRepository.findAll()) {
            if (!existing.getPassword().startsWith("$2")) {
                existing.setPassword(passwordEncoder.encode(existing.getPassword()));
                memberRepository.save(existing);
            }
        }

        // Если данные уже есть, второй раз не создаём
        if (trainerRepository.count() > 0) {
            return;
        }

        Trainer trainer = new Trainer();
        trainer.setFirstName("Анна");
        trainer.setLastName("Смирнова");
        trainer.setSpecialization("Йога");
        trainerRepository.save(trainer);

        WorkoutType yoga = new WorkoutType();
        yoga.setName("Йога");
        yoga.setDescription("Растяжка и дыхательные практики");
        workoutTypeRepository.save(yoga);

        Workout workout = new Workout();
        workout.setTitle("Утренняя йога");
        workout.setStartTime(LocalDateTime.now().plusDays(1));
        workout.setDurationMinutes(60);
        workout.setMaxParticipants(15);
        workout.setTrainer(trainer);
        workout.setWorkoutType(yoga);
        workoutRepository.save(workout);

        Member member = new Member();
        member.setEmail("ivan@example.com");
        member.setPassword(passwordEncoder.encode("password123"));
        member.setFirstName("Иван");
        member.setLastName("Петров");
        member.setCreatedAt(LocalDateTime.now());
        memberRepository.save(member);

        Membership membership = new Membership();
        membership.setStartDate(LocalDate.now());
        membership.setEndDate(LocalDate.now().plusMonths(1));
        membership.setPrice(new BigDecimal("3000.00"));
        membership.setType(MembershipType.MONTHLY);
        membership.setStatus(MembershipStatus.ACTIVE);
        membership.setMember(member);
        membershipRepository.save(membership);

        Equipment equipment = new Equipment();
        equipment.setName("Беговая дорожка");
        equipment.setQuantity(5);
        equipment.setCondition("Хорошее");
        equipmentRepository.save(equipment);

        System.out.println("Тестовые данные загружены успешно!");
    }
}