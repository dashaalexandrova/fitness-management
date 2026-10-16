package com.foreverfitness.fintess.controller;

import com.foreverfitness.fintess.dto.CreateMembershipDTO;
import com.foreverfitness.fintess.dto.MembershipDTO;
import com.foreverfitness.fintess.entity.Member;
import com.foreverfitness.fintess.entity.Membership;
import com.foreverfitness.fintess.service.MemberService;
import com.foreverfitness.fintess.service.MembershipService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/memberships")
public class MembershipController {

    private final MembershipService membershipService;
    private final MemberService memberService;

    public MembershipController(MembershipService membershipService,
                                MemberService memberService) {
        this.membershipService = membershipService;
        this.memberService = memberService;
    }

    @GetMapping
    public List<MembershipDTO> getAll() {
        return membershipService.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembershipDTO> getById(@PathVariable Long id) {
        return membershipService.findById(id)
                .map(m -> ResponseEntity.ok(toDTO(m)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Все абонементы одного клиента
    @GetMapping("/member/{memberId}")
    public List<MembershipDTO> getByMember(@PathVariable Long memberId) {
        return membershipService.findByMemberId(memberId).stream()
                .map(this::toDTO)
                .toList();
    }

    @PostMapping
    public ResponseEntity<MembershipDTO> create(@Valid @RequestBody CreateMembershipDTO dto) {
        Optional<Member> member = memberService.findById(dto.getMemberId());
        if (member.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Membership membership = toEntity(dto, member.get());
        Membership saved = membershipService.save(membership);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MembershipDTO> update(@PathVariable Long id,
                                                @Valid @RequestBody CreateMembershipDTO dto) {
        if (membershipService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<Member> member = memberService.findById(dto.getMemberId());
        if (member.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Membership membership = toEntity(dto, member.get());
        membership.setId(id);
        return ResponseEntity.ok(toDTO(membershipService.save(membership)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (membershipService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        membershipService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private MembershipDTO toDTO(Membership membership) {
        MembershipDTO dto = new MembershipDTO();
        dto.setId(membership.getId());
        dto.setStartDate(membership.getStartDate());
        dto.setEndDate(membership.getEndDate());
        dto.setPrice(membership.getPrice());
        dto.setType(membership.getType());
        dto.setStatus(membership.getStatus());
        if (membership.getMember() != null) {
            dto.setMemberId(membership.getMember().getId());
            dto.setMemberName(membership.getMember().getFirstName() + " "
                    + membership.getMember().getLastName());
        }
        return dto;
    }

    private Membership toEntity(CreateMembershipDTO dto, Member member) {
        Membership membership = new Membership();
        membership.setStartDate(dto.getStartDate());
        membership.setEndDate(dto.getEndDate());
        membership.setPrice(dto.getPrice());
        membership.setType(dto.getType());
        membership.setStatus(dto.getStatus());
        membership.setMember(member);
        return membership;
    }
}
