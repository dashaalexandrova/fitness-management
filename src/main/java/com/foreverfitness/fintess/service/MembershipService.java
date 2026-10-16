package com.foreverfitness.fintess.service;

import com.foreverfitness.fintess.entity.Membership;
import java.util.List;
import java.util.Optional;

public interface MembershipService {
    List<Membership> findAll();
    Optional<Membership> findById(Long id);
    Membership save(Membership membership);
    void deleteById(Long id);
    List<Membership> findByMemberId(Long memberId);
}
