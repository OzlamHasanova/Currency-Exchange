package az.digirella.currencyexchange.controller;

import az.digirella.currencyexchange.dto.ExchangeRateResponse;
import az.digirella.currencyexchange.dto.ReverseAllCurrencyResponse;
import az.digirella.currencyexchange.service.ExchangeReverseService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/exchange-azn")
@RequiredArgsConstructor
public class ExchangeReverseController {

    private final ExchangeReverseService exchangeReverseService;

    @GetMapping("/reverse-rate")
    public ResponseEntity<?> getReverseRate(
            @RequestParam("code") String code,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        try {
            ExchangeRateResponse response = exchangeReverseService.getReverseRateByCodeAndDate(code, date);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/reverse-rates")
    public ResponseEntity<List<ReverseAllCurrencyResponse>> getAllReverseRates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<ReverseAllCurrencyResponse> reverseRates = exchangeReverseService.getAllReverseRatesByDate(date);
        return ResponseEntity.ok(reverseRates);
    }

}
