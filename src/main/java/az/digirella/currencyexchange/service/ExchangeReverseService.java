package az.digirella.currencyexchange.service;

import az.digirella.currencyexchange.dto.ExchangeRateResponse;
import az.digirella.currencyexchange.dto.ReverseAllCurrencyResponse;
import az.digirella.currencyexchange.dto.ReverseCurrencyByAllDatesResponse;
import az.digirella.currencyexchange.entity.ExchangeRate;
import az.digirella.currencyexchange.repository.CurrencyRepository;
import az.digirella.currencyexchange.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExchangeReverseService {

    private final CurrencyRepository currencyRepository;
    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateResponse getReverseRateByCodeAndDate(String code, LocalDate date) {
        ExchangeRate rate = exchangeRateRepository.findByCurrencyCodeAndDate(code.toUpperCase(), date)
                .orElseThrow(() -> new RuntimeException("Valyuta kodu və ya tarixə görə məzənnə tapılmadı"));

        BigDecimal value = rate.getValue();

        BigDecimal reverseRate = BigDecimal.valueOf(Long.parseLong(rate.getNominal())).divide(value,
                6, RoundingMode.HALF_UP);

        return new ExchangeRateResponse(
                rate.getCurrency().getCode(),
                rate.getCurrency().getName(),
                reverseRate,
                rate.getDate()
        );
    }

    public List<ReverseAllCurrencyResponse> getAllReverseRatesByDate(LocalDate date) {
        List<ExchangeRate> exchangeRates = exchangeRateRepository.findAllByDate(date);

        return exchangeRates.stream()
                .map(rate -> {
                    BigDecimal oneUnitRate = rate.getValue().divide(BigDecimal.valueOf(Long.parseLong(rate.getNominal())), 6, RoundingMode.HALF_UP);
                    BigDecimal reverseRate = BigDecimal.ONE.divide(oneUnitRate, 6, RoundingMode.HALF_UP);
                    return new ReverseAllCurrencyResponse(rate.getCurrency().getCode(), reverseRate);
                })
                .collect(Collectors.toList());
    }

    public List<ReverseCurrencyByAllDatesResponse> getReverseRatesForCurrencyOverAllDates(String currencyCode) {
        List<ExchangeRate> exchangeRates = exchangeRateRepository.findAllByCurrencyCode(currencyCode);

        return exchangeRates.stream()
                .map(rate -> {
                    BigDecimal oneUnitRate = rate.getValue().divide(BigDecimal.valueOf(Long.parseLong(rate.getNominal())), 6, RoundingMode.HALF_UP);
                    BigDecimal reverseRate = BigDecimal.ONE.divide(oneUnitRate, 6, RoundingMode.HALF_UP);
                    return new ReverseCurrencyByAllDatesResponse(rate.getDate(), reverseRate);
                })
                .collect(Collectors.toList());
    }


}
