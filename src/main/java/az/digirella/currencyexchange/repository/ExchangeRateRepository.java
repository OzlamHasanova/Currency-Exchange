package az.digirella.currencyexchange.repository;

import az.digirella.currencyexchange.entity.Currency;
import az.digirella.currencyexchange.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    Optional<ExchangeRate> findByCurrencyAndDate(Currency currency, LocalDate date);
    List<ExchangeRate> findAllByDate(LocalDate date);
    List<ExchangeRate> findAllByCurrency_Code(String currencyCode);

    boolean existsByCurrencyAndDate(Currency currency, LocalDate rateDate);
    boolean existsByDate(LocalDate date);

    long deleteByDate(LocalDate date);

    Optional<ExchangeRate> findByCurrencyCodeAndDate(String currency, LocalDate date);
}
