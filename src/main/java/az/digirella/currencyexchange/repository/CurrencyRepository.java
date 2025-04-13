package az.digirella.currencyexchange.repository;

import az.digirella.currencyexchange.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findByCode(String code);
}
