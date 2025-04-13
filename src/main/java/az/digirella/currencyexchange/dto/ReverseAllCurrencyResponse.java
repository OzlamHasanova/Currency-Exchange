package az.digirella.currencyexchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ReverseAllCurrencyResponse {
    private String currencyCode;
    private BigDecimal reverseRate;
}
