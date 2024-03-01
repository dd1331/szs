package com.jobis.jobis.szs;

import java.math.BigDecimal;

public interface TaxCalculator
{
    public BigDecimal calculateTax(BigDecimal taxableIncome);
}
