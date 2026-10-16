package com.foreverfitness.fintess.repository;

import com.foreverfitness.fintess.entity.Membership;
import com.foreverfitness.fintess.entity.MembershipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
    List<Membership> findByMemberId(Long memberId);
    List<Membership> findByStatus(MembershipStatus status);
}
