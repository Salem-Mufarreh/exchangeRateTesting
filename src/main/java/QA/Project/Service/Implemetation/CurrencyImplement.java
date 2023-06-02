package QA.Project.Service.Implemetation;

import QA.Project.Entity.CurrencyEntity;
import QA.Project.Repository.CurrencyRep;
import QA.Project.Service.CurrencyService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Random;

@Service
public class CurrencyImplement implements CurrencyService {
    private final CurrencyRep _CurrencyRepo;

    public CurrencyImplement(CurrencyRep currencyRepo) {
        _CurrencyRepo = currencyRepo;
    }

    @Override
    public CurrencyEntity addCurrency(CurrencyEntity currencyEntity) {
        CurrencyEntity currency = new CurrencyEntity();
        try {
            currencyEntity.setId(new Random().nextLong());
            currency = _CurrencyRepo.save(currencyEntity);
            return currency;
        }catch (Exception ex){
            return currency;
        }

    }

    @Override
    public void removeCurrency(Long id) {
        CurrencyEntity currencyEntity = getCurrency(id);
        _CurrencyRepo.delete(currencyEntity);
    }

    @Override
    public List<CurrencyEntity> getAllRates() {
        List<CurrencyEntity> CurrencyList = _CurrencyRepo.findAll();
        return CurrencyList;
    }

    @Override
    public Double convertCurrency(String source, String target) {
        CurrencyEntity Source = getCurrency(source);
        CurrencyEntity Target = getCurrency(target);
        double rate = (Target.rate/ Source.rate);


        return rate;
    }


    @Override
    public CurrencyEntity getCurrency(Long id) {
        CurrencyEntity entity = _CurrencyRepo.findById(id).orElseThrow(()-> new RuntimeException(String.valueOf(HttpStatus.NOT_FOUND)));
        return entity;
    }

    public CurrencyEntity getCurrency(String name){
        name = "%"+name+"%";
        CurrencyEntity entity = _CurrencyRepo.getCurrencyEntitiesBySourceCurrency(name);
        if (entity == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, name + " is not available");

        }
        return entity;
    }
}
