package az.digirella.currencyexchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ExchangeRateResponse {
    private String currencyCode;
    private String currencyName;
    private BigDecimal amountInCurrency;
    private LocalDate date;
}

