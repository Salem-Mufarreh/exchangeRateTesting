package QA.Project.Tests;

import QA.Project.Controller.CurrencyController;
import QA.Project.Entity.CurrencyEntity;
import jakarta.validation.constraints.Null;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Nested
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ControllerTest {
    @Autowired
    @Qualifier("currencyController")
    private CurrencyController _CurrencyController;

    @Test
    @Order(0)
    public void AddCurrency_Success(){
        CurrencyEntity entity = new CurrencyEntity();
        entity.setRate(11.0);
        entity.setDate(new Date());
        entity.setSourceCurrency("Example1");
        entity.setTargetCurrency("Example2");
        ResponseEntity response =  _CurrencyController.AddCurrency(entity);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(response.getBody());

    }
    @Test
    @Order(3)
    public void RemoveCurrency(){
        List<CurrencyEntity> all = _CurrencyController.getAllRates().getBody();
        CurrencyEntity tobeDeleted = all.get(all.size()-1);
       ResponseEntity result = _CurrencyController.deleteCurrency(tobeDeleted.id);
       Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @ParameterizedTest
    @NullSource
    public void AddCurrency_null(CurrencyEntity currencyEntity){
        ResponseEntity response = _CurrencyController.AddCurrency(currencyEntity);
        Assertions.assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }
    @Test
    public void AddCurrency_Empty(){
        CurrencyEntity currency = new CurrencyEntity();
        ResponseEntity response = _CurrencyController.AddCurrency(currency);
        Assertions.assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }
    @Test
    public void GetCurrency(){
        ResponseEntity response = _CurrencyController.getCurrency(1L);
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
    }
    @Test
    public void GetCurrency_DoesntExist(){
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            _CurrencyController.getCurrency(100000L);
        });
        Assertions.assertEquals(HttpStatus.NOT_FOUND.toString(),exception.getMessage());
    }
    @ParameterizedTest
    @CsvSource({
            "USD, BHD, 100, 265.0",  // sourceCurrency, targetCurrency, amount, expectedConvertedAmount
            "GBP, KWD, 200, 509.375",
            "USD, ILS, , 3.74"
    })
    public void ConvertCurrency(String sourceCurrency, String targetCurrency, Double amount, Double expectedConvertedAmount){
        ResponseEntity response = _CurrencyController.convertCurrency(sourceCurrency,targetCurrency,amount);
        Assertions.assertEquals(expectedConvertedAmount,response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    }
    @Test
    public void GetAll(){
        ResponseEntity response = _CurrencyController.getAllRates();
        Assertions.assertAll("Response validation",
                () -> {
                    System.out.println("Verifying response status code");
                    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode(), "Response status should be OK");
                },
                () -> {
                    System.out.println("Verifying response body");
                    Assertions.assertNotNull(response.getBody(), "Response body should not be null");
                }
        );
    }


}
