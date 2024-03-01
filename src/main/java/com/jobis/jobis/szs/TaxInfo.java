package com.jobis.jobis.szs;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tax_info")
public class TaxInfo {
    @Id
    @GeneratedValue()
    private Long id;

    private int taxYear;

    private BigDecimal 소득공제;
    private BigDecimal 세액공제;
    private BigDecimal 종합소득금액;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
