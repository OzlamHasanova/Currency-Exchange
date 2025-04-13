package az.digirella.currencyexchange.controller;

import az.digirella.currencyexchange.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/exchange-rates")
@RequiredArgsConstructor
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;

    @PostMapping("/import")
    public ResponseEntity<String> importRates(@RequestParam("date")
                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        String result = exchangeRateService.importByDate(date);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteRates(@RequestParam("date") String dateStr) {
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String responseMessage = exchangeRateService.deleteRatesByDate(date);
        return ResponseEntity.ok(responseMessage);
    }
}


