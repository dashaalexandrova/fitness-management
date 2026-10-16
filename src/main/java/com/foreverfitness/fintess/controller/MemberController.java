package com.foreverfitness.fintess.controller;
import com.foreverfitness.fintess.dto.CreateMemberDTO;
import com.foreverfitness.fintess.dto.MemberDTO;
import com.foreverfitness.fintess.entity.Member;
import com.foreverfitness.fintess.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public List<MemberDTO> getAll() {
        return memberService.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> getById(@PathVariable Long id) {
        return memberService.findById(id)
                .map(m -> ResponseEntity.ok(toDTO(m)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<MemberDTO> getByEmail(@PathVariable String email) {
        return memberService.findByEmail(email)
                .map(m -> ResponseEntity.ok(toDTO(m)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MemberDTO> create(@Valid @RequestBody CreateMemberDTO dto) {
        if (memberService.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Member member = toEntity(dto);
        member.setCreatedAt(LocalDateTime.now());
        Member saved = memberService.save(member);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberDTO> update(@PathVariable Long id,
                                            @Valid @RequestBody CreateMemberDTO dto) {
        Optional<Member> existing = memberService.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Member member = toEntity(dto);
        member.setId(id);
        member.setCreatedAt(existing.get().getCreatedAt());
        return ResponseEntity.ok(toDTO(memberService.save(member)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (memberService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        memberService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private MemberDTO toDTO(Member member) {
        MemberDTO dto = new MemberDTO();
        dto.setId(member.getId());
        dto.setEmail(member.getEmail());
        dto.setFirstName(member.getFirstName());
        dto.setLastName(member.getLastName());
        dto.setCreatedAt(member.getCreatedAt());
        return dto;
    }

    private Member toEntity(CreateMemberDTO dto) {
        Member member = new Member();
        member.setEmail(dto.getEmail());
        member.setPassword(dto.getPassword());
        member.setFirstName(dto.getFirstName());
        member.setLastName(dto.getLastName());
        return member;
    }
}