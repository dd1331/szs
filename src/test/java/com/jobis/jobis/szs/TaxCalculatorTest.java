package com.jobis.jobis.szs;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
public class TaxCalculatorTest {

    @Test
    public void testCalculateTax() {
        testCalculateTax("1,400,000", "84,000");
        testCalculateTax("20,000,000", "1,740,000");
        testCalculateTax("50,000,000", "6,240,000");
        testCalculateTax("88,000,000", "15,360,000");
        testCalculateTax("150,000,000", "37,060,000");
        testCalculateTax("300,000,000", "94,060,000");
        testCalculateTax("500,000,000", "174,060,000");
        testCalculateTax("500,000,001", "174,060,000");
        testCalculateTax("1,000,000,000", "384,060,000");
        testCalculateTax("1,000,000,001", "384,060,000");
        testCalculateTax("1,500,000,000", "609,060,000");

    }

    private void testCalculateTax(String taxableIncomeStr, String expectedTaxStr) {
        BigDecimal taxableIncome = new BigDecimal(taxableIncomeStr.replaceAll(",", ""));
        BigDecimal expectedTax = new BigDecimal(expectedTaxStr.replaceAll(",", ""));
        BigDecimal actualTax = new MyTaxCalculator().calculateTaxableIncome(taxableIncome);
        assertEquals(expectedTax, actualTax);
    }
}
