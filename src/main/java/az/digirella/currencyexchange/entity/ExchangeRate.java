package az.digirella.currencyexchange.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "exchange_rate", uniqueConstraints = @UniqueConstraint(columnNames = {"currency_id", "date"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal value;

    @Column(nullable = false)
    private String nominal;

    @Column(nullable = false)
    private LocalDate date;
}
