package com.foreverfitness.fintess.service;

import com.foreverfitness.fintess.entity.Member;
import com.foreverfitness.fintess.repository.MemberRepository;
import com.foreverfitness.fintess.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberServiceImpl memberService;

    private Member testMember;

    @BeforeEach
    void setUp() {
        testMember = new Member();
        testMember.setId(1L);
        testMember.setEmail("test@example.com");
        testMember.setPassword("password123");
        testMember.setFirstName("Тест");
        testMember.setLastName("Клиент");
    }

    @Test
    void save_ShouldEncodePassword() {
        when(passwordEncoder.encode("password123")).thenReturn("encoded-password");
        when(memberRepository.save(any(Member.class))).thenAnswer(inv -> inv.getArgument(0));

        Member result = memberService.save(testMember);

        assertEquals("encoded-password", result.getPassword());
        verify(passwordEncoder).encode("password123");
        verify(memberRepository).save(testMember);
    }

    @Test
    void findByEmail_WhenMemberExists_ShouldReturnMember() {
        when(memberRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(testMember));

        Optional<Member> result = memberService.findByEmail("test@example.com");

        assertTrue(result.isPresent());
        assertEquals("Тест", result.get().getFirstName());
    }

    @Test
    void findByEmail_WhenMemberNotExists_ShouldReturnEmpty() {
        when(memberRepository.findByEmail("nobody@example.com"))
                .thenReturn(Optional.empty());

        Optional<Member> result = memberService.findByEmail("nobody@example.com");

        assertFalse(result.isPresent());
    }
}
