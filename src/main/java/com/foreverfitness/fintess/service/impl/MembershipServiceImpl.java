package com.foreverfitness.fintess.service.impl;

import com.foreverfitness.fintess.entity.Membership;
import com.foreverfitness.fintess.repository.MembershipRepository;
import com.foreverfitness.fintess.service.MembershipService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;

    public MembershipServiceImpl(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Override
    public List<Membership> findAll() {
        return membershipRepository.findAll();
    }

    @Override
    public Optional<Membership> findById(Long id) {
        return membershipRepository.findById(id);
    }

    @Override
    public Membership save(Membership membership) {
        return membershipRepository.save(membership);
    }

    @Override
    public void deleteById(Long id) {
        membershipRepository.deleteById(id);
    }

    @Override
    public List<Membership> findByMemberId(Long memberId) {
        return membershipRepository.findByMemberId(memberId);
    }
}
