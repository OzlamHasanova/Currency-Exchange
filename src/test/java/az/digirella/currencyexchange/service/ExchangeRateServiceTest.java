package az.digirella.currencyexchange.service;

import az.digirella.currencyexchange.entity.*;
import az.digirella.currencyexchange.repository.CurrencyRepository;
import az.digirella.currencyexchange.repository.ExchangeRateRepository;
import az.digirella.currencyexchange.util.CbarXmlParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ExchangeRateServiceTest {

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    @Mock
    private CbarXmlParser cbarXmlParser;

    @InjectMocks
    private ExchangeRateService exchangeRateService;

    private ValCurs valCurs;
    private InputStream xmlStream;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Test data setup
        valCurs = new ValCurs();
        valCurs.setDate("25.05.2022");

        ValType valType = new ValType();
        valType.setType("Xarici valyutalar");

        Valute valute = new Valute();
        valute.setCode("USD");
        valute.setNominal("1");
        valute.setName("1 ABŞ dolları");
        valute.setValue(new BigDecimal("1.7"));

        valType.setValutes(List.of(valute));
        valCurs.setValTypes(List.of(valType));
    }

    @Test
    void givenValidXml_whenImportFromXml_thenImportSuccess() throws Exception {
        // Given
        InputStream mockInputStream = mock(InputStream.class);
        when(cbarXmlParser.parseXml(mockInputStream)).thenReturn(valCurs);

        Currency currency = new Currency();
        currency.setCode("USD");
        currency.setName("1 ABŞ dolları");

        when(currencyRepository.findByCode("USD")).thenReturn(Optional.empty());
        when(currencyRepository.save(any(Currency.class))).thenReturn(currency);
        when(exchangeRateRepository.existsByCurrencyAndDate(any(Currency.class), any(LocalDate.class))).thenReturn(false);

        // When
        exchangeRateService.importFromXml(mockInputStream);

        // Then
        verify(currencyRepository, times(1)).findByCode("USD");
        verify(currencyRepository, times(1)).save(any(Currency.class));
        verify(exchangeRateRepository, times(1)).save(any(ExchangeRate.class));
    }


    @Test
    void givenDateAlreadyExists_whenImportByDate_thenReturnExistsMessage() {
        // Given
        LocalDate date = LocalDate.of(2022, 5, 25);
        when(exchangeRateRepository.existsByDate(date)).thenReturn(true);

        // When
        String result = exchangeRateService.importByDate(date);

        // Then
        assertEquals("Seçilmiş tarix üçün məzənnələr artıq mövcuddur: 2022-05-25", result);
    }

    @Test
    void givenDate_whenDeleteRatesByDate_thenReturnDeleteMessage() {
        // Given
        LocalDate date = LocalDate.of(2022, 5, 25);
        when(exchangeRateRepository.deleteByDate(date)).thenReturn(5L);

        // When
        String result = exchangeRateService.deleteRatesByDate(date);

        // Then
        assertEquals("Seçilmiş tarix üçün məzənnələr silindi: 2022-05-25", result);
    }

    @Test
    void givenDate_whenDeleteRatesByDateAndNoRates_thenReturnNoRatesFoundMessage() {
        // Given
        LocalDate date = LocalDate.of(2022, 5, 25);
        when(exchangeRateRepository.deleteByDate(date)).thenReturn(0L);

        // When
        String result = exchangeRateService.deleteRatesByDate(date);

        // Then
        assertEquals("Seçilmiş tarix üçün məzənnələr tapılmadı: 2022-05-25", result);
    }
}
