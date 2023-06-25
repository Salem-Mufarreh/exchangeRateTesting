package QA.Project.Controller;

import QA.Project.Entity.CurrencyEntity;
import QA.Project.Service.CurrencyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {
    private final CurrencyService _CurrencyService;

    public CurrencyController(CurrencyService currencyService) {
        _CurrencyService = currencyService;
    }

    @PostMapping("/")
    public ResponseEntity<CurrencyEntity> AddCurrency(@Valid @RequestBody CurrencyEntity currency){
        if(currency!= null){
            if (_CurrencyService.isEmpty(currency)){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            CurrencyEntity entity = _CurrencyService.addCurrency(currency);
            System.out.println(entity.toString());
            return new ResponseEntity(entity, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CurrencyEntity> getCurrency(@PathVariable Long id){
        return new ResponseEntity<>(_CurrencyService.getCurrency(id),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteCurrency(@PathVariable Long id){
        _CurrencyService.removeCurrency(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<List<CurrencyEntity>> getAllRates(){
        List<CurrencyEntity> list = _CurrencyService.getAllRates();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    @GetMapping("/convert")
    public ResponseEntity<Double> convertCurrency(@RequestParam("source") String sourceCurrency,
                                                  @RequestParam("target") String targetCurrency,
                                                  @RequestParam(name = "amount", required = false) Double amount ) {

        // Logic to convert the currency
        Double rate = _CurrencyService.convertCurrency(sourceCurrency,targetCurrency);;
        // Retrieve the conversion rate based on source and target currencies

        // Calculate the converted amount
        Double convertedAmount =rate;
        if(amount != null) {
            convertedAmount= rate * amount;
        }

        // Return the converted amount
        return ResponseEntity.ok(convertedAmount);
    }


}
