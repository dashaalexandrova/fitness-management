package com.foreverfitness.fintess.service;

import com.foreverfitness.fintess.entity.Member;
import java.util.List;
import java.util.Optional;

public interface MemberService {
    List<Member> findAll();
    Optional<Member> findById(Long id);
    Member save(Member member);
    void deleteById(Long id);
    Optional<Member> findByEmail(String email);
}