CREATE TABLE currency (
                          id BIGSERIAL PRIMARY KEY,
                          code VARCHAR(10) NOT NULL UNIQUE,
                          name VARCHAR(100)
);

CREATE TABLE exchange_rate (
                               id BIGSERIAL PRIMARY KEY,
                               currency_id BIGINT NOT NULL REFERENCES currency(id),
                               value DECIMAL(19,4) NOT NULL,
                               nominal VARCHAR(255) NOT NULL,
                               date DATE NOT NULL,
                               UNIQUE (currency_id, date)
);