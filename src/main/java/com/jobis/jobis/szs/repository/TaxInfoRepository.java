package com.jobis.jobis.szs.repository;

import com.jobis.jobis.szs.entity.TaxInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaxInfoRepository extends JpaRepository<TaxInfo, Integer> {

    Optional<TaxInfo> findByUserIdOrderByTaxYearDesc(Long user_id);

}
