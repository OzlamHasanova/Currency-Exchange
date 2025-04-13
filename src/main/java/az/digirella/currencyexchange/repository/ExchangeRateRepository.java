package az.digirella.currencyexchange.repository;

import az.digirella.currencyexchange.entity.Currency;
import az.digirella.currencyexchange.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    List<ExchangeRate> findAllByDate(LocalDate date);

    boolean existsByCurrencyAndDate(Currency currency, LocalDate rateDate);
    boolean existsByDate(LocalDate date);

    long deleteByDate(LocalDate date);

    Optional<ExchangeRate> findByCurrencyCodeAndDate(String currency, LocalDate date);

    List<ExchangeRate> findAllByCurrencyCode(String currencyCode);
}
