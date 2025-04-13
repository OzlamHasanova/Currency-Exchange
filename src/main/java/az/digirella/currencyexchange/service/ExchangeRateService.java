package az.digirella.currencyexchange.service;

import az.digirella.currencyexchange.entity.*;
import az.digirella.currencyexchange.repository.CurrencyRepository;
import az.digirella.currencyexchange.repository.ExchangeRateRepository;
import az.digirella.currencyexchange.util.CbarXmlParser;
import jakarta.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final CurrencyRepository currencyRepository;
    private final ExchangeRateRepository exchangeRateRepository;


    public void importFromXml(InputStream xmlStream) {
        try {
            ValCurs valCurs = new CbarXmlParser().parseXml(xmlStream);
            LocalDate rateDate = LocalDate.parse(valCurs.getDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));

            for (ValType valType : valCurs.getValTypes()) {
                if ("Xarici valyutalar".equals(valType.getType())) {
                    for (Valute valute : valType.getValutes()) {
                        Currency currency = currencyRepository.findByCode(valute.getCode())
                                .orElseGet(() -> {
                                    Currency newCurrency = new Currency();
                                    newCurrency.setCode(valute.getCode());
                                    newCurrency.setName(valute.getName());
                                    return currencyRepository.save(newCurrency);
                                });

                        ExchangeRate rate = new ExchangeRate();
                        rate.setCurrency(currency);
                        rate.setValue(valute.getValue());
                        rate.setDate(rateDate);
                        rate.setNominal(valute.getNominal());
                        if (!exchangeRateRepository.existsByCurrencyAndDate(currency, rateDate)) {
                            exchangeRateRepository.save(rate);
                        }
                    }
                }
            }
        } catch (JAXBException e) {
            throw new RuntimeException("XML parse xətası!", e);
        }
    }

    public String importByDate(LocalDate date) {
        boolean exists = exchangeRateRepository.existsByDate(date);
        if (exists) {
            return "Seçilmiş tarix üçün məzənnələr artıq mövcuddur: " + date;
        }

        String formattedDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String urlString = "https://www.cbar.az/currencies/" + formattedDate + ".xml";

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int statusCode = connection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                try (InputStream inputStream = connection.getInputStream()) {
                    importFromXml(inputStream);
                    return "Məzənnələr uğurla yükləndi və bazaya əlavə olundu: " + date;
                }
            } else {
                throw new RuntimeException("CBAR serveri xətalı cavab verdi: " + statusCode);
            }
        } catch (IOException e) {
            throw new RuntimeException("Faylın URL-ə açılması zamanı xətaya yol verildi: " + urlString, e);
        }
    }

    @Transactional
    public String deleteRatesByDate(LocalDate date) {
        long deletedCount = exchangeRateRepository.deleteByDate(date);
        if (deletedCount > 0) {
            return "Seçilmiş tarix üçün məzənnələr silindi: " + date;
        } else {
            return "Seçilmiş tarix üçün məzənnələr tapılmadı: " + date;
        }
    }
}
