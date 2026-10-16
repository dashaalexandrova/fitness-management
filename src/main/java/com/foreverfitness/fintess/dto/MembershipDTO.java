package com.foreverfitness.fintess.dto;

import com.foreverfitness.fintess.entity.MembershipStatus;
import com.foreverfitness.fintess.entity.MembershipType;
import java.math.BigDecimal;
import java.time.LocalDate;

public class MembershipDTO {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal price;
    private MembershipType type;
    private MembershipStatus status;
    private Long memberId;
    private String memberName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public MembershipType getType() {
        return type;
    }

    public void setType(MembershipType type) {
        this.type = type;
    }

    public MembershipStatus getStatus() {
        return status;
    }

    public void setStatus(MembershipStatus status) {
        this.status = status;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public MembershipDTO() {}
}
