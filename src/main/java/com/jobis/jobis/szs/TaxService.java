package com.jobis.jobis.szs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Service
@RequiredArgsConstructor
public class TaxService {

    private final TaxInfoRepository taxInfoRepository;
    private final UserRepository userRepository;
    private final TaxCalculator taxCalculator;
     FinalTaxResponse getFinalTax(String userId) {

             User user = userRepository.findByUserId(userId).orElseThrow();

             TaxInfo taxInfo = taxInfoRepository.findByUserIdOrderByTaxYearDesc(user.getId()).orElseThrow();
             BigDecimal 과세표준 = taxInfo.get종합소득금액().subtract(taxInfo.get소득공제());
             BigDecimal 산출세액 = taxCalculator.calculateTaxableIncome(과세표준);
             BigDecimal 결정세액 = 산출세액.subtract(taxInfo.get세액공제());

             DecimalFormat decimalFormat = new DecimalFormat("###,###");
             String formattedFinalTax = decimalFormat.format(결정세액);

             return FinalTaxResponse.builder().결정세액(formattedFinalTax).build();
     }

}
