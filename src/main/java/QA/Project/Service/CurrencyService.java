package QA.Project.Service;

import QA.Project.Entity.CurrencyEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CurrencyService {
    CurrencyEntity addCurrency(CurrencyEntity currencyEntity);
    void removeCurrency(Long id);
    List<CurrencyEntity> getAllRates();
    Double convertCurrency(String source, String target);

    CurrencyEntity getCurrency(Long id);
    Boolean isEmpty(CurrencyEntity currency);
}
