package com.jobis.jobis.szs;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaxInfoRepository extends JpaRepository<TaxInfo, Integer> {

    Optional<TaxInfo> findByUserIdAndTaxYear(Long user_id, int taxYear);

}
