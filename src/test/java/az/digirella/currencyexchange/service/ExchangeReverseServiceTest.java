package az.digirella.currencyexchange.service;

import az.digirella.currencyexchange.dto.ExchangeRateResponse;
import az.digirella.currencyexchange.dto.ReverseAllCurrencyResponse;
import az.digirella.currencyexchange.dto.ReverseCurrencyByAllDatesResponse;
import az.digirella.currencyexchange.entity.Currency;
import az.digirella.currencyexchange.entity.ExchangeRate;
import az.digirella.currencyexchange.repository.CurrencyRepository;
import az.digirella.currencyexchange.repository.ExchangeRateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExchangeReverseServiceTest {

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    @InjectMocks
    private ExchangeReverseService exchangeReverseService;

    private Currency currency;
    private ExchangeRate exchangeRate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Prepare the common test data
        currency = new Currency();
        currency.setCode("USD");
        currency.setName("US Dollar");

        exchangeRate = new ExchangeRate();
        exchangeRate.setCurrency(currency);
        exchangeRate.setValue(new BigDecimal("1.7"));
        exchangeRate.setNominal("1");
        exchangeRate.setDate(LocalDate.of(2022, 5, 25));
    }

    @Test
    void getReverseRateByCodeAndDate_whenRateExists_thenReturnReverseRate() {
        // Given
        String code = "USD";
        LocalDate date = LocalDate.of(2022, 5, 25);
        when(exchangeRateRepository.findByCurrencyCodeAndDate(code.toUpperCase(), date))
                .thenReturn(Optional.of(exchangeRate));

        // When
        ExchangeRateResponse response = exchangeReverseService.getReverseRateByCodeAndDate(code, date);

        // Then
        assertEquals("USD", response.getCurrencyCode());
        assertEquals("US Dollar", response.getCurrencyName());
        assertEquals(new BigDecimal("0.588235"), response.getAmountInCurrency());  // 1/1.7 rounded to 6 decimal places
    }

    @Test
    void getReverseRateByCodeAndDate_whenRateNotFound_thenThrowException() {
        // Given
        String code = "USD";
        LocalDate date = LocalDate.of(2022, 5, 25);
        when(exchangeRateRepository.findByCurrencyCodeAndDate(code.toUpperCase(), date))
                .thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                exchangeReverseService.getReverseRateByCodeAndDate(code, date));
        assertEquals("Valyuta kodu və ya tarixə görə məzənnə tapılmadı", exception.getMessage());
    }

    @Test
    void getAllReverseRatesByDate_whenRatesExist_thenReturnReverseRates() {
        // Given
        LocalDate date = LocalDate.of(2022, 5, 25);
        when(exchangeRateRepository.findAllByDate(date))
                .thenReturn(List.of(exchangeRate));

        // When
        List<ReverseAllCurrencyResponse> responses = exchangeReverseService.getAllReverseRatesByDate(date);

        // Then
        assertEquals(1, responses.size());
        ReverseAllCurrencyResponse response = responses.get(0);
        assertEquals("USD", response.getCurrencyCode());

        // Correct expected value: reverse rate = 1 / 1.7 (0.588235)
        assertEquals(new BigDecimal("0.588235").setScale(6, BigDecimal.ROUND_HALF_UP), response.getReverseRate());
    }

    @Test
    void getReverseRatesForCurrencyOverAllDates_whenRatesExist_thenReturnReverseRates() {
        // Given
        String currencyCode = "USD";
        when(exchangeRateRepository.findAllByCurrencyCode(currencyCode))
                .thenReturn(List.of(exchangeRate));

        // When
        List<ReverseCurrencyByAllDatesResponse> responses = exchangeReverseService.getReverseRatesForCurrencyOverAllDates(currencyCode);

        // Then
        assertEquals(1, responses.size());
        ReverseCurrencyByAllDatesResponse response = responses.get(0);
        assertEquals(LocalDate.of(2022, 5, 25), response.getDate());

        // Correct expected value: reverse rate = 1 / 1.7 (0.588235)
        assertEquals(new BigDecimal("0.588235").setScale(6, BigDecimal.ROUND_HALF_UP), response.getReverseRate());
    }

}
