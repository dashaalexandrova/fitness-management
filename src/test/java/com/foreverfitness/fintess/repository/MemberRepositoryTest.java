package com.foreverfitness.fintess.repository;

import com.foreverfitness.fintess.entity.Member;
import com.foreverfitness.fintess.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    private Member createMember(String email) {
        Member member = new Member();
        member.setEmail(email);
        member.setPassword("secret");
        member.setFirstName("Тест");
        member.setLastName("Репозиторий");
        member.setCreatedAt(LocalDateTime.now());
        return memberRepository.save(member);
    }

    @Test
    void save_ShouldGenerateId() {
        Member saved = createMember("repo-save@example.com");

        assertNotNull(saved.getId());
    }

    @Test
    void findByEmail_WhenExists_ShouldReturnMember() {
        createMember("repo-find@example.com");

        Optional<Member> result = memberRepository.findByEmail("repo-find@example.com");

        assertTrue(result.isPresent());
        assertEquals("Репозиторий", result.get().getLastName());
    }

    @Test
    void findByEmail_WhenNotExists_ShouldReturnEmpty() {
        Optional<Member> result = memberRepository.findByEmail("no-such@example.com");

        assertTrue(result.isEmpty());
    }

    @Test
    void existsByEmail_ShouldReturnTrueOnlyForExisting() {
        createMember("repo-exists@example.com");

        assertTrue(memberRepository.existsByEmail("repo-exists@example.com"));
        assertFalse(memberRepository.existsByEmail("repo-missing@example.com"));
    }
}
