package com.jobis.jobis.szs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.Year;

@Service
@RequiredArgsConstructor
public class TaxService {

    private final TaxInfoRepository taxInfoRepository;
    private final UserRepository userRepository;
    private final TaxCalculator taxCalculator;
     FinalTaxResponse getFinalTax(String userId) {

             User user = userRepository.findByUserId(userId).orElseThrow();

             TaxInfo taxInfo = taxInfoRepository.findByUserIdAndTaxYear(user.getId(), Year.now().getValue()).orElseThrow();

             BigDecimal taxableIncome = taxCalculator.calculateTax(taxInfo.get종합소득금액().subtract(taxInfo.get소득공제()));
             BigDecimal finalTax = taxableIncome.subtract(taxInfo.get세액공제());

             DecimalFormat decimalFormat = new DecimalFormat("###,###");
             String formattedFinalTax = decimalFormat.format(finalTax);

             return FinalTaxResponse.builder().결정세액(formattedFinalTax).build();
     }

}
