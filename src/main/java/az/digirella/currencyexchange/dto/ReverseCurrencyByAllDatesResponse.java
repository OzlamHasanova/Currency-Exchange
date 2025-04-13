package az.digirella.currencyexchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReverseCurrencyByAllDatesResponse {
    private LocalDate date;
    private BigDecimal reverseRate;
}
