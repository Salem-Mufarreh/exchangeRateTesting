package QA.Project;

import QA.Project.Entity.CurrencyEntity;
import QA.Project.Service.Implemetation.CurrencyImplement;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@Nested
public class DataBaseTest {
    @Autowired
    @Qualifier("currencyImplement")
    private CurrencyImplement _CurrencyService;

    @BeforeEach
    public void setup() {
        // No need to assign _CurrencyService here
        // It will be automatically injected by @Autowired
    }

    @Test
    public void TableIsEmpty() {
        List<CurrencyEntity> result = _CurrencyService.getAllRates();
        Assertions.assertTrue(result != null | !result.isEmpty());
    }
    @Test
    public void USDToILS(){
        double rate = _CurrencyService.convertCurrency("USD","ILS");
        double result = 100*rate;
        Assertions.assertEquals(result,374);
    }
    @Test
    public void GetRateUSD_ILS(){
        double rate = _CurrencyService.convertCurrency("USD","ILS");
        Assertions.assertEquals(rate,3.74);
    }
    @Test
    public void testConcurrencyHandling() throws InterruptedException {
        int numThreads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);

        // Submit multiple conversion tasks concurrently
        for (int i = 0; i < numThreads; i++) {
            executorService.submit(() -> {
                try {
                    // Make the currency conversion request
                    double conversionRate = _CurrencyService.convertCurrency("USD", "ILS");
                    // Assert that the conversion rate is as expected
                    Assertions.assertEquals(3.74, conversionRate);
                } finally {
                    latch.countDown();
                }
            });
        }

        // Wait for all tasks to complete
        latch.await();

        // Shut down the executor service
        executorService.shutdown();
    }

    @Test
    public void NullAndEmptySource(){
        // Test when source currency is missing
        Assertions.assertThrows(NullPointerException.class, () -> {
            _CurrencyService.convertCurrency(null, "USD");
        });

        // Test when target currency is missing
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            _CurrencyService.convertCurrency("EUR", "");
        });

        // Test when both source and target currencies are missing
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            _CurrencyService.convertCurrency("", null);
        });
    }
    @Test
    public void addCurrencyRate(){
        CurrencyEntity entity = new CurrencyEntity();
        entity.setRate(11.0);
        entity.setDate(new Date());
        entity.setSourceCurrency("Example1");
        entity.setTargetCurrency("Example2");
        CurrencyEntity result =  _CurrencyService.addCurrency(entity);
        Assertions.assertTrue(result.rate != null);
    }

    @Test
    public void RemoveCurrencyRate(){
        CurrencyEntity entity = _CurrencyService.getCurrency("Example1");
        _CurrencyService.removeCurrency(entity.id);
        Assertions.assertThrows(RuntimeException.class, () -> {
            _CurrencyService.getCurrency(entity.id);
        });

    }
    @Test
    public void CurrencyDoesntExists(){
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            _CurrencyService.getCurrency("ASDA");
        });
        Assertions.assertEquals(HttpStatus.SC_NOT_FOUND, exception.getStatusCode().value());
    }
}
