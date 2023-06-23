package QA.Project.Tests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.Mockito.*;

public class CurrencyApiTesting {
    @ParameterizedTest
    @CsvSource({
            "USD, BHD, 100, 265.0",  // sourceCurrency, targetCurrency, amount, expectedConvertedAmount
            "GBP, KWD, 200, 170",
            "USD, ILS, , 3.74"
    })
    public void MockitoTesting(String sourceCurrency, String targetCurrency, Double amount, Double expectedConvertedAmount) throws IOException {
        assumeFalse(amount == null);

        // Mocking the HttpURLConnection class
        HttpURLConnection mockConnection = Mockito.mock(HttpURLConnection.class);
        InputStream mockInputStream = new ByteArrayInputStream("{\"result\":\"success\",\"base_code\":\"USD\",\"target_code\":\"EUR\",\"conversion_rate\":\"0.85\"}".getBytes());

        // Setting URL
        String url_str = "https://v6.exchangerate-api.com/v6/e0b86c4e5b83deb6f8ab1c2d/pair/"+sourceCurrency+"/"+targetCurrency;

        // Mocking the URL and connection
        URL mockUrl = Mockito.mock(URL.class);
        Mockito.when(mockUrl.openConnection()).thenReturn(mockConnection);
        Mockito.when(mockUrl.toString()).thenReturn(url_str);

        // Mocking the request and input stream
        Mockito.when(mockConnection.getInputStream()).thenReturn(mockInputStream);

        // Convert to JSON
        Gson gson = new Gson();
        JsonElement jsonelement = gson.fromJson(new InputStreamReader(mockConnection.getInputStream()), JsonElement.class);
        JsonObject jsonobj = jsonelement.getAsJsonObject();

        // Call the method under test
        double conversionRate = jsonobj.get("conversion_rate").getAsDouble();
        Double convertedAmount = conversionRate * amount;

        // Assertions
        Assertions.assertEquals(expectedConvertedAmount, convertedAmount);
    }
}
